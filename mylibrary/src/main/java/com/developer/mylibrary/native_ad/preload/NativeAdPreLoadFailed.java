package com.developer.mylibrary.native_ad.preload;

import android.app.Activity;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.developer.mylibrary.eum_class.AdsLogTag;
import com.developer.mylibrary.eum_class.AllAdsType;
import com.developer.mylibrary.utils.AdsMasterClass;
import com.facebook.ads.Ad;
import com.facebook.ads.NativeAdBase;
import com.facebook.ads.NativeAdListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.admanager.AdManagerAdRequest;
import com.google.android.gms.ads.nativead.NativeAd;

public class NativeAdPreLoadFailed {

    public static void loadPreloadNativeAdOnFailed(Activity activity, LinearLayout linearLayout, String nativeAdSize, String currentAd) {
        String nextFailedAd = AdsMasterClass.getNextNativeFailedAd(currentAd);
        NativeAdPreLoad.showApplovin = nextFailedAd;
        if (nextFailedAd.equals(AllAdsType.g.name())) {
            loadGoogleNative(activity, linearLayout, AdsMasterClass.getGoogleNativeId(activity, AdsMasterClass.getAdsDataModel().getGoogle_native_id()), nativeAdSize);
        } else if (nextFailedAd.equals(AllAdsType.adx.name())) {
            loadGoogleNative(activity, linearLayout, AdsMasterClass.getGoogleNativeId(activity, AdsMasterClass.getAdsDataModel().getAdx_native_id()), nativeAdSize);
        } else if (nextFailedAd.equals(AllAdsType.f.name())) {
            loadFacebookNative(activity, linearLayout, AdsMasterClass.getFacebookNativeId(activity, AdsMasterClass.getAdsDataModel().getFacebook_native_id()), nativeAdSize);
        } else if (nextFailedAd.equals(AllAdsType.a.name())) {
            AdsMasterClass.showAdTag(AdsLogTag.NativeAdPreLoadFailed.name(), "preloadApplovinNativeAdFailed ");
        } else if (nextFailedAd.equals(AllAdsType.q.name())) {
            AdsMasterClass.showAdTag(AdsLogTag.NativeAdPreLoadFailed.name(), "preloadQurekaNativeAdFailed ");
        }
    }

    public static void loadGoogleNative(Activity activity, LinearLayout linearLayout, String adID, String nativeAdSize) {
        if (adID != null && adID.trim().length() > 0) {
            AdLoader.Builder builder = new AdLoader.Builder(activity, adID);
            builder.forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                @Override
                public void onNativeAdLoaded(@NonNull NativeAd nativeAd) {
                    AdsMasterClass.showAdTag(AdsLogTag.NativeAdPreLoadFailed.name(), "preloadGoogleNativeFailed - loaded");
                    NativeAdPreLoad.nativeObject = nativeAd;
                }
            });

            VideoOptions videoOptions = new VideoOptions.Builder().setStartMuted(true).build();
            com.google.android.gms.ads.nativead.NativeAdOptions nativeAdOptions = new com.google.android.gms.ads.nativead.NativeAdOptions.Builder()
                    .setVideoOptions(videoOptions).build();
            builder.withNativeAdOptions(nativeAdOptions);

            builder.withAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    AdsMasterClass.showAdTag(AdsLogTag.NativeAdPreLoadFailed.name(), "preloadGoogleNativeFailed - failed " + loadAdError.getMessage());
                }
            }).build().loadAd(new AdManagerAdRequest.Builder().build());
        }
    }

    public static void loadFacebookNative(Activity activity, LinearLayout linearLayout, String adID, String nativeAdSize) {
        if (adID != null && adID.trim().length() > 0) {
            final com.facebook.ads.NativeAd nativeAd = new com.facebook.ads.NativeAd(activity, adID);
            NativeAdListener nativeAdListener = new NativeAdListener() {
                @Override
                public void onMediaDownloaded(Ad ad) {
                }

                @Override
                public void onError(Ad ad, com.facebook.ads.AdError adError) {
                    AdsMasterClass.showAdTag(AdsLogTag.NativeAdPreLoadFailed.name(), "preloadFacebookNativeFailed - failed " + adError.getErrorMessage());
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    AdsMasterClass.showAdTag(AdsLogTag.NativeAdPreLoadFailed.name(), "preloadFacebookNativeFailed - loaded");
                    NativeAdPreLoad.nativeObject = nativeAd;
                }

                @Override
                public void onAdClicked(Ad ad) {
                }

                @Override
                public void onLoggingImpression(Ad ad) {
                }
            };

            nativeAd.loadAd(
                    nativeAd.buildLoadAdConfig()
                            .withAdListener(nativeAdListener)
                            .withMediaCacheFlag(NativeAdBase.MediaCacheFlag.ALL)
                            .build());
        }
    }
}
