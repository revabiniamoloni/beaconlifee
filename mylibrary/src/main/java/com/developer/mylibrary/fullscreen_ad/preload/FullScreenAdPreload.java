package com.developer.mylibrary.fullscreen_ad.preload;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.developer.mylibrary.fullscreen_ad.normal.FullScreenAdFailed;
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

public class FullScreenAdPreload {

    public static Activity activityPreload;
    public static Intent intentPreload;

    public static AdManagerInterstitialAd interstitialAd;
    public static com.facebook.ads.InterstitialAd fbInterstitialAd;
    public static MaxInterstitialAd maxInterstitialAd;

    public static void showPreloadSequenceFullScreenAd(Activity activity, Intent intent) {
        activityPreload = activity;
        intentPreload = intent;
        String ad = AdsMasterClass.getNextShowPreloadFullScreenAd(activity);
        if (ad.equals(AllAdsType.g.name()) || ad.equals(AllAdsType.adx.name()) || ad.equals(AllAdsType.adx2.name()) || ad.equals(AllAdsType.ab.name())) {
            if (interstitialAd != null) {
                interstitialAd.show(activity);
            } else {
                AdsMasterClass.showAdProgressDialog(activity);
                FullScreenAdFailed.showFullScreenAdOnFailed(activity, intent, ad);
            }
        } else if (ad.equals(AllAdsType.f.name())) {
            if (fbInterstitialAd != null && fbInterstitialAd.isAdLoaded() && !fbInterstitialAd.isAdInvalidated()) {
                fbInterstitialAd.show();
            } else {
                AdsMasterClass.showAdProgressDialog(activity);
                FullScreenAdFailed.showFullScreenAdOnFailed(activity, intent, ad);
            }
        } else if (ad.equals(AllAdsType.a.name())) {
            if (maxInterstitialAd != null && maxInterstitialAd.isReady()) {
                maxInterstitialAd.showAd();
            } else {
                AdsMasterClass.showAdProgressDialog(activity);
                FullScreenAdFailed.showFullScreenAdOnFailed(activity, intent, ad);
            }
        } else if (ad.equals(AllAdsType.q.name())) {
            preloadSequenceFullScreenAd(activity);
            showQurekaFullAds(activity, intent);
        } else {
            preloadSequenceFullScreenAd(activity);
            AdsMasterClass.startNextActivity(activity, intent);
        }
    }

    public static void preloadSequenceFullScreenAd(Activity activity) {
        if (AdsMasterClass.getAdsDataModel() != null && AdsMasterClass.getAdsDataModel().getInterstitial_show_ads_loading() == 0) {
            if (AdsMasterClass.getAdsDataModel().getInterstitial_show_sequence() != null && AdsMasterClass.getAdsDataModel().getInterstitial_show_sequence().trim().length() > 0) {
                activityPreload = activity;
                String ad = AdsMasterClass.getNextPreloadFullScreenAd(activity);
                if (ad.equals(AllAdsType.g.name())) {
                    loadGoogleFullScreen(activity, AdsMasterClass.getGoogleFullScreenId(activity, AdsMasterClass.getAdsDataModel().getGoogle_interstitial_id()));
                } else if (ad.equals(AllAdsType.adx.name())) {
                    loadGoogleFullScreen(activity, AdsMasterClass.getGoogleFullScreenId(activity, AdsMasterClass.getAdsDataModel().getAdx_interstitial_id()));
                } else if (ad.equals(AllAdsType.adx2.name())) {
                    loadGoogleFullScreen(activity, AdsMasterClass.getGoogleFullScreenId(activity, AdsMasterClass.getAdsDataModel().getAdx2_interstitial_id()));
                } else if (ad.equals(AllAdsType.ab.name())) {
                    loadGoogleFullScreen(activity, AdsMasterClass.getGoogleFullScreenId(activity, AdsMasterClass.getAdsDataModel().getAppbaroda_interstitial_id()));
                } else if (ad.equals(AllAdsType.f.name())) {
                    loadFacebookFullScreen(activity, AdsMasterClass.getFacebookFullScreenId(activity, AdsMasterClass.getAdsDataModel().getFacebook_interstitial_id()));
                } else if (ad.equals(AllAdsType.a.name())) {
                    loadApplovinFullScreen(activity, AdsMasterClass.getApplovinFullScreenId(activity, AdsMasterClass.getAdsDataModel().getApplovin_interstitial_id()));
                }
            }
        }
    }

