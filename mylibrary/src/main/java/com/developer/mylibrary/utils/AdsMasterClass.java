package com.developer.mylibrary.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.value.LottieFrameInfo;
import com.airbnb.lottie.value.SimpleLottieValueCallback;
import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdViewAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.developer.mylibrary.rewarded_ad.RewardedAdLoad;
import com.developer.mylibrary.R;
import com.developer.mylibrary.appopen_ad.AppOpenAdLoad;
import com.developer.mylibrary.banner_ad.BannerAdLoad;
import com.developer.mylibrary.eum_class.AdsLogTag;
import com.developer.mylibrary.eum_class.AllAdsType;
import com.developer.mylibrary.fullscreen_ad.normal.FullScreenAdLoad;
import com.developer.mylibrary.fullscreen_ad.preload.FullScreenAdPreload;
import com.developer.mylibrary.model.AdsDataModel;
import com.developer.mylibrary.native_ad.normal.NativeAdLoad;
import com.developer.mylibrary.native_ad.preload.NativeAdPreLoad;
import com.facebook.ads.Ad;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;

import java.nio.charset.StandardCharsets;

public class AdsMasterClass {

    public static Dialog dialog;
    public static CountDownTimer countDownTimer;

    public static AdsDataModel adsDataModel;
    public static AppOpenAdLoad appOpenAdLoad;
    public static OnRewardEarnedListener onRewardEarnedListener;

    public static boolean isSplashAdRequestCancel = false;

    // check connection
    public static boolean isConnected(Activity activity) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    // for ads url
    public static String getOriginalString(String originalString) {
        StringBuilder modifiedString = new StringBuilder();
        int counter = 0;
        int interval = 3;

        for (int i = 0; i < originalString.length(); i++) {
            counter++;
            if (counter == interval) {
                counter = 0;
                interval = interval == 3 ? 4 : 3;
            } else {
                modifiedString.append(originalString.charAt(i));
            }
        }
        return modifiedString.toString();
    }

    public static void getBaseUrl(Context context, String originalString) {
        byte[] data = Base64.decode(getOriginalString(originalString), 0);
        String value = new String(data, StandardCharsets.UTF_8);
        AdsPreference.putString(context, AdsConstant.BASE_URL, value == null ? "" : value);
    }

    public static void getToken(Context context, String originalString) {
        byte[] data = Base64.decode(originalString, 0);
        String value = new String(data, StandardCharsets.UTF_8);
        AdsPreference.putString(context, AdsConstant.TOKEN, value == null ? "" : value);
    }

    // getter setter
    public static AdsDataModel getAdsDataModel() {
        return adsDataModel;
    }

    public static void setAdsDataModel(AdsDataModel adsDataModel) {
        AdsMasterClass.adsDataModel = adsDataModel;
    }

