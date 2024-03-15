package com.developer.mylibrary.fullscreen_ad.normal;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdViewAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.developer.mylibrary.R;
import com.developer.mylibrary.eum_class.AdsLogTag;
import com.developer.mylibrary.eum_class.AllAdsType;
import com.developer.mylibrary.fullscreen_ad.preload.FullScreenAdPreload;
import com.developer.mylibrary.utils.AdsConstant;
import com.developer.mylibrary.utils.AdsMasterClass;
import com.developer.mylibrary.utils.AdsShareUtils;
import com.facebook.ads.Ad;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.admanager.AdManagerAdRequest;
import com.google.android.gms.ads.admanager.AdManagerInterstitialAd;
import com.google.android.gms.ads.admanager.AdManagerInterstitialAdLoadCallback;

public class FullScreenAdFailed {

    public static Activity activityAdFailed;
    public static Intent intentAdFailed;

    public static void showFullScreenAdOnFailed(Activity activity, Intent intent, String currentAd) {
        activityAdFailed = activity;
        intentAdFailed = intent;
        String nextFailedAd = AdsMasterClass.getNextFullScreenFailedAd(currentAd);
        AdsMasterClass.showAdTag(AdsLogTag.FullScreenAdFailed.name(), "showFullScreenAdOnFailed - value " + nextFailedAd);
        if (nextFailedAd.equals(AllAdsType.g.name())) {
            FullScreenAdPreload.preloadSequenceFullScreenAd(activity);
            loadGoogleFullScreen(activity, intent, AdsMasterClass.getGoogleFullScreenId(activity, AdsMasterClass.getAdsDataModel().getGoogle_interstitial_id()));
        } else if (nextFailedAd.equals(AllAdsType.adx.name())) {
            FullScreenAdPreload.preloadSequenceFullScreenAd(activity);
            loadGoogleFullScreen(activity, intent, AdsMasterClass.getGoogleFullScreenId(activity, AdsMasterClass.getAdsDataModel().getAdx_interstitial_id()));
        } else if (nextFailedAd.equals(AllAdsType.f.name())) {
            FullScreenAdPreload.preloadSequenceFullScreenAd(activity);
            loadFacebookFullScreen(activity, intent, AdsMasterClass.getFacebookFullScreenId(activity, AdsMasterClass.getAdsDataModel().getFacebook_interstitial_id()));
        } else if (nextFailedAd.equals(AllAdsType.a.name())) {
            loadApplovinFullScreen(activity, intent, AdsMasterClass.getApplovinFullScreenId(activity, AdsMasterClass.getAdsDataModel().getApplovin_interstitial_id()));
        } else if (nextFailedAd.equals(AllAdsType.q.name())) {
            AdsMasterClass.dismissAdsProgressDialog();
            AdsMasterClass.adProgressTimerCancel();
            FullScreenAdPreload.preloadSequenceFullScreenAd(activity);
            showQurekaFullAds(activity, intent);
        } else {
            AdsMasterClass.dismissAdsProgressDialog();
            AdsMasterClass.adProgressTimerCancel();
            FullScreenAdPreload.preloadSequenceFullScreenAd(activity);
            AdsMasterClass.startNextActivity(activity, intent);
        }
    }