    public static void loadGoogleFullScreen(final Activity activity, String adID) {
        if (adID != null && adID.trim().length() > 0) {
            AdManagerAdRequest adRequest = new AdManagerAdRequest.Builder().build();

            AdManagerInterstitialAd.load(activity, adID, adRequest, new AdManagerInterstitialAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull AdManagerInterstitialAd mInterstitialAd) {
                    super.onAdLoaded(mInterstitialAd);
                    AdsMasterClass.showAdTag(AdsLogTag.FullScreenAdPreload.name(), "preloadGoogleFullScreen - loaded");

                    interstitialAd = mInterstitialAd;
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
                            AdsMasterClass.showAdTag(AdsLogTag.FullScreenAdPreload.name(), "preloadGoogleFullScreen - dismiss");
                            interstitialAd = null;
                            preloadSequenceFullScreenAd(activityPreload);
                            AdsMasterClass.startNextActivity(activityPreload, intentPreload);
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
                    AdsMasterClass.showAdTag(AdsLogTag.FullScreenAdPreload.name(), "preloadGoogleFullScreen - failed " + loadAdError.getMessage());
                }
            });
        }
    }

    public static void loadFacebookFullScreen(Activity activity, String adID) {
        if (adID != null && adID.trim().length() > 0) {
            fbInterstitialAd = new com.facebook.ads.InterstitialAd(activity, adID);

            InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
                @Override
                public void onInterstitialDisplayed(Ad ad) {

                }

                @Override
                public void onInterstitialDismissed(Ad ad) {
                    AdsMasterClass.showAdTag(AdsLogTag.FullScreenAdPreload.name(), "preloadFacebookFullScreen - dismiss");
                    if (fbInterstitialAd != null) {
                        fbInterstitialAd.destroy();
                    }
                    preloadSequenceFullScreenAd(activityPreload);
                    AdsMasterClass.startNextActivity(activityPreload, intentPreload);
                }

                @Override
                public void onError(Ad ad, com.facebook.ads.AdError adError) {
                    AdsMasterClass.showAdTag(AdsLogTag.FullScreenAdPreload.name(), "preloadFacebookFullScreen - failed " + adError.getErrorMessage());
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    AdsMasterClass.showAdTag(AdsLogTag.FullScreenAdPreload.name(), "preloadFacebookFullScreen - loaded");
                }

                @Override
                public void onAdClicked(Ad ad) {

                }

                @Override
                public void onLoggingImpression(Ad ad) {

                }
            };

            fbInterstitialAd.loadAd(
                    fbInterstitialAd.buildLoadAdConfig()
                            .withAdListener(interstitialAdListener)
                            .build());
        }
    }

    public static void loadApplovinFullScreen(Activity activity, String adID) {
        if (adID != null && adID.trim().length() > 0) {
            maxInterstitialAd = new MaxInterstitialAd(adID, activity);
            maxInterstitialAd.setListener(new MaxAdViewAdListener() {
                @Override
                public void onAdExpanded(MaxAd ad) {

                }

                @Override
                public void onAdCollapsed(MaxAd ad) {

                }

                @Override
                public void onAdLoaded(MaxAd ad) {
                    AdsMasterClass.showAdTag(AdsLogTag.FullScreenAdPreload.name(), "preloadApplovinFullScreen - loaded");
                }

                @Override
                public void onAdDisplayed(MaxAd ad) {

                }

                @Override
                public void onAdHidden(MaxAd ad) {
                    AdsMasterClass.showAdTag(AdsLogTag.FullScreenAdPreload.name(), "preloadApplovinFullScreen - dismiss");
                    maxInterstitialAd.destroy();
                    preloadSequenceFullScreenAd(activityPreload);
                    AdsMasterClass.startNextActivity(activityPreload, intentPreload);
                }

                @Override
                public void onAdClicked(MaxAd ad) {

                }

                @Override
                public void onAdLoadFailed(String adUnitId, MaxError error) {
                    AdsMasterClass.showAdTag(AdsLogTag.FullScreenAdPreload.name(), "preloadApplovinFullScreen - failed " + error.getMessage());
                }

                @Override
                public void onAdDisplayFailed(MaxAd ad, MaxError error) {
                    AdsMasterClass.showAdTag(AdsLogTag.FullScreenAdPreload.name(), "preloadApplovinFullScreen - displayFailed " + error.getMessage());
                    maxInterstitialAd.destroy();
                    preloadSequenceFullScreenAd(activityPreload);
                    AdsMasterClass.startNextActivity(activityPreload, intentPreload);
                }
            });
            maxInterstitialAd.loadAd();
        }
    }

    private static void showQurekaFullAds(final Activity activity, final Intent intent) {
        if (AdsMasterClass.getAdsDataModel() != null && AdsMasterClass.getAdsDataModel().getIs_direct_qureka_open() != 1) {
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
        } else {
            AdsShareUtils.showDirectQurekaAds(activity, intent);
        }
    }
}
