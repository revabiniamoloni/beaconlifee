package com.developer.mylibrary.fullscreen_ad.normal;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.os.Handler;
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
import com.developer.mylibrary.utils.AdsConstant;
import com.developer.mylibrary.utils.AdsMasterClass;
import com.developer.mylibrary.utils.AdsShareUtils;
import com.facebook.ads.Ad;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class FullScreenAdLoad {

    public static Activity activityLoading;
    public static Intent intentLoading;

    public static boolean isAdRequestCancel = false;

    public static void loadSequenceFullScreenAd(Activity activity, Intent intent) {
        activityLoading = activity;
        intentLoading = intent;
        String nextAd = AdsMasterClass.getNextFullScreenAd(activity);
        if (nextAd.equals(AllAdsType.g.name())) {
            loadGoogleFullScreen(activity, intent, AdsMasterClass.getGoogleFullScreenId(activity, AdsMasterClass.getAdsDataModel().getGoogle_interstitial_id()), nextAd);
        } else if (nextAd.equals(AllAdsType.adx.name())) {
            loadGoogleFullScreen(activity, intent, AdsMasterClass.getGoogleFullScreenId(activity, AdsMasterClass.getAdsDataModel().getAdx_interstitial_id()), nextAd);
        }  else if (nextAd.equals(AllAdsType.f.name())) {
            loadFacebookFullScreen(activity, intent, AdsMasterClass.getFacebookFullScreenId(activity, AdsMasterClass.getAdsDataModel().getFacebook_interstitial_id()), nextAd);
        } else if (nextAd.equals(AllAdsType.a.name())) {
            loadApplovinFullScreen(activity, intent, AdsMasterClass.getApplovinFullScreenId(activity, AdsMasterClass.getAdsDataModel().getApplovin_interstitial_id()), nextAd);
        } else if (nextAd.equals(AllAdsType.q.name())) {
            showQurekaFullAds(activity, intent);
        } else {
            AdsMasterClass.startNextActivity(activity, intent);
        }
    }

    public static void loadGoogleFullScreen(final Activity activity, final Intent intent, String adID, String currentAd) {
        if (adID != null && adID.trim().length() > 0) {
            AdsMasterClass.showAdProgressDialog(activity);
            isAdRequestCancel = false;
            AdsMasterClass.adProgressTimerCancel();
            AdsMasterClass.countDownTimer = new CountDownTimer(20000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    AdsMasterClass.showAdTag(AdsLogTag.FullScreenAdLoad.name(), "onTick: Remain " + millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    AdsMasterClass.dismissAdsProgressDialog();
                    AdsMasterClass.adProgressTimerCancel();
                    if (!isAdRequestCancel) {
                        isAdRequestCancel = true;
                        AdsMasterClass.startNextActivity(activity, intent);
                    }
                }
            }.start();

            AdRequest adRequest = new AdRequest.Builder().build();

            InterstitialAd.load(activity, adID, adRequest, new InterstitialAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                    super.onAdLoaded(interstitialAd);
                    AdsMasterClass.showAdTag(AdsLogTag.FullScreenAdLoad.name(), "loadGoogleFullScreen - loaded");

                    AdsMasterClass.adProgressTimerCancel();
                    AdsMasterClass.dismissAdsProgressDialog();
                    if (!isAdRequestCancel) {
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
                            AdsMasterClass.showAdTag(AdsLogTag.FullScreenAdLoad.name(), "loadGoogleFullScreen - dismiss");
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
                    AdsMasterClass.showAdTag(AdsLogTag.FullScreenAdLoad.name(), "loadGoogleFullScreen - failed " + loadAdError.getMessage());
                    AdsMasterClass.adProgressTimerCancel();
                    if (!isAdRequestCancel) {
                        FullScreenAdFailed.showFullScreenAdOnFailed(activity, intent, currentAd);
                    }
                }
            });
        } else {
            AdsMasterClass.startNextActivity(activity, intent);
        }
    }

    public static void loadFacebookFullScreen(Activity activity, Intent intent, String adID, String currentAd) {
        if (adID != null && adID.trim().length() > 0) {
            AdsMasterClass.showAdProgressDialog(activity);
            isAdRequestCancel = false;
            AdsMasterClass.adProgressTimerCancel();
            AdsMasterClass.countDownTimer = new CountDownTimer(20000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    AdsMasterClass.showAdTag(AdsLogTag.FullScreenAdLoad.name(), "onTick: Remain " + millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    AdsMasterClass.dismissAdsProgressDialog();
                    AdsMasterClass.adProgressTimerCancel();
                    if (!isAdRequestCancel) {
                        isAdRequestCancel = true;
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
                    AdsMasterClass.showAdTag(AdsLogTag.FullScreenAdLoad.name(), "loadFacebookFullScreen - dismiss");
                    AdsMasterClass.startNextActivity(activity, intent);
                }

                @Override
                public void onError(Ad ad, com.facebook.ads.AdError adError) {
                    AdsMasterClass.showAdTag(AdsLogTag.FullScreenAdLoad.name(), "loadFacebookFullScreen - failed " + adError.getErrorMessage());
                    AdsMasterClass.adProgressTimerCancel();
                    if (!isAdRequestCancel) {
                        FullScreenAdFailed.showFullScreenAdOnFailed(activity, intent, currentAd);
                    }
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    AdsMasterClass.showAdTag(AdsLogTag.FullScreenAdLoad.name(), "loadFacebookFullScreen - loaded");
                    AdsMasterClass.adProgressTimerCancel();
                    AdsMasterClass.dismissAdsProgressDialog();
                    if (!isAdRequestCancel) {
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
            AdsMasterClass.startNextActivity(activity, intent);
        }
    }

    public static void loadApplovinFullScreen(Activity activity, Intent intent, String adID, String adType) {
        if (adID != null && adID.trim().length() > 0) {
            AdsMasterClass.showAdProgressDialog(activity);
            isAdRequestCancel = false;
            AdsMasterClass.adProgressTimerCancel();
            AdsMasterClass.countDownTimer = new CountDownTimer(20000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    AdsMasterClass.showAdTag(AdsLogTag.FullScreenAdLoad.name(), "onTick: Remain " + millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    AdsMasterClass.dismissAdsProgressDialog();
                    AdsMasterClass.adProgressTimerCancel();
                    if (!isAdRequestCancel) {
                        isAdRequestCancel = true;
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
                    AdsMasterClass.showAdTag(AdsLogTag.FullScreenAdLoad.name(), "loadApplovinFullScreen - loaded");
                    AdsMasterClass.adProgressTimerCancel();
                    AdsMasterClass.dismissAdsProgressDialog();
                    if (!isAdRequestCancel) {
                        maxInterstitialAd.showAd();
                    }
                }

                @Override
                public void onAdDisplayed(MaxAd ad) {

                }

                @Override
                public void onAdHidden(MaxAd ad) {
                    AdsMasterClass.showAdTag(AdsLogTag.FullScreenAdLoad.name(), "loadApplovinFullScreen - dismiss");
                    maxInterstitialAd.destroy();
                    AdsMasterClass.startNextActivity(activity, intent);
                }

                @Override
                public void onAdClicked(MaxAd ad) {

                }

                @Override
                public void onAdLoadFailed(String adUnitId, MaxError error) {
                    AdsMasterClass.showAdTag(AdsLogTag.FullScreenAdLoad.name(), "loadApplovinFullScreen - failed " + error.getMessage());
                    AdsMasterClass.adProgressTimerCancel();
                    if (!isAdRequestCancel) {
                        FullScreenAdFailed.showFullScreenAdOnFailed(activity, intent, adType);
                    }
                }

                @Override
                public void onAdDisplayFailed(MaxAd ad, MaxError error) {
                    if (!isAdRequestCancel) {
                        AdsMasterClass.adProgressTimerCancel();
                        AdsMasterClass.dismissAdsProgressDialog();
                        AdsMasterClass.startNextActivity(activity, intent);
                    }
                }
            });
            maxInterstitialAd.loadAd();
        } else {
            AdsMasterClass.startNextActivity(activity, intent);
        }
    }

    private static void showQurekaFullAds(final Activity activity, final Intent intent) {
        if (AdsMasterClass.getAdsDataModel() != null && AdsMasterClass.getAdsDataModel().getQureka_url().trim().length() > 0) {
            AdsMasterClass.showAdProgressDialog(activity);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    AdsMasterClass.dismissAdsProgressDialog();

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
                }
            }, 500);
        } else {
            AdsMasterClass.startNextActivity(activity, intent);
        }
    }
}