    // constant method
    public static int getAppVersionCode(Activity activity, String packageName) {
        try {
            PackageInfo packageInfo = activity.getPackageManager().getPackageInfo(packageName, 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            return 0;
        }
    }

    public static void showAdTag(String tag, String msg) {
        if (getAdsDataModel() != null && getAdsDataModel().getShow_log() == 1) {
            Log.e(tag, msg);
        }
    }

    public static void setAdsDefaultValue(Activity activity, String packageName) {
        if (AdsMasterClass.getAdsDataModel().getApp_inreview() > 0 && AdsMasterClass.getAdsDataModel().getApp_inreview() == AdsMasterClass.getAppVersionCode(activity, packageName)) {
            AdsDefaultValue.setDefaultValue();
        }
    }

    public static void checkAppLive(Activity activity) {
        if (AdsMasterClass.getAdsDataModel() != null && AdsMasterClass.getAdsDataModel().getCheck_app_status() == 1) {
            new AppLiveAsyncTask(activity).execute();
        }
    }

    public static void loadGoogleAppOpen(Activity appOpenActivity) {
        appOpenAdLoad = new AppOpenAdLoad(MyAdApplication.getMyApplication(), appOpenActivity);
    }

    public interface OnRewardEarnedListener {
        void onRewardEarned(boolean earnedReward);
    }

    public static void setOnRewardEarnedListener(OnRewardEarnedListener mOnRewardEarnedListener) {
        onRewardEarnedListener = mOnRewardEarnedListener;
    }

    // show fullscreen
    public static void showFullScreenAd(Activity activity, Intent intent) {
        if (getFullScreenAdShowValue(activity)) {
            if (getAdsDataModel().getInterstitial_show_sequence() != null && getAdsDataModel().getInterstitial_show_sequence().trim().length() > 0) {
                if (getAdsDataModel().getInterstitial_show_ads_loading() == 0) {
                    FullScreenAdPreload.showPreloadSequenceFullScreenAd(activity, intent);
                } else {
                    FullScreenAdLoad.loadSequenceFullScreenAd(activity, intent);
                }
            } else {
                startNextActivity(activity, intent);
            }
        } else {
            startNextActivity(activity, intent);
        }
    }

    public static void showFullScreenAdOnBack(Activity activity) {
        if (getFullScreenAdOnBackShowValue(activity)) {
            if (getAdsDataModel().getInterstitial_show_sequence() != null && getAdsDataModel().getInterstitial_show_sequence().trim().length() > 0) {
                Intent intent = new Intent();
                intent.putExtra(AdsConstant.ONLY_FINISH, true);
                if (getAdsDataModel().getInterstitial_show_ads_loading() == 0) {
                    FullScreenAdPreload.showPreloadSequenceFullScreenAd(activity, intent);
                } else {
                    FullScreenAdLoad.loadSequenceFullScreenAd(activity, intent);
                }
            } else {
                activity.finish();
            }
        } else {
            activity.finish();
        }
    }

    public static void showTipsFullScreenAd(Activity activity, Intent intent) {
        if (getTipsFullScreenAdShowValue(activity)) {
            if (getAdsDataModel().getInterstitial_show_sequence() != null && getAdsDataModel().getInterstitial_show_sequence().trim().length() > 0) {
                if (getAdsDataModel().getInterstitial_show_ads_loading() == 0) {
                    FullScreenAdPreload.showPreloadSequenceFullScreenAd(activity, intent);
                } else {
                    FullScreenAdLoad.loadSequenceFullScreenAd(activity, intent);
                }
            } else {
                startNextActivity(activity, intent);
            }
        } else {
            startNextActivity(activity, intent);
        }
    }

    // show rewarded
    public static void showRewardedAd(Activity activity) {
        if (AdsMasterClass.isConnected(activity) && getRewardedAdShowValue(activity)) {
            if (getAdsDataModel().getRewarded_show_sequence() != null && getAdsDataModel().getRewarded_show_sequence().trim().length() > 0) {
                RewardedAdLoad.loadSequenceRewardedAd(activity);
            }
        }
    }

    // show banner
    public static void showBannerAd(Activity activity, LinearLayout linearLayout) {
        if (getBannerAdShowValue(activity)) {
            BannerAdLoad.loadSequenceBannerAd(activity, linearLayout);
        }
    }

    public static void showExtraBannerAd(Activity activity, LinearLayout linearLayout) {
        if (getAdsDataModel() != null && getAdsDataModel().getShow_native_second() == 1) {
            if (getBannerAdShowValue(activity)) {
                BannerAdLoad.loadSequenceBannerAd(activity, linearLayout);
            }
        }
    }

    // show native
    public static void showNativeAd(Activity activity, RelativeLayout relativeLayout, LinearLayout linearLayout, String nativeAdSize, boolean showExtraNative) {
        if (getNativeAdShowValue(activity)) {
            if (showExtraNative) {
                if (getAdsDataModel() != null && getAdsDataModel().getShow_native_second() == 1) {
                    NativeAdLoad.loadSequenceNativeAd(activity, relativeLayout, linearLayout, nativeAdSize);
                }
            } else {
                NativeAdLoad.loadSequenceNativeAd(activity, relativeLayout, linearLayout, nativeAdSize);
            }
        }
    }

    public static void showPreloadNativeAd(Activity activity, RelativeLayout relativeLayout, LinearLayout linearLayout, String nativeAdSize, boolean showExtraNative) {
        if (getNativeAdShowValue(activity)) {
            if (showExtraNative) {
                if (getAdsDataModel() != null && getAdsDataModel().getShow_native_second() == 1) {
                    NativeAdPreLoad.showPreloadSequenceNativeAd(activity, relativeLayout, linearLayout, nativeAdSize, showExtraNative);
                }
            } else {
                NativeAdPreLoad.showPreloadSequenceNativeAd(activity, relativeLayout, linearLayout, nativeAdSize, showExtraNative);
            }
        }
    }

    public static void showListNativeAd(Activity activity, RelativeLayout relativeLayout, LinearLayout linearLayout, String nativeAdSize) {
        NativeAdLoad.loadSequenceNativeAd(activity, relativeLayout, linearLayout, nativeAdSize);
    }

    public static void showListPreloadNativeAd(Activity activity, RelativeLayout relativeLayout, LinearLayout linearLayout, String nativeAdSize) {
        NativeAdPreLoad.showPreloadSequenceNativeAd(activity, relativeLayout, linearLayout, nativeAdSize, false);
    }

    // get ads value
    public static boolean getFullScreenAdShowValue(Activity activity) {
        if (getAdsDataModel() != null) {
            if (getAdsDataModel().getRandom_max_number() > 0) {
                return AdsGetRandomValue.getRandomAdShowValue(activity);
            }
            if (getAdsDataModel().getInterstitial_show_value() > 0) {
                int pos = AdsPreference.getInt(activity, AdsConstant.FullScreenAdShowValuePos, 0);
                int nextPos = pos + 1;
                if (nextPos == getAdsDataModel().getInterstitial_show_value()) {
                    AdsPreference.putInt(activity, AdsConstant.FullScreenAdShowValuePos, 0);
                    return true;
                }
                if (nextPos > getAdsDataModel().getInterstitial_show_value()) {
                    nextPos = 0;
                }
                AdsPreference.putInt(activity, AdsConstant.FullScreenAdShowValuePos, nextPos);
                return false;
            }
        }
        return false;
    }

    public static boolean getTipsFullScreenAdShowValue(Activity activity) {
        if (getAdsDataModel() != null && getAdsDataModel().getShow_tips_interstitial_value() != null && getAdsDataModel().getShow_tips_interstitial_value().trim().length() > 0 && !getAdsDataModel().getShow_tips_interstitial_value().equals("0")) {
            if (getAdsDataModel().getNext_tips_fullscreen_value() > 0) {
                int pos = AdsPreference.getInt(activity, AdsConstant.FullScreenAdShowValuePosTips, 0);
                int nextPos = pos + 1;
                if (nextPos == getAdsDataModel().getNext_tips_fullscreen_value()) {
                    AdsPreference.putInt(activity, AdsConstant.FullScreenAdShowValuePosTips, 0);
                    getAdsDataModel().setNext_tips_fullscreen_value(AdsGetRandomValue.getNextTipsFullScreenAdValue(activity));
                    return true;
                }
                if (nextPos > getAdsDataModel().getNext_tips_fullscreen_value()) {
                    nextPos = 0;
                }
                AdsPreference.putInt(activity, AdsConstant.FullScreenAdShowValuePosTips, nextPos);
                return false;
            }
            AdsPreference.putInt(activity, AdsConstant.FullScreenAdShowValuePosTips, 0);
            getAdsDataModel().setNext_tips_fullscreen_value(AdsGetRandomValue.getNextTipsFullScreenAdValue(activity));
            return false;
        }
        return false;
    }

    public static boolean getFullScreenAdOnBackShowValue(Activity activity) {
        if (getAdsDataModel() != null && getAdsDataModel().getShow_ads_on_back() != null && getAdsDataModel().getShow_ads_on_back().trim().length() > 0 && !getAdsDataModel().getShow_ads_on_back().equals("0")) {
            if (getAdsDataModel().getNext_on_back_value() > 0) {
                int pos = AdsPreference.getInt(activity, AdsConstant.FullScreenAdShowOnBackValuePos, 0);
                int nextPos = pos + 1;
                if (nextPos == getAdsDataModel().getNext_on_back_value()) {
                    AdsPreference.putInt(activity, AdsConstant.FullScreenAdShowOnBackValuePos, 0);
                    getAdsDataModel().setNext_on_back_value(AdsGetRandomValue.getNextFullScreenAdOnBackValue(activity));
                    return true;
                }
                if (nextPos > getAdsDataModel().getNext_on_back_value()) {
                    nextPos = 0;
                }
                AdsPreference.putInt(activity, AdsConstant.FullScreenAdShowOnBackValuePos, nextPos);
                return false;
            }
            AdsPreference.putInt(activity, AdsConstant.FullScreenAdShowOnBackValuePos, 0);
            getAdsDataModel().setNext_on_back_value(AdsGetRandomValue.getNextFullScreenAdOnBackValue(activity));
            return false;
        }
        return false;
    }

    public static boolean getBannerAdShowValue(Activity activity) {
        if (getAdsDataModel() != null && getAdsDataModel().getBanner_show_value() > 0) {
            int pos = AdsPreference.getInt(activity, AdsConstant.BannerAdShowValuePos, 0);
            int nextPos = pos + 1;
            if (nextPos == getAdsDataModel().getBanner_show_value()) {
                AdsPreference.putInt(activity, AdsConstant.BannerAdShowValuePos, 0);
                return true;
            }
            if (nextPos > getAdsDataModel().getBanner_show_value()) {
                nextPos = 0;
            }
            AdsPreference.putInt(activity, AdsConstant.BannerAdShowValuePos, nextPos);
            return false;
        }
        return false;
    }

    public static boolean getNativeAdShowValue(Activity activity) {
        if (getAdsDataModel() != null && getAdsDataModel().getNative_show_value() > 0) {
            int pos = AdsPreference.getInt(activity, AdsConstant.NativeAdShowValuePos, 0);
            int nextPos = pos + 1;
            if (nextPos == getAdsDataModel().getNative_show_value()) {
                AdsPreference.putInt(activity, AdsConstant.NativeAdShowValuePos, 0);
                return true;
            }
            if (nextPos > getAdsDataModel().getNative_show_value()) {
                nextPos = 0;
            }
            AdsPreference.putInt(activity, AdsConstant.NativeAdShowValuePos, nextPos);
            return false;
        }
        return false;
    }

    public static boolean getRewardedAdShowValue(Activity activity) {
        if (getAdsDataModel() != null && getAdsDataModel().getRewarded_show_value() > 0) {
            int pos = AdsPreference.getInt(activity, AdsConstant.RewardedAdShowValuePos, 0);
            int nextPos = pos + 1;
            if (nextPos == getAdsDataModel().getRewarded_show_value()) {
                AdsPreference.putInt(activity, AdsConstant.RewardedAdShowValuePos, 0);
                return true;
            }
            if (nextPos > getAdsDataModel().getRewarded_show_value()) {
                nextPos = 0;
            }
            AdsPreference.putInt(activity, AdsConstant.RewardedAdShowValuePos, nextPos);
            return false;
        }
        return false;
    }

    // get ads seq
    public static String getNextFullScreenAd(Activity activity) {
        String adsSequence = getAdsDataModel().getInterstitial_show_sequence().trim();
        String[] values = adsSequence.split("-");
        if (values != null && values.length > 0) {
            int pos = AdsPreference.getInt(activity, AdsConstant.FullScreenAdShowSeqPos, 0);
            if (pos < values.length) {
                String ad = values[pos];
                int nextPos = pos + 1;
                AdsPreference.putInt(activity, AdsConstant.FullScreenAdShowSeqPos, nextPos);
                showAdTag(AdsLogTag.AdsMasterClass.name(), "getNextFullScreenAd: " + ad);
                return ad;
            } else {
                String ad = values[0];
                AdsPreference.putInt(activity, AdsConstant.FullScreenAdShowSeqPos, 1);
                showAdTag(AdsLogTag.AdsMasterClass.name(), "getNextFullScreenAd: " + ad);
                return ad;
            }
        }
        return "";
    }

    public static String getNextPreloadFullScreenAd(Activity activity) {
        String adsSequence = getAdsDataModel().getInterstitial_show_sequence().trim();
        String[] values = adsSequence.split("-");
        if (values != null && values.length > 0) {
            int pos = AdsPreference.getInt(activity, AdsConstant.Preload_FullScreenAdSeqPos, 0);
            if (pos < values.length) {
                String ad = values[pos];
                int nextPos = pos + 1;
                AdsPreference.putInt(activity, AdsConstant.ShowPreload_FullScreenAdSeqPos, pos);
                AdsPreference.putInt(activity, AdsConstant.Preload_FullScreenAdSeqPos, nextPos);
                showAdTag(AdsLogTag.AdsMasterClass.name(), "getNextPreloadFullScreenAd: " + ad);
                return ad;
            } else {
                String ad = values[0];
                AdsPreference.putInt(activity, AdsConstant.ShowPreload_FullScreenAdSeqPos, 0);
                AdsPreference.putInt(activity, AdsConstant.Preload_FullScreenAdSeqPos, 1);
                showAdTag(AdsLogTag.AdsMasterClass.name(), "getNextPreloadFullScreenAd: " + ad);
                return ad;
            }
        }
        return "";
    }

    public static String getNextShowPreloadFullScreenAd(Activity activity) {
        String adsSequence = getAdsDataModel().getInterstitial_show_sequence().trim();
        String[] values = adsSequence.split("-");
        if (values != null && values.length > 0) {
            int pos = AdsPreference.getInt(activity, AdsConstant.ShowPreload_FullScreenAdSeqPos, 0);
            if (pos < values.length) {
                String ad = values[pos];
                showAdTag(AdsLogTag.AdsMasterClass.name(), "getNextShowPreloadFullScreenAd: " + ad);
                return ad;
            } else {
                String ad = values[0];
                showAdTag(AdsLogTag.AdsMasterClass.name(), "getNextShowPreloadFullScreenAd: " + ad);
                return ad;
            }
        }
        return "";
    }

    public static String getNextBannerAd(Activity activity) {
        String adsSequence = getAdsDataModel().getBanner_show_sequence().trim();
        String[] values = adsSequence.split("-");
        if (values != null && values.length > 0) {
            int pos = AdsPreference.getInt(activity, AdsConstant.BannerAdShowSeqPos, 0);
            if (pos < values.length) {
                String ad = values[pos];
                int nextPos = pos + 1;
                AdsPreference.putInt(activity, AdsConstant.BannerAdShowSeqPos, nextPos);
                showAdTag(AdsLogTag.AdsMasterClass.name(), "getNextBannerAd: " + ad);
                return ad;
            } else {
                String ad = values[0];
                AdsPreference.putInt(activity, AdsConstant.BannerAdShowSeqPos, 1);
                showAdTag(AdsLogTag.AdsMasterClass.name(), "getNextBannerAd: " + ad);
                return ad;
            }
        }
        return "";
    }

    public static String getNextNativeAd(Activity activity) {
        String adsSequence = getAdsDataModel().getNative_show_sequence().trim();
        String[] values = adsSequence.split("-");
        if (values != null && values.length > 0) {
            int pos = AdsPreference.getInt(activity, AdsConstant.NativeAdShowSeqPos, 0);
            if (pos < values.length) {
                String ad = values[pos];
                int nextPos = pos + 1;
                AdsPreference.putInt(activity, AdsConstant.NativeAdShowSeqPos, nextPos);
                showAdTag(AdsLogTag.AdsMasterClass.name(), "getNextNativeAd: " + ad);
                return ad;
            } else {
                String ad = values[0];
                AdsPreference.putInt(activity, AdsConstant.NativeAdShowSeqPos, 1);
                showAdTag(AdsLogTag.AdsMasterClass.name(), "getNextNativeAd: " + ad);
                return ad;
            }
        }
        return "";
    }

    public static String getNextRewardedAd(Activity activity) {
        String adsSequence = getAdsDataModel().getRewarded_show_sequence().trim();
        String[] values = adsSequence.split("-");
        if (values != null && values.length > 0) {
            int pos = AdsPreference.getInt(activity, AdsConstant.RewardedAdShowSeqPos, 0);
            if (pos < values.length) {
                String ad = values[pos];
                int nextPos = pos + 1;
                AdsPreference.putInt(activity, AdsConstant.RewardedAdShowSeqPos, nextPos);
                showAdTag(AdsLogTag.AdsMasterClass.name(), "getNextRewardedAd: " + ad);
                return ad;
            } else {
                String ad = values[0];
                AdsPreference.putInt(activity, AdsConstant.RewardedAdShowSeqPos, 1);
                showAdTag(AdsLogTag.AdsMasterClass.name(), "getNextRewardedAd: " + ad);
                return ad;
            }
        }
        return "";
    }

    public static String showQurekaImage(Activity activity) {
        String adsSequence = AdsMasterClass.getAdsDataModel().getQureka_ads_image().trim();
        String[] values = adsSequence.split("\\|");
        if (values != null && values.length > 0) {
            int pos = AdsPreference.getInt(activity, AdsConstant.QurekaAdShowSeqPos, 0);
            if (pos < values.length) {
                String ad = values[pos];
                int nextPos = pos + 1;
                AdsPreference.putInt(activity, AdsConstant.QurekaAdShowSeqPos, nextPos);
                return ad;
            } else {
                String ad = values[0];
                AdsPreference.putInt(activity, AdsConstant.QurekaAdShowSeqPos, 1);
                return ad;
            }
        }
        return "0";
    }

    // get ads seq on fail
    public static String getNextFullScreenFailedAd(String adType) {
        if (getAdsDataModel() != null && getAdsDataModel().getInterstitial_show_fail_sequence() != null && getAdsDataModel().getInterstitial_show_fail_sequence().trim().length() > 0) {
            String failSeq = getAdsDataModel().getInterstitial_show_fail_sequence().trim();
            String[] values = failSeq.split(",");
            if (values != null && values.length > 0) {
                for (int i = 0; i < values.length; i++) {
                    String adsSequence = values[i];
                    if (adsSequence != null && adsSequence.length() > 1) {
                        String[] fail = adsSequence.split("-");
                        if (fail != null && fail.length > 1) {
                            String failKey = fail[0].trim();
                            String failValue = fail[1].trim();
                            if (failKey.equals(adType)) {
                                return failValue == null ? "" : failValue;
                            }
                        }
                    }
                }
            }
            return "";
        }
        return "";
    }

    public static String getNextBannerFailedAd(String adType) {
        if (getAdsDataModel() != null && getAdsDataModel().getBanner_show_fail_sequence() != null && getAdsDataModel().getBanner_show_fail_sequence().trim().length() > 0) {
            String failSeq = getAdsDataModel().getBanner_show_fail_sequence().trim();
            String[] values = failSeq.split(",");
            if (values != null && values.length > 0) {
                for (int i = 0; i < values.length; i++) {
                    String adsSequence = values[i];
                    if (adsSequence != null && adsSequence.length() > 1) {
                        String[] fail = adsSequence.split("-");
                        if (fail != null && fail.length > 1) {
                            String failKey = fail[0].trim();
                            String failValue = fail[1].trim();
                            if (failKey.equals(adType)) {
                                return failValue == null ? "" : failValue;
                            }
                        }
                    }
                }
            }
            return "";
        }
        return "";
    }

    public static String getNextNativeFailedAd(String adType) {
        if (getAdsDataModel() != null && getAdsDataModel().getNative_show_fail_sequence() != null && getAdsDataModel().getNative_show_fail_sequence().trim().length() > 0) {
            String failSeq = getAdsDataModel().getNative_show_fail_sequence().trim();
            String[] values = failSeq.split(",");
            if (values != null && values.length > 0) {
                for (int i = 0; i < values.length; i++) {
                    String adsSequence = values[i];
                    if (adsSequence != null && adsSequence.length() > 1) {
                        String[] fail = adsSequence.split("-");
                        if (fail != null && fail.length > 1) {
                            String failKey = fail[0].trim();
                            String failValue = fail[1].trim();
                            if (failKey.equals(adType)) {
                                return failValue == null ? "" : failValue;
                            }
                        }
                    }
                }
            }
            return "";
        }
        return "";
    }

    public static String getNextRewardedFailedAd(String adType) {
        if (getAdsDataModel() != null && getAdsDataModel().getRewarded_show_fail_sequence() != null && getAdsDataModel().getRewarded_show_fail_sequence().trim().length() > 0) {
            String failSeq = getAdsDataModel().getRewarded_show_fail_sequence().trim();
            String[] values = failSeq.split(",");
            if (values != null && values.length > 0) {
                for (int i = 0; i < values.length; i++) {
                    String adsSequence = values[i];
                    if (adsSequence != null && adsSequence.length() > 1) {
                        String[] fail = adsSequence.split("-");
                        if (fail != null && fail.length > 1) {
                            String failKey = fail[0].trim();
                            String failValue = fail[1].trim();
                            if (failKey.equals(adType)) {
                                return failValue == null ? "" : failValue;
                            }
                        }
                    }
                }
            }
            return "";
        }
        return "";
    }

    public static String getNextAppOpenFailedAd(String adType) {
        if (getAdsDataModel() != null && getAdsDataModel().getApp_open_fail_sequence() != null && getAdsDataModel().getApp_open_fail_sequence().trim().length() > 0) {
            String failSeq = getAdsDataModel().getApp_open_fail_sequence().trim();
            String[] values = failSeq.split(",");
            if (values != null && values.length > 0) {
                for (int i = 0; i < values.length; i++) {
                    String adsSequence = values[i];
                    if (adsSequence != null && adsSequence.length() > 1) {
                        String[] fail = adsSequence.split("-");
                        if (fail != null && fail.length > 1) {
                            String failKey = fail[0].trim();
                            String failValue = fail[1].trim();
                            if (failKey.equals(adType)) {
                                return failValue == null ? "" : failValue;
                            }
                        }
                    }
                }
            }
            return "";
        }
        return "";
    }

    // for fullscreen loading ad
    public static void showAdProgressDialog(Activity activity) {
        if (dialog != null && dialog.isShowing()) {

        } else {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        dialog = new Dialog(activity);
                        dialog.setContentView(R.layout.dialog_show_ad);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.setCancelable(false);
                        dialog.show();

                        ((LottieAnimationView) dialog.findViewById(R.id.lottie_ads_loading)).addValueCallback(new KeyPath("**"), LottieProperty.COLOR_FILTER,
                                new SimpleLottieValueCallback<ColorFilter>() {
                                    @Override
                                    public ColorFilter getValue(LottieFrameInfo<ColorFilter> frameInfo) {
                                        return new PorterDuffColorFilter(Color.parseColor(AdsPreference.getString(activity, AdsConstant.NATIVE_TEXT_COLOR, AdsConstant.getHexStringColor(activity.getResources().getColor(R.color.ad_text_color)))), PorterDuff.Mode.SRC_ATOP);
                                    }
                                }
                        );
                        dialog.findViewById(R.id.ly_ads_loading).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(AdsPreference.getString(activity, AdsConstant.NATIVE_BG_COLOR, AdsConstant.getHexStringColor(activity.getResources().getColor(R.color.ad_bg_color))))));
                        ((TextView) dialog.findViewById(R.id.tv_ads_loading)).setTextColor(ColorStateList.valueOf(Color.parseColor(AdsPreference.getString(activity, AdsConstant.NATIVE_TEXT_COLOR, AdsConstant.getHexStringColor(activity.getResources().getColor(R.color.ad_text_color))))));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public static void dismissAdsProgressDialog() {
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void adProgressTimerCancel() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }

    /*----------------------------------AD Dialog-----------------------------*/

    public static boolean getRateDialogShowValue(Activity activity) {
        if (getAdsDataModel() != null && getAdsDataModel().getShow_rate_dialog_value() > 0) {
            int pos = AdsPreference.getInt(activity, AdsConstant.RateDialogShowValuePos, 0);
            int nextPos = pos + 1;
            if (nextPos == getAdsDataModel().getShow_rate_dialog_value()) {
                AdsPreference.putInt(activity, AdsConstant.RateDialogShowValuePos, 0);
                return true;
            }
            if (nextPos > getAdsDataModel().getShow_rate_dialog_value()) {
                nextPos = 0;
            }
            AdsPreference.putInt(activity, AdsConstant.RateDialogShowValuePos, nextPos);
            return false;
        }
        return false;
    }

    /*--------------------------------AD ID---------------------------------*/

    public static String getAdID(Context context, String adID, String key) {
        String[] values = adID.split("\\|");
        if (values != null && values.length > 0) {
            int pos = AdsPreference.getInt(context, key, 0);
            String adId;
            if (pos < values.length) {
                adId = values[pos];
                int nextPos = pos + 1;
                AdsPreference.putInt(context, key, nextPos);
            } else {
                adId = values[0];
                AdsPreference.putInt(context, key, 1);
            }
            showAdTag(AdsLogTag.AdsMasterClass.name(), key + " - " + adId);
            return adId;
        }
        return "";
    }

    public static String getGoogleFullScreenId(Activity activity, String adID) {
        if (adID != null && adID.contains("|")) {
            return getAdID(activity, adID, AdsConstant.GoogleFullScreenIdSeqPos);
        }
        return adID;
    }

    public static String getFacebookFullScreenId(Activity activity, String adID) {
        if (adID != null && adID.contains("|")) {
            return getAdID(activity, adID, AdsConstant.FacebookFullScreenIdSeqPos);
        }
        return adID;
    }

    public static String getApplovinFullScreenId(Activity activity, String adID) {
        if (adID != null && adID.contains("|")) {
            return getAdID(activity, adID, AdsConstant.ApplovinFullScreenIdSeqPos);
        }
        return adID;
    }

    public static String getGoogleBannerId(Activity activity, String adID) {
        if (adID != null && adID.contains("|")) {
            return getAdID(activity, adID, AdsConstant.GoogleBannerIdSeqPos);
        }
        return adID;
    }

    public static String getFacebookBannerId(Activity activity, String adID) {
        if (adID != null && adID.contains("|")) {
            return getAdID(activity, adID, AdsConstant.FacebookBannerIdSeqPos);
        }
        return adID;
    }

    public static String getFacebookNativeBannerId(Activity activity, String adID) {
        if (adID != null && adID.contains("|")) {
            return getAdID(activity, adID, AdsConstant.FacebookNativeBannerIdSeqPos);
        }
        return adID;
    }

    public static String getGoogleNativeId(Activity activity, String adID) {
        if (adID != null && adID.contains("|")) {
            return getAdID(activity, adID, AdsConstant.GoogleNativeIdSeqPos);
        }
        return adID;
    }

    public static String getFacebookNativeId(Activity activity, String adID) {
        if (adID != null && adID.contains("|")) {
            return getAdID(activity, adID, AdsConstant.FacebookNativeIdSeqPos);
        }
        return adID;
    }

    public static String getApplovinNativeId(Activity activity, String adID) {
        if (adID != null && adID.contains("|")) {
            return getAdID(activity, adID, AdsConstant.ApplovinNativeIdSeqPos);
        }
        return adID;
    }

    public static String getApplovinAppOpenId(Context context, String adID) {
        if (adID != null && adID.contains("|")) {
            return getAdID(context, adID, AdsConstant.ApplovinAppOpenIdSeqPos);
        }
        return adID;
    }

    public static String getGoogleRewardedId(Activity activity, String adID) {
        if (adID != null && adID.contains("|")) {
            return getAdID(activity, adID, AdsConstant.GoogleRewardedIdSeqPos);
        }
        return adID;
    }

    public static String getGoogleAppOpenId(Context context, String ad) {
        if (ad != null && ad.trim().length() > 0) {
            String adID = "";
            if (ad.equals(AllAdsType.g.name())) {
                adID = AdsMasterClass.getAdsDataModel().getGoogle_appopen_id();
            } else if (ad.equals(AllAdsType.adx.name())) {
                adID = AdsMasterClass.getAdsDataModel().getAdx_appopen_id();
            } else if (ad.equals(AllAdsType.adx2.name())) {
                adID = AdsMasterClass.getAdsDataModel().getAdx2_appopen_id();
            } else if (ad.equals(AllAdsType.ab.name())) {
                adID = getAdsDataModel().getAppbaroda_appopen_id();
            }
            if (adID != null && adID.contains("|")) {
                return getAdID(context, adID, AdsConstant.GoogleAppOpenIdSeqPos);
            }
            return adID;
        }
        return "";
    }

    // splash ad
    public static void showSplashAd(Activity activity, Intent intent) {
        String ad = getAdsDataModel().getApp_open_splash();
        showAdTag(AdsLogTag.AdsMasterClass.name(), "showSplashAppOpenAd - " + ad);
        if (ad.equals(AllAdsType.g.name())) {
            showSplashAppOpenAd(activity, intent, getGoogleAppOpenId(activity, ad), ad);
        } else if (ad.equals(AllAdsType.adx.name())) {
            showSplashAppOpenAd(activity, intent, getGoogleAppOpenId(activity, ad), ad);
        } else if (ad.equals(AllAdsType.adx2.name())) {
            showSplashAppOpenAd(activity, intent, getGoogleAppOpenId(activity, ad), ad);
        } else if (ad.equals(AllAdsType.ab.name())) {
            showSplashAppOpenAd(activity, intent, getGoogleAppOpenId(activity, ad), ad);
        } else if (ad.equals(AllAdsType.f.name())) {
            loadFacebookFullScreen(activity, intent, AdsMasterClass.getFacebookFullScreenId(activity, AdsMasterClass.getAdsDataModel().getFacebook_interstitial_id()));
        } else if (ad.equals(AllAdsType.a.name())) {
            loadApplovinFullScreen(activity, intent, AdsMasterClass.getApplovinFullScreenId(activity, AdsMasterClass.getAdsDataModel().getApplovin_interstitial_id()));
        } else if (ad.equals(AllAdsType.q.name())) {
            showQurekaFullAds(activity, intent);
        } else {
            startAppAfterAd(activity, intent);
        }
    }

    public static void showSplashAppOpenAd(Activity activity, Intent intent, String adID, String adType) {
        if (adID != null && adID.trim().length() > 0) {
            AppOpenAd.AppOpenAdLoadCallback appOpenAdLoadCallback = new AppOpenAd.AppOpenAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull AppOpenAd appOpenAd) {
                    super.onAdLoaded(appOpenAd);
                    showAdTag(AdsLogTag.AdsMasterClass.name(), "showSplashAppOpenAd - load");

                    FullScreenContentCallback fullScreenContentCallback = new FullScreenContentCallback() {
                        @Override
                        public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                            super.onAdFailedToShowFullScreenContent(adError);
                            startAppAfterAd(activity, intent);
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            super.onAdShowedFullScreenContent();
                        }

                        @Override
                        public void onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent();
                            showAdTag(AdsLogTag.AdsMasterClass.name(), "showSplashAppOpenAd - dismiss");
                            startAppAfterAd(activity, intent);
                        }
                    };
                    appOpenAd.setFullScreenContentCallback(fullScreenContentCallback);
                    appOpenAd.show(activity);
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    showAdTag(AdsLogTag.AdsMasterClass.name(), "showSplashAppOpenAd - failed " + loadAdError.getMessage());
                    if (getAdsDataModel().isIs_splash_appopen_fail_check()) {
                        showAdTag(AdsLogTag.AdsMasterClass.name(), "showSplashAppOpenAdOnFailed - Two time fail");
                        getAdsDataModel().setIs_splash_appopen_fail_check(false);
                        startAppAfterAd(activity, intent);
                    } else {
                        getAdsDataModel().setIs_splash_appopen_fail_check(true);
                        String ad = getNextAppOpenFailedAd(adType);
                        showAdTag(AdsLogTag.AdsMasterClass.name(), "showSplashAppOpenAdOnFailed - " + ad);
                        showSplashAppOpenAd(activity, intent, getGoogleAppOpenId(activity, ad), ad);
                    }
                }
            };
            AdRequest adRequest = new AdRequest.Builder().build();
            AppOpenAd.load(activity, adID, adRequest, appOpenAdLoadCallback);
        } else {
            startAppAfterAd(activity, intent);
        }
    }

    public static void loadFacebookFullScreen(Activity activity, Intent intent, String adID) {
        if (adID != null && adID.trim().length() > 0) {
            AdsMasterClass.isSplashAdRequestCancel = false;
            AdsMasterClass.countDownTimer = new CountDownTimer(5000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    AdsMasterClass.showAdTag(AdsLogTag.AdsMasterClass.name(), "onTick: " + millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    AdsMasterClass.adProgressTimerCancel();
                    if (!AdsMasterClass.isSplashAdRequestCancel) {
                        AdsMasterClass.isSplashAdRequestCancel = true;
                        startAppAfterAd(activity, intent);
                    }
                }
            }.start();

            com.facebook.ads.InterstitialAd interstitialAd = new com.facebook.ads.InterstitialAd(activity, adID);

            InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
                @Override
                public void onInterstitialDisplayed(Ad ad) {

                }

                @Override
                public void onInterstitialDismissed(Ad ad) {
                    AdsMasterClass.showAdTag(AdsLogTag.AdsMasterClass.name(), "showSplashAppOpenAd - dismiss");
                    startAppAfterAd(activity, intent);
                }

                @Override
                public void onError(Ad ad, com.facebook.ads.AdError adError) {
                    AdsMasterClass.showAdTag(AdsLogTag.AdsMasterClass.name(), "showSplashAppOpenAd - failed " + adError.getErrorMessage());
                    AdsMasterClass.adProgressTimerCancel();
                    if (!AdsMasterClass.isSplashAdRequestCancel) {
                        startAppAfterAd(activity, intent);
                    }
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    AdsMasterClass.showAdTag(AdsLogTag.AdsMasterClass.name(), "showSplashAppOpenAd - load");
                    AdsMasterClass.adProgressTimerCancel();
                    if (!AdsMasterClass.isSplashAdRequestCancel) {
                        interstitialAd.show();
                    }
                }

                @Override
                public void onAdClicked(Ad ad) {

                }

                @Override
                public void onLoggingImpression(Ad ad) {

                }
            };

            interstitialAd.loadAd(
                    interstitialAd.buildLoadAdConfig()
                            .withAdListener(interstitialAdListener)
                            .build());
        } else {
            startAppAfterAd(activity, intent);
        }
    }

    public static void loadApplovinFullScreen(Activity activity, Intent intent, String adID) {
        if (adID != null && adID.trim().length() > 0) {
            AdsMasterClass.isSplashAdRequestCancel = false;
            AdsMasterClass.countDownTimer = new CountDownTimer(5000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    AdsMasterClass.showAdTag(AdsLogTag.AdsMasterClass.name(), "onTick: " + millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    AdsMasterClass.adProgressTimerCancel();
                    if (!AdsMasterClass.isSplashAdRequestCancel) {
                        AdsMasterClass.isSplashAdRequestCancel = true;
                        startAppAfterAd(activity, intent);
                    }
                }
            }.start();

            MaxInterstitialAd maxInterstitialAd = new MaxInterstitialAd(adID, activity);
            maxInterstitialAd.setListener(new MaxAdViewAdListener() {
                @Override
                public void onAdExpanded(MaxAd ad) {

                }

                @Override
                public void onAdCollapsed(MaxAd ad) {

                }

                @Override
                public void onAdLoaded(MaxAd ad) {
                    AdsMasterClass.showAdTag(AdsLogTag.AdsMasterClass.name(), "showSplashAppOpenAd - load");
                    AdsMasterClass.adProgressTimerCancel();
                    if (!AdsMasterClass.isSplashAdRequestCancel) {
                        maxInterstitialAd.showAd();
                    }
                }

                @Override
                public void onAdDisplayed(MaxAd ad) {

                }

                @Override
                public void onAdHidden(MaxAd ad) {
                    AdsMasterClass.showAdTag(AdsLogTag.AdsMasterClass.name(), "showSplashAppOpenAd - dismiss");
                    maxInterstitialAd.destroy();
                    startAppAfterAd(activity, intent);
                }

                @Override
                public void onAdClicked(MaxAd ad) {

                }

                @Override
                public void onAdLoadFailed(String adUnitId, MaxError error) {
                    AdsMasterClass.showAdTag(AdsLogTag.AdsMasterClass.name(), "showSplashAppOpenAd - failed " + error.getMessage());
                    AdsMasterClass.adProgressTimerCancel();
                    if (!AdsMasterClass.isSplashAdRequestCancel) {
                        startAppAfterAd(activity, intent);
                    }
                }

                @Override
                public void onAdDisplayFailed(MaxAd ad, MaxError error) {
                    if (!AdsMasterClass.isSplashAdRequestCancel) {
                        startAppAfterAd(activity, intent);
                    }
                }
            });
            maxInterstitialAd.loadAd();
        } else {
            startAppAfterAd(activity, intent);
        }
    }

    private static void showQurekaFullAds(final Activity activity, final Intent intent) {
        if (AdsMasterClass.getAdsDataModel() != null && AdsMasterClass.getAdsDataModel().getQureka_url().trim().length() > 0) {
            Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(AdsConstant.QUREKA_FULL_SCREEN_LIST[Integer.parseInt(AdsMasterClass.showQurekaImage(activity))]);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);

            CardView card_click = dialog.findViewById(R.id.card_click);
            ImageView iv_close = dialog.findViewById(R.id.iv_close);
            TextView tv_ad = dialog.findViewById(R.id.tv_ad);

            Animation slide_down = AnimationUtils.loadAnimation(activity, R.anim.slide_up);
            card_click.setAnimation(slide_down);
            Animation left_in = AnimationUtils.loadAnimation(activity, R.anim.slide_in_left);
            tv_ad.setAnimation(left_in);
            Animation right_in = AnimationUtils.loadAnimation(activity, R.anim.slide_in_right);
            iv_close.setAnimation(right_in);

            iv_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    startAppAfterAd(activity, intent);
                }
            });

            card_click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AdsShareUtils.showQurekaAds(activity);
                }
            });

            dialog.show();
        } else {
            startAppAfterAd(activity, intent);
        }
    }

    public static void startAppAfterAd(Activity activity, Intent intent) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                activity.startActivity(intent);
                activity.finish();
            }
        }, 500);
    }

    public static void startApp(Activity activity, Intent intent) {
        if (getAdsDataModel() != null) {
            AdsMasterClass.checkAppLive(activity);

            if (getAdsDataModel().getApp_open_splash() != null && !getAdsDataModel().getApp_open_splash().equals("0")) {
                showSplashAd(activity, intent);
            } else {
                activity.startActivity(intent);
                activity.finish();
            }
        }
    }

    public static void startNextActivity(Activity activity, Intent intent) {
        if (intent != null) {
            AdsCallBack.onFullscreenAdDismissListener.onFullscreenAdDismiss(activity, intent);
        }
    }
}
