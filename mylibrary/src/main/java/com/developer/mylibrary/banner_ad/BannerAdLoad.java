package com.developer.mylibrary.banner_ad;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowMetrics;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.developer.mylibrary.R;
import com.developer.mylibrary.eum_class.AdsLogTag;
import com.developer.mylibrary.eum_class.AllAdsType;
import com.developer.mylibrary.utils.AdsMasterClass;
import com.developer.mylibrary.utils.AdsShareUtils;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdListener;
import com.facebook.ads.NativeBannerAd;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.admanager.AdManagerAdView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BannerAdLoad {

    public static void loadSequenceBannerAd(Activity activity, LinearLayout linearLayout, boolean isShowExtraBanner, boolean is_collapsible) {
        if (AdsMasterClass.getAdsDataModel().getBanner_show_sequence() != null && AdsMasterClass.getAdsDataModel().getBanner_show_sequence().trim().length() > 0 && !AdsMasterClass.getAdsDataModel().getBanner_show_sequence().equals("0")) {
            String nextAd = isShowExtraBanner ? AdsMasterClass.getNextExtraBannerAd(activity) : AdsMasterClass.getNextBannerAd(activity);
            if (nextAd.equals(AllAdsType.g.name())) {
                loadGoogleBanner(activity, linearLayout, AdsMasterClass.getGoogleBannerId(activity, AdsMasterClass.getAdsDataModel().getGoogle_banner_id()), nextAd, is_collapsible);
            } else if (nextAd.equals(AllAdsType.adx.name())) {
                loadGoogleBanner(activity, linearLayout, AdsMasterClass.getGoogleBannerId(activity, AdsMasterClass.getAdsDataModel().getAdx_banner_id()), nextAd, is_collapsible);
            } else if (nextAd.equals(AllAdsType.adx2.name())) {
                loadGoogleBanner(activity, linearLayout, AdsMasterClass.getGoogleBannerId(activity, AdsMasterClass.getAdsDataModel().getAdx2_banner_id()), nextAd, is_collapsible);
            } else if (nextAd.equals(AllAdsType.ab.name())) {
                loadGoogleBanner(activity, linearLayout, AdsMasterClass.getGoogleBannerId(activity, AdsMasterClass.getAdsDataModel().getAppbaroda_banner_id()), nextAd, is_collapsible);
            } else if (nextAd.equals(AllAdsType.f.name())) {
                loadFacebookBanner(activity, linearLayout, AdsMasterClass.getFacebookBannerId(activity, AdsMasterClass.getAdsDataModel().getFacebook_banner_id()), nextAd, is_collapsible);
            } else if (nextAd.equals(AllAdsType.fnb.name())) {
                loadFacebookNativeBanner(activity, linearLayout, AdsMasterClass.getFacebookNativeBannerId(activity, AdsMasterClass.getAdsDataModel().getFacebook_native_banner_id()), nextAd, is_collapsible);
            } else if (nextAd.equals(AllAdsType.q.name())) {
                loadQurekaBanner(activity, linearLayout);
            } else {
                linearLayout.setVisibility(View.GONE);
            }
        } else {
            linearLayout.setVisibility(View.GONE);
        }
    }

    public static void loadGoogleBanner(Activity activity, LinearLayout linearLayout, String adID, String currentAd, boolean is_collapsible) {
        if (adID != null && adID.trim().length() > 0) {
            linearLayout.setVisibility(View.VISIBLE);
            AdManagerAdView adView = new AdManagerAdView(activity);
            adView.setAdUnitId(adID);
            if (is_collapsible) {
                AdSize adSize = getAdBannerAdSize(activity, linearLayout);
                adView.setAdSize(adSize);
                Bundle extras = new Bundle();
                extras.putString("collapsible", "bottom");
                extras.putString("collapsible_request_id", UUID.randomUUID().toString());
                adView.loadAd(new AdRequest.Builder().addNetworkExtrasBundle(AdMobAdapter.class, extras).build());
            } else {
                adView.setAdSize(AdSize.BANNER);
                adView.loadAd(new AdRequest.Builder().build());
            }
            adView.setAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    AdsMasterClass.showAdTag(AdsLogTag.BannerAdLoad.name(), "loadGoogleBanner - failed " + loadAdError.getMessage());
                    adView.setAdListener(null);
                    adView.destroy();
                    BannerAdFailed.showBannerAdOnFailed(activity, linearLayout, currentAd, is_collapsible);
                }

                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    AdsMasterClass.showAdTag(AdsLogTag.BannerAdLoad.name(), "loadGoogleBanner - loaded");
                }
            });
            linearLayout.addView(adView);
        } else {
            linearLayout.setVisibility(View.GONE);
        }
    }

    public static AdSize getAdBannerAdSize(Activity activity, LinearLayout linearLayout) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            WindowMetrics windowMetrics = null;
            windowMetrics = activity.getWindowManager().getCurrentWindowMetrics();
            Rect bounds = null;
            bounds = windowMetrics.getBounds();
            float adWidthPixels = linearLayout.getWidth();
            if (adWidthPixels == 0f) {
                adWidthPixels = bounds.width();
            }
            float density = activity.getResources().getDisplayMetrics().density;
            int adWidth = (int) (adWidthPixels / density);
            return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, adWidth);
        } else {
            Display display = activity.getWindowManager().getDefaultDisplay();
            DisplayMetrics outMetrics = new DisplayMetrics();
            display.getMetrics(outMetrics);
            float density = outMetrics.density;
            float adWidthPixels = linearLayout.getWidth();
            if (adWidthPixels == 0) {
                adWidthPixels = outMetrics.widthPixels;
            }
            int adWidth = (int) (adWidthPixels / density);
            return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, adWidth);
        }
    }

    public static void loadFacebookBanner(Activity activity, LinearLayout linearLayout, String adID, String currentAd, boolean is_collapsible) {
        if (adID != null && adID.trim().length() > 0) {
            linearLayout.setVisibility(View.VISIBLE);
            com.facebook.ads.AdView adView = new com.facebook.ads.AdView(activity, adID, com.facebook.ads.AdSize.BANNER_HEIGHT_50);
            linearLayout.addView(adView);
            com.facebook.ads.AdListener adListener = new com.facebook.ads.AdListener() {
                @Override
                public void onError(Ad ad, AdError adError) {
                    AdsMasterClass.showAdTag(AdsLogTag.BannerAdLoad.name(), "loadFacebookBanner - failed " + adError.getErrorMessage());
                    BannerAdFailed.showBannerAdOnFailed(activity, linearLayout, currentAd, is_collapsible);
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    AdsMasterClass.showAdTag(AdsLogTag.BannerAdLoad.name(), "loadFacebookBanner - loaded");
                }

                @Override
                public void onAdClicked(Ad ad) {
                }

                @Override
                public void onLoggingImpression(Ad ad) {
                }
            };
            adView.loadAd(adView.buildLoadAdConfig().withAdListener(adListener).build());
        } else {
            linearLayout.setVisibility(View.GONE);
        }
    }

    public static void loadFacebookNativeBanner(Activity activity, LinearLayout linearLayout, String adID, String currentAd, boolean is_collapsible) {
        if (adID != null && adID.trim().length() > 0) {
            NativeBannerAd nativeBannerAd = new NativeBannerAd(activity, adID);
            NativeAdListener nativeAdListener = new NativeAdListener() {
                @Override
                public void onMediaDownloaded(Ad ad) {
                }

                @Override
                public void onError(Ad ad, AdError adError) {
                    AdsMasterClass.showAdTag(AdsLogTag.BannerAdLoad.name(), "loadFacebookNativeBanner - failed " + adError.getErrorMessage());
                    BannerAdFailed.showBannerAdOnFailed(activity, linearLayout, currentAd, is_collapsible);
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    AdsMasterClass.showAdTag(AdsLogTag.BannerAdLoad.name(), "loadFacebookNativeBanner - loaded");
                    if (nativeBannerAd == null || nativeBannerAd != ad) {
                        return;
                    }
                    inflateNativeBannerAd(nativeBannerAd, activity, linearLayout);
                }

                @Override
                public void onAdClicked(Ad ad) {
                }

                @Override
                public void onLoggingImpression(Ad ad) {
                }
            };
            nativeBannerAd.loadAd(
                    nativeBannerAd.buildLoadAdConfig()
                            .withAdListener(nativeAdListener)
                            .build());
        } else {
            linearLayout.setVisibility(View.GONE);
        }
    }

    public static void loadQurekaBanner(Activity activity, LinearLayout linearLayout) {
        AdsMasterClass.showAdTag(AdsLogTag.BannerAdLoad.name(), "loadQurekaBanner - loaded");

        linearLayout.setVisibility(View.VISIBLE);
        linearLayout.removeAllViews();

        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        LinearLayout nativeAdLayout = new LinearLayout(activity);
        // Inflate the Ad view.  The layout referenced is the one you created in the last step.
        LinearLayout adView = (LinearLayout) layoutInflater.inflate(R.layout.custom_qureka_native_mini, nativeAdLayout, false);
        nativeAdLayout.addView(adView);
        linearLayout.addView(nativeAdLayout);

        ImageView iv_media_native = adView.findViewById(R.id.iv_media_native);

        iv_media_native.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdsShareUtils.showQurekaAds(activity);
            }
        });
    }

    public static void inflateNativeBannerAd(NativeBannerAd nativeBannerAd, Activity activity, LinearLayout linearLayout) {
        // Unregister last ad
        nativeBannerAd.unregisterView();
        linearLayout.setVisibility(View.VISIBLE);
        // Add the Ad view into the ad container.
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        NativeAdLayout nativeAdLayout = new NativeAdLayout(activity);
        // Inflate the Ad view.  The layout referenced is the one you created in the last step.
        LinearLayout adView = (LinearLayout) layoutInflater.inflate(R.layout.custom_facebook_native_banner, nativeAdLayout, false);
        nativeAdLayout.addView(adView);
        linearLayout.addView(nativeAdLayout);

        // Add the AdChoices icon
        RelativeLayout adChoicesContainer = adView.findViewById(R.id.ad_choices_container);
        AdOptionsView adOptionsView = new AdOptionsView(activity, nativeBannerAd, nativeAdLayout);
        adChoicesContainer.removeAllViews();
        adChoicesContainer.addView(adOptionsView, 0);

        // Create native UI using the ad metadata.
        TextView nativeAdTitle = adView.findViewById(R.id.native_ad_title);
        TextView nativeAdSocialContext = adView.findViewById(R.id.native_ad_social_context);
        TextView sponsoredLabel = adView.findViewById(R.id.native_ad_sponsored_label);
        MediaView nativeAdIconView = adView.findViewById(R.id.native_icon_view);
        Button nativeAdCallToAction = adView.findViewById(R.id.native_ad_call_to_action);

        // Set the Text.
        nativeAdCallToAction.setText(nativeBannerAd.getAdCallToAction());
        nativeAdCallToAction.setVisibility(
                nativeBannerAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
        nativeAdTitle.setText(nativeBannerAd.getAdvertiserName());
        nativeAdSocialContext.setText(nativeBannerAd.getAdSocialContext());
        sponsoredLabel.setText(nativeBannerAd.getSponsoredTranslation());

        // Register the Title and CTA button to listen for clicks.
        List<View> clickableViews = new ArrayList<>();
        clickableViews.add(nativeAdTitle);
        clickableViews.add(nativeAdCallToAction);
        nativeBannerAd.registerViewForInteraction(adView, nativeAdIconView, clickableViews);
    }
}