    public static void loadGoogleFullScreen(final Activity activity, final Intent intent, String adID) {
        if (adID != null && adID.trim().length() > 0) {
            FullScreenAdLoad.isAdRequestCancel = false;
            AdsMasterClass.adProgressTimerCancel();
            AdsMasterClass.countDownTimer = new CountDownTimer(20000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    AdsMasterClass.showAdTag(AdsLogTag.FullScreenAdFailed.name(), "onTick: Remain " + millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    AdsMasterClass.dismissAdsProgressDialog();
                    AdsMasterClass.adProgressTimerCancel();
                    if (!FullScreenAdLoad.isAdRequestCancel) {
                        FullScreenAdLoad.isAdRequestCancel = true;
                        AdsMasterClass.startNextActivity(activity, intent);
                    }
                }
            }.start();

            AdManagerAdRequest adRequest = new AdManagerAdRequest.Builder().build();

            AdManagerInterstitialAd.load(activity, adID, adRequest, new AdManagerInterstitialAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull AdManagerInterstitialAd interstitialAd) {
                    super.onAdLoaded(interstitialAd);
                    AdsMasterClass.showAdTag(AdsLogTag.FullScreenAdFailed.name(), "loadGoogleFullScreenFailed - loaded");

                    AdsMasterClass.adProgressTimerCancel();
                    AdsMasterClass.dismissAdsProgressDialog();
                    if (!FullScreenAdLoad.isAdRequestCancel) {
                        interstitialAd.show(activity);
                    }
                    interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                            super.onAdFailedToShowFullScreenContent(adError);
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            super.onAdShowedFullScreenContent();
                        }

                        @Override
                        public void onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent();
                            AdsMasterClass.showAdTag(AdsLogTag.FullScreenAdFailed.name(), "loadGoogleFullScreenFailed - dismiss");
                            AdsMasterClass.startNextActivity(activity, intent);
                        }

                        @Override
                        public void onAdImpression() {
                            super.onAdImpression();
                        }
                    });
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    AdsMasterClass.showAdTag(AdsLogTag.FullScreenAdFailed.name(), "loadGoogleFullScreenFailed - failed " + loadAdError.getMessage());
                    if (!FullScreenAdLoad.isAdRequestCancel) {
                        AdsMasterClass.dismissAdsProgressDialog();
                        AdsMasterClass.adProgressTimerCancel();
                        AdsMasterClass.startNextActivity(activity, intent);
                    }
                }
            });
        } else {
            AdsMasterClass.dismissAdsProgressDialog();
            AdsMasterClass.startNextActivity(activity, intent);
        }
    }

    public static void loadFacebookFullScreen(Activity activity, Intent intent, String adID) {
        if (adID != null && adID.trim().length() > 0) {
            FullScreenAdLoad.isAdRequestCancel = false;
            AdsMasterClass.adProgressTimerCancel();
            AdsMasterClass.countDownTimer = new CountDownTimer(20000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    AdsMasterClass.showAdTag(AdsLogTag.FullScreenAdFailed.name(), "onTick: Remain " + millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    AdsMasterClass.dismissAdsProgressDialog();
                    AdsMasterClass.adProgressTimerCancel();
                    if (!FullScreenAdLoad.isAdRequestCancel) {
                        FullScreenAdLoad.isAdRequestCancel = true;
                        AdsMasterClass.startNextActivity(activity, intent);
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
                    AdsMasterClass.showAdTag(AdsLogTag.FullScreenAdFailed.name(), "loadFacebookFullScreenFailed - dismiss");
                    AdsMasterClass.startNextActivity(activity, intent);
                }

                @Override
                public void onError(Ad ad, com.facebook.ads.AdError adError) {
                    AdsMasterClass.showAdTag(AdsLogTag.FullScreenAdFailed.name(), "loadFacebookFullScreenFailed - failed " + adError.getErrorMessage());
                    if (!FullScreenAdLoad.isAdRequestCancel) {
                        AdsMasterClass.adProgressTimerCancel();
                        AdsMasterClass.dismissAdsProgressDialog();
                        AdsMasterClass.startNextActivity(activity, intent);
                    }
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    AdsMasterClass.showAdTag(AdsLogTag.FullScreenAdFailed.name(), "loadFacebookFullScreenFailed - loaded");
                    AdsMasterClass.adProgressTimerCancel();
                    AdsMasterClass.dismissAdsProgressDialog();
                    if (!FullScreenAdLoad.isAdRequestCancel) {
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
            AdsMasterClass.dismissAdsProgressDialog();
            AdsMasterClass.startNextActivity(activity, intent);
        }
    }

    public static void loadApplovinFullScreen(Activity activity, Intent intent, String adID) {
        if (adID != null && adID.trim().length() > 0) {
            FullScreenAdLoad.isAdRequestCancel = false;
            AdsMasterClass.adProgressTimerCancel();
            AdsMasterClass.countDownTimer = new CountDownTimer(20000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    AdsMasterClass.showAdTag(AdsLogTag.FullScreenAdFailed.name(), "onTick: Remain " + millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    AdsMasterClass.dismissAdsProgressDialog();
                    AdsMasterClass.adProgressTimerCancel();
                    if (!FullScreenAdLoad.isAdRequestCancel) {
                        FullScreenAdLoad.isAdRequestCancel = true;
                        FullScreenAdPreload.preloadSequenceFullScreenAd(activity);
                        AdsMasterClass.startNextActivity(activity, intent);
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
                    AdsMasterClass.showAdTag(AdsLogTag.FullScreenAdFailed.name(), "loadApplovinFullScreenFailed - loaded");
                    FullScreenAdPreload.preloadSequenceFullScreenAd(activity);
                    AdsMasterClass.adProgressTimerCancel();
                    AdsMasterClass.dismissAdsProgressDialog();
                    if (!FullScreenAdLoad.isAdRequestCancel) {
                        maxInterstitialAd.showAd();
                    }
                }

                @Override
                public void onAdDisplayed(MaxAd ad) {

                }

                @Override
                public void onAdHidden(MaxAd ad) {
                    AdsMasterClass.showAdTag(AdsLogTag.FullScreenAdFailed.name(), "loadApplovinFullScreenFailed - dismiss");
                    maxInterstitialAd.destroy();
                    AdsMasterClass.startNextActivity(activity, intent);
                }

                @Override
                public void onAdClicked(MaxAd ad) {

                }

                @Override
                public void onAdLoadFailed(String adUnitId, MaxError error) {
                    AdsMasterClass.showAdTag(AdsLogTag.FullScreenAdFailed.name(), "loadApplovinFullScreenFailed - failed " + error.getMessage());
                    FullScreenAdPreload.preloadSequenceFullScreenAd(activity);
                    if (!FullScreenAdLoad.isAdRequestCancel) {
                        AdsMasterClass.adProgressTimerCancel();
                        AdsMasterClass.dismissAdsProgressDialog();
                        AdsMasterClass.startNextActivity(activity, intent);
                    }
                }

                @Override
                public void onAdDisplayFailed(MaxAd ad, MaxError error) {
                    if (!FullScreenAdLoad.isAdRequestCancel) {
                        AdsMasterClass.adProgressTimerCancel();
                        AdsMasterClass.dismissAdsProgressDialog();
                        AdsMasterClass.startNextActivity(activity, intent);
                    }
                }
            });
            maxInterstitialAd.loadAd();
        } else {
            FullScreenAdPreload.preloadSequenceFullScreenAd(activity);
            AdsMasterClass.dismissAdsProgressDialog();
            AdsMasterClass.startNextActivity(activity, intent);
        }
    }

    private static void showQurekaFullAds(final Activity activity, final Intent intent) {
        if (AdsMasterClass.getAdsDataModel() != null && AdsMasterClass.getAdsDataModel().getQureka_url().trim().length() > 0) {
            Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.qureka_full_ad_0);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);

            CardView card_click = dialog.findViewById(R.id.card_click);
            ImageView iv_close = dialog.findViewById(R.id.iv_close_qureka);
            TextView tv_ad = dialog.findViewById(R.id.tv_ad_qureka);

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
                    AdsMasterClass.startNextActivity(activity, intent);
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
            AdsMasterClass.startNextActivity(activity, intent);
        }
    }
}
