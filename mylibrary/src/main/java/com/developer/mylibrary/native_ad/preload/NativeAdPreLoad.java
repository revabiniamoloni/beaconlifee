package com.developer.mylibrary.native_ad.preload;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.nativeAds.MaxNativeAdListener;
import com.applovin.mediation.nativeAds.MaxNativeAdLoader;
import com.applovin.mediation.nativeAds.MaxNativeAdView;
import com.applovin.mediation.nativeAds.MaxNativeAdViewBinder;
import com.developer.mylibrary.R;
import com.developer.mylibrary.eum_class.AdsLogTag;
import com.developer.mylibrary.eum_class.AllAdsType;
import com.developer.mylibrary.eum_class.NativeAdSize;
import com.developer.mylibrary.native_ad.normal.NativeAdLoad;
import com.developer.mylibrary.utils.AdsConstant;
import com.developer.mylibrary.utils.AdsMasterClass;
import com.developer.mylibrary.utils.AdsPreference;
import com.developer.mylibrary.utils.AdsShareUtils;
import com.facebook.ads.Ad;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.NativeAdBase;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.admanager.AdManagerAdRequest;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NativeAdPreLoad {

    public static Object nativeObject;
    public static String showApplovin = "";

    public static void showPreloadSequenceNativeAd(Activity activity, RelativeLayout relativeLayout, LinearLayout linearLayout, String nativeAdSize) {
        AdsMasterClass.showAdTag(AdsLogTag.NativeAdPreLoad.name(), "showPreloadSequenceNativeAd - " + showApplovin);
        NativeAdLoad.hideLoadingAdsLayout(relativeLayout);
        if (showApplovin.equals(AllAdsType.a.name())) {
            loadApplovinNative(activity, relativeLayout, linearLayout, AdsMasterClass.getApplovinNativeId(activity, AdsMasterClass.getAdsDataModel().getApplovin_native_id()), AllAdsType.a.name(), nativeAdSize);
        } else if (showApplovin.equals(AllAdsType.q.name())) {
            loadQurekaNative(activity, relativeLayout, linearLayout, nativeAdSize);
        } else {
            if (nativeObject != null) {
                if (nativeObject instanceof NativeAd) {
                    showApplovin = "";
                    relativeLayout.setVisibility(View.VISIBLE);
                    linearLayout.setVisibility(View.VISIBLE);
                    NativeAdView adView;
                    if (nativeAdSize.equals(NativeAdSize.EXTRA.name())) {
                        adView = (NativeAdView) activity.getLayoutInflater().inflate(R.layout.custom_google_native_extra, (ViewGroup) null);
                    } else if (nativeAdSize.equals(NativeAdSize.MINI.name())) {
                        adView = (NativeAdView) activity.getLayoutInflater().inflate(R.layout.custom_google_native_mini, (ViewGroup) null);
                    } else if (nativeAdSize.equals(NativeAdSize.MIDDLE.name())) {
                        adView = (NativeAdView) activity.getLayoutInflater().inflate(R.layout.custom_google_native_middle, (ViewGroup) null);
                    } else {
                        adView = (NativeAdView) activity.getLayoutInflater().inflate(R.layout.custom_google_native_large, (ViewGroup) null);
                    }
                    populateNativeAdView(activity, (NativeAd) nativeObject, adView, nativeAdSize);
                    linearLayout.removeAllViews();
                    linearLayout.addView(adView);
                } else if (nativeObject instanceof com.facebook.ads.NativeAd) {
                    showApplovin = "";
                    if (((com.facebook.ads.NativeAd) nativeObject).isAdLoaded() && !((com.facebook.ads.NativeAd) nativeObject).isAdInvalidated()) {
                        relativeLayout.setVisibility(View.VISIBLE);
                        linearLayout.setVisibility(View.VISIBLE);
                        inflateNativeAdView((com.facebook.ads.NativeAd) nativeObject, activity, linearLayout, nativeAdSize);
                    }
                }
            }
        }
        NativeAdPreLoad.preloadSequenceNativeAd(activity, relativeLayout, linearLayout, nativeAdSize);
    }

    public static void preloadSequenceNativeAd(Activity activity, RelativeLayout relativeLayout, LinearLayout linearLayout, String nativeAdSize) {
        NativeAdLoad.hideLoadingAdsLayout(relativeLayout);
        if (AdsMasterClass.getAdsDataModel().getNative_show_sequence() != null && AdsMasterClass.getAdsDataModel().getNative_show_sequence().trim().length() > 0 && !AdsMasterClass.getAdsDataModel().getNative_show_sequence().equals("0")) {
            String nextAd = AdsMasterClass.getNextNativeAd(activity);
            showApplovin = nextAd;
            AdsMasterClass.showAdTag(AdsLogTag.NativeAdPreLoad.name(), "preloadSequenceNativeAd - " + nextAd);
            if (nextAd.equals(AllAdsType.g.name())) {
                loadGoogleNative(activity, linearLayout, AdsMasterClass.getGoogleNativeId(activity, AdsMasterClass.getAdsDataModel().getGoogle_native_id()), nextAd, nativeAdSize);
            } else if (nextAd.equals(AllAdsType.adx.name())) {
                loadGoogleNative(activity, linearLayout, AdsMasterClass.getGoogleNativeId(activity, AdsMasterClass.getAdsDataModel().getAdx_native_id()), nextAd, nativeAdSize);
            } else if (nextAd.equals(AllAdsType.adx2.name())) {
                loadGoogleNative(activity, linearLayout, AdsMasterClass.getGoogleNativeId(activity, AdsMasterClass.getAdsDataModel().getAdx2_native_id()), nextAd, nativeAdSize);
            } else if (nextAd.equals(AllAdsType.ab.name())) {
                loadGoogleNative(activity, linearLayout, AdsMasterClass.getGoogleNativeId(activity, AdsMasterClass.getAdsDataModel().getAppbaroda_native_id()), nextAd, nativeAdSize);
            } else if (nextAd.equals(AllAdsType.f.name())) {
                loadFacebookNative(activity, linearLayout, AdsMasterClass.getFacebookNativeId(activity, AdsMasterClass.getAdsDataModel().getFacebook_native_id()), nextAd, nativeAdSize);
            } else if (nextAd.equals(AllAdsType.a.name())) {
                AdsMasterClass.showAdTag(AdsLogTag.NativeAdPreLoad.name(), "preloadApplovinNativeAd ");
            } else if (nextAd.equals(AllAdsType.q.name())) {
                AdsMasterClass.showAdTag(AdsLogTag.NativeAdPreLoad.name(), "preloadQurekaNativeAd ");
            } else {
                AdsMasterClass.showAdTag(AdsLogTag.NativeAdPreLoad.name(), "preloadSequenceNativeAd - Wrong Seq");
            }
        } else {
            linearLayout.setVisibility(View.GONE);
        }
    }

    public static void loadGoogleNative(Activity activity, LinearLayout linearLayout, String adID, String currentAd, String nativeAdSize) {
        if (adID != null && adID.trim().length() > 0) {
            AdLoader.Builder builder = new AdLoader.Builder(activity, adID);
            builder.forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                @Override
                public void onNativeAdLoaded(@NonNull NativeAd nativeAd) {
                    AdsMasterClass.showAdTag(AdsLogTag.NativeAdPreLoad.name(), "preloadGoogleNative - loaded");
                    nativeObject = nativeAd;
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
                    AdsMasterClass.showAdTag(AdsLogTag.NativeAdPreLoad.name(), "preloadGoogleNative - failed " + loadAdError.getMessage());
                    NativeAdPreLoadFailed.loadPreloadNativeAdOnFailed(activity, linearLayout, nativeAdSize, currentAd);
                }
            }).build().loadAd(new AdManagerAdRequest.Builder().build());
        } else {
            linearLayout.setVisibility(View.GONE);
        }
    }

    public static void loadFacebookNative(Activity activity, LinearLayout linearLayout, String adID, String adType, String nativeAdSize) {
        if (adID != null && adID.trim().length() > 0) {
            final com.facebook.ads.NativeAd nativeAd = new com.facebook.ads.NativeAd(activity, adID);
            NativeAdListener nativeAdListener = new NativeAdListener() {
                @Override
                public void onMediaDownloaded(Ad ad) {
                }

                @Override
                public void onError(Ad ad, com.facebook.ads.AdError adError) {
                    AdsMasterClass.showAdTag(AdsLogTag.NativeAdPreLoad.name(), "preloadFacebookNative - failed " + adError.getErrorMessage());
                    NativeAdPreLoadFailed.loadPreloadNativeAdOnFailed(activity, linearLayout, nativeAdSize, adType);
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    AdsMasterClass.showAdTag(AdsLogTag.NativeAdPreLoad.name(), "preloadFacebookNative - loaded");
                    nativeObject = nativeAd;
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
        } else {
            linearLayout.setVisibility(View.GONE);
        }
    }

    public static void loadApplovinNative(Activity activity, RelativeLayout relativeLayout, LinearLayout linearLayout, String adID, String currentAd, String nativeAdSize) {
        if (adID != null && adID.trim().length() > 0) {
            MaxNativeAdLoader nativeAdLoader = new MaxNativeAdLoader(adID, activity);
            nativeAdLoader.setNativeAdListener(new MaxNativeAdListener() {
                @Override
                public void onNativeAdLoaded(MaxNativeAdView maxNativeAdView, MaxAd maxAd) {
                    super.onNativeAdLoaded(maxNativeAdView, maxAd);
                    AdsMasterClass.showAdTag(AdsLogTag.NativeAdPreLoad.name(), "preloadApplovinNative - loaded");
                    relativeLayout.setVisibility(View.VISIBLE);
                    linearLayout.setVisibility(View.VISIBLE);
                    linearLayout.removeAllViews();
                    linearLayout.addView(maxNativeAdView);

                    if (AdsMasterClass.getAdsDataModel() != null && AdsMasterClass.getAdsDataModel().getNative_bg_color().trim().length() > 0 && !AdsMasterClass.getAdsDataModel().getNative_bg_color().equals("0")) {
                        maxNativeAdView.getMainView().setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(AdsMasterClass.getAdsDataModel().getNative_bg_color())));
                    } else {
                        maxNativeAdView.getMainView().setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(AdsPreference.getString(activity, AdsConstant.NATIVE_BG_COLOR, AdsConstant.getHexStringColor(activity.getResources().getColor(R.color.ad_bg_color))))));
                    }
                    if (AdsMasterClass.getAdsDataModel() != null && AdsMasterClass.getAdsDataModel().getNative_button_color().trim().length() > 0 && !AdsMasterClass.getAdsDataModel().getNative_button_color().equals("0")) {
                        maxNativeAdView.getCallToActionButton().setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(AdsMasterClass.getAdsDataModel().getNative_button_color())));
                    } else {
                        maxNativeAdView.getCallToActionButton().setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(AdsPreference.getString(activity, AdsConstant.NATIVE_BUTTON_COLOR, AdsConstant.getHexStringColor(activity.getResources().getColor(R.color.ad_button_color))))));
                    }
                    maxNativeAdView.getAdvertiserTextView().setTextColor(ColorStateList.valueOf(Color.parseColor(AdsPreference.getString(activity, AdsConstant.NATIVE_TEXT_COLOR, AdsConstant.getHexStringColor(activity.getResources().getColor(R.color.ad_text_color))))));
                    maxNativeAdView.getTitleTextView().setTextColor(ColorStateList.valueOf(Color.parseColor(AdsPreference.getString(activity, AdsConstant.NATIVE_TEXT_COLOR, AdsConstant.getHexStringColor(activity.getResources().getColor(R.color.ad_text_color))))));
                    maxNativeAdView.getBodyTextView().setTextColor(ColorStateList.valueOf(Color.parseColor(AdsPreference.getString(activity, AdsConstant.NATIVE_TEXT_COLOR, AdsConstant.getHexStringColor(activity.getResources().getColor(R.color.ad_text_color))))));
                    maxNativeAdView.getCallToActionButton().setTextColor(ColorStateList.valueOf(Color.parseColor(AdsPreference.getString(activity, AdsConstant.NATIVE_BUTTON_TEXT_COLOR, AdsConstant.getHexStringColor(activity.getResources().getColor(R.color.ad_button_text_color))))));

                    if (nativeAdSize.equals(NativeAdSize.LARGE.name())) {
                        int heightInPx = (int) (300 * activity.getResources().getDisplayMetrics().density);
                        maxNativeAdView.getMainView().setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, heightInPx));
                    }
                }

                @Override
                public void onNativeAdLoadFailed(String s, MaxError maxError) {
                    super.onNativeAdLoadFailed(s, maxError);
                    AdsMasterClass.showAdTag(AdsLogTag.NativeAdPreLoad.name(), "preloadApplovinNative - failed " + maxError.getMessage());
                    NativeAdPreLoadFailed.loadPreloadNativeAdOnFailed(activity, linearLayout, nativeAdSize, currentAd);
                }
            });
            if (nativeAdSize.equals(NativeAdSize.LARGE.name())) {
                nativeAdLoader.loadAd();
            } else {
                nativeAdLoader.loadAd(inflateMaxNativeAdView(activity, nativeAdSize));
            }
        } else {
            linearLayout.setVisibility(View.GONE);
        }
    }

    public static void loadQurekaNative(Activity activity, RelativeLayout relativeLayout, LinearLayout linearLayout, String nativeAdSize) {
        relativeLayout.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.VISIBLE);
        linearLayout.removeAllViews();

        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        LinearLayout nativeAdLayout = new LinearLayout(activity);
        // Inflate the Ad view.  The layout referenced is the one you created in the last step.
        LinearLayout adView;
        if (nativeAdSize.equals(NativeAdSize.EXTRA.name())) {
            adView = (LinearLayout) layoutInflater.inflate(R.layout.custom_qureka_native_extra, nativeAdLayout, false);
        } else if (nativeAdSize.equals(NativeAdSize.MINI.name())) {
            adView = (LinearLayout) layoutInflater.inflate(R.layout.custom_qureka_native_mini, nativeAdLayout, false);
        } else if (nativeAdSize.equals(NativeAdSize.MIDDLE.name())) {
            adView = (LinearLayout) layoutInflater.inflate(R.layout.custom_qureka_native_middle, nativeAdLayout, false);
        } else {
            adView = (LinearLayout) layoutInflater.inflate(R.layout.custom_qureka_native_large, nativeAdLayout, false);
        }
        nativeAdLayout.addView(adView);
        linearLayout.addView(nativeAdLayout);

        ImageView iv_media_native = adView.findViewById(R.id.iv_media_native);
        if (AdsMasterClass.getAdsDataModel() != null && !AdsMasterClass.getAdsDataModel().getQureka_ads_image().trim().isEmpty()) {
            Integer[] qureka_image_list = new Integer[]{};
            if (nativeAdSize.equals(NativeAdSize.EXTRA.name())) {
                qureka_image_list = AdsConstant.QUREKA_NATIVE_MIDDLE_LIST;
            } else if (nativeAdSize.equals(NativeAdSize.MINI.name())) {
                qureka_image_list = AdsConstant.QUREKA_NATIVE_MINI_LIST;
            } else if (nativeAdSize.equals(NativeAdSize.MIDDLE.name())) {
                qureka_image_list = AdsConstant.QUREKA_NATIVE_MIDDLE_LIST;
            } else {
                qureka_image_list = AdsConstant.QUREKA_NATIVE_LARGE_LIST;
            }
            if (AdsMasterClass.getAdsDataModel().getQureka_ads_image().trim().equals("0")) {
                iv_media_native.setImageResource(qureka_image_list[Integer.parseInt(AdsMasterClass.getAdsDataModel().getQureka_ads_image())]);
            } else {
                int randomValue = new Random().nextInt(qureka_image_list.length);
                if (randomValue == 0) {
                    iv_media_native.setImageResource(qureka_image_list[1]);
                } else {
                    iv_media_native.setImageResource(qureka_image_list[randomValue]);
                }
            }
        }

        iv_media_native.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdsShareUtils.showQurekaAds(activity);
            }
        });
    }

    public static void populateNativeAdView(Activity activity, NativeAd nativeAd, NativeAdView adView, String nativeAdSize) {
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        if (AdsMasterClass.getAdsDataModel() != null && AdsMasterClass.getAdsDataModel().getNative_bg_color().trim().length() > 0 && !AdsMasterClass.getAdsDataModel().getNative_bg_color().equals("0")) {
            adView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(AdsMasterClass.getAdsDataModel().getNative_bg_color())));
        } else {
            adView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(AdsPreference.getString(activity, AdsConstant.NATIVE_BG_COLOR, AdsConstant.getHexStringColor(activity.getResources().getColor(R.color.ad_bg_color))))));
        }
        if (AdsMasterClass.getAdsDataModel() != null && AdsMasterClass.getAdsDataModel().getNative_button_color().trim().length() > 0 && !AdsMasterClass.getAdsDataModel().getNative_button_color().equals("0")) {
            adView.findViewById(R.id.ad_call_to_action).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(AdsMasterClass.getAdsDataModel().getNative_button_color())));
        } else {
            adView.findViewById(R.id.ad_call_to_action).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(AdsPreference.getString(activity, AdsConstant.NATIVE_BUTTON_COLOR, AdsConstant.getHexStringColor(activity.getResources().getColor(R.color.ad_button_color))))));
        }

        ((TextView) adView.findViewById(R.id.ad_headline)).setTextColor(ColorStateList.valueOf(Color.parseColor(AdsPreference.getString(activity, AdsConstant.NATIVE_TEXT_COLOR, AdsConstant.getHexStringColor(activity.getResources().getColor(R.color.ad_text_color))))));
        ((TextView) adView.findViewById(R.id.ad_body)).setTextColor(ColorStateList.valueOf(Color.parseColor(AdsPreference.getString(activity, AdsConstant.NATIVE_TEXT_COLOR, AdsConstant.getHexStringColor(activity.getResources().getColor(R.color.ad_text_color))))));
        ((AppCompatButton) adView.findViewById(R.id.ad_call_to_action)).setTextColor(ColorStateList.valueOf(Color.parseColor(AdsPreference.getString(activity, AdsConstant.NATIVE_BUTTON_TEXT_COLOR, AdsConstant.getHexStringColor(activity.getResources().getColor(R.color.ad_button_text_color))))));

        if (nativeAdSize.equals(NativeAdSize.LARGE.name())) {
            adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));
            adView.setStoreView(adView.findViewById(R.id.ad_store));
            adView.setPriceView(adView.findViewById(R.id.ad_price));
            adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        }
        if (nativeAdSize.equals(NativeAdSize.LARGE.name()) || nativeAdSize.equals(NativeAdSize.MIDDLE.name())) {
            adView.setMediaView((com.google.android.gms.ads.nativead.MediaView) adView.findViewById(R.id.ad_media));
        }
        if (nativeAdSize.equals(NativeAdSize.LARGE.name()) || nativeAdSize.equals(NativeAdSize.MINI.name()) || nativeAdSize.equals(NativeAdSize.EXTRA.name())) {
            adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        }
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
        if (nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }
        if (nativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            adView.getCallToActionView().setVisibility(View.VISIBLE);
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }

        if (nativeAdSize.equals(NativeAdSize.LARGE.name())) {
            adView.getPriceView().setVisibility(View.GONE);
            adView.getStoreView().setVisibility(View.GONE);
            adView.getStarRatingView().setVisibility(View.GONE);
            if (nativeAd.getAdvertiser() == null || (nativeAd.getBody() != null && !nativeAd.getBody().isEmpty())) {
                adView.getAdvertiserView().setVisibility(View.GONE);
            } else {
                ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
                adView.getAdvertiserView().setVisibility(View.VISIBLE);
            }
        }
        if (nativeAdSize.equals(NativeAdSize.LARGE.name()) || nativeAdSize.equals(NativeAdSize.MIDDLE.name())) {
            adView.getMediaView().setMediaContent(nativeAd.getMediaContent());
        }
        if (nativeAdSize.equals(NativeAdSize.LARGE.name()) || nativeAdSize.equals(NativeAdSize.MINI.name()) || nativeAdSize.equals(NativeAdSize.EXTRA.name())) {
            if (nativeAd.getIcon() == null) {
                adView.getIconView().setVisibility(View.GONE);
            } else {
                ((ImageView) adView.getIconView()).setImageDrawable(nativeAd.getIcon().getDrawable());
                adView.getIconView().setVisibility(View.VISIBLE);
            }
        }
        adView.setNativeAd(nativeAd);
    }

    public static void inflateNativeAdView(com.facebook.ads.NativeAd nativeAd, Activity activity, LinearLayout linearLayout, String nativeAdSize) {
        // Unregister last ad
        nativeAd.unregisterView();
        linearLayout.setVisibility(View.VISIBLE);
        linearLayout.removeAllViews();
        // Add the Ad view into the ad container.
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        NativeAdLayout nativeAdLayout = new NativeAdLayout(activity);
        // Inflate the Ad view.  The layout referenced is the one you created in the last step.
        LinearLayout adView;
        if (nativeAdSize.equals(NativeAdSize.EXTRA.name())) {
            adView = (LinearLayout) layoutInflater.inflate(R.layout.custom_facebook_native_extra, nativeAdLayout, false);
        } else if (nativeAdSize.equals(NativeAdSize.MINI.name())) {
            adView = (LinearLayout) layoutInflater.inflate(R.layout.custom_facebook_native_mini, nativeAdLayout, false);
        } else if (nativeAdSize.equals(NativeAdSize.MIDDLE.name())) {
            adView = (LinearLayout) layoutInflater.inflate(R.layout.custom_facebook_native_middle, nativeAdLayout, false);
        } else {
            adView = (LinearLayout) layoutInflater.inflate(R.layout.custom_facebook_native_large, nativeAdLayout, false);
        }
        nativeAdLayout.addView(adView);
        linearLayout.addView(nativeAdLayout);

        // Add the AdOptionsView
        LinearLayout adChoicesContainer = adView.findViewById(R.id.ad_choices_container);
        AdOptionsView adOptionsView = new AdOptionsView(activity, nativeAd, nativeAdLayout);
        adChoicesContainer.removeAllViews();
        adChoicesContainer.addView(adOptionsView, 0);

        // Create native UI using the ad metadata.
        com.facebook.ads.MediaView nativeAdIcon = adView.findViewById(R.id.native_ad_icon);
        TextView nativeAdTitle = adView.findViewById(R.id.native_ad_title);
        com.facebook.ads.MediaView nativeAdMedia = adView.findViewById(R.id.native_ad_media);
        TextView nativeAdSocialContext = adView.findViewById(R.id.native_ad_social_context);
        TextView nativeAdBody = adView.findViewById(R.id.native_ad_body);
        TextView sponsoredLabel = adView.findViewById(R.id.native_ad_sponsored_label);
        Button nativeAdCallToAction = adView.findViewById(R.id.native_ad_call_to_action);

        if (AdsMasterClass.getAdsDataModel() != null && AdsMasterClass.getAdsDataModel().getNative_bg_color().trim().length() > 0 && !AdsMasterClass.getAdsDataModel().getNative_bg_color().equals("0")) {
            adView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(AdsMasterClass.getAdsDataModel().getNative_bg_color())));
        } else {
            adView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(AdsPreference.getString(activity, AdsConstant.NATIVE_BG_COLOR, AdsConstant.getHexStringColor(activity.getResources().getColor(R.color.ad_bg_color))))));
        }
        if (AdsMasterClass.getAdsDataModel() != null && AdsMasterClass.getAdsDataModel().getNative_button_color().trim().length() > 0 && !AdsMasterClass.getAdsDataModel().getNative_button_color().equals("0")) {
            nativeAdCallToAction.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(AdsMasterClass.getAdsDataModel().getNative_button_color())));
        } else {
            nativeAdCallToAction.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(AdsPreference.getString(activity, AdsConstant.NATIVE_BUTTON_COLOR, AdsConstant.getHexStringColor(activity.getResources().getColor(R.color.ad_button_color))))));
        }

        nativeAdTitle.setTextColor(ColorStateList.valueOf(Color.parseColor(AdsPreference.getString(activity, AdsConstant.NATIVE_TEXT_COLOR, AdsConstant.getHexStringColor(activity.getResources().getColor(R.color.ad_text_color))))));
        nativeAdBody.setTextColor(ColorStateList.valueOf(Color.parseColor(AdsPreference.getString(activity, AdsConstant.NATIVE_TEXT_COLOR, AdsConstant.getHexStringColor(activity.getResources().getColor(R.color.ad_text_color))))));
        nativeAdCallToAction.setTextColor(ColorStateList.valueOf(Color.parseColor(AdsPreference.getString(activity, AdsConstant.NATIVE_BUTTON_TEXT_COLOR, AdsConstant.getHexStringColor(activity.getResources().getColor(R.color.ad_button_text_color))))));

        // Set the Text.
        nativeAdTitle.setText(nativeAd.getAdvertiserName());
        nativeAdBody.setText(nativeAd.getAdBodyText());
        nativeAdSocialContext.setText(nativeAd.getAdSocialContext());
        nativeAdCallToAction.setVisibility(nativeAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
        nativeAdCallToAction.setText(nativeAd.getAdCallToAction());
        sponsoredLabel.setText(nativeAd.getSponsoredTranslation());

        // Create a list of clickable views
        List<View> clickableViews = new ArrayList<>();
        clickableViews.add(nativeAdTitle);
        clickableViews.add(nativeAdCallToAction);

        // Register the Title and CTA button to listen for clicks.
        nativeAd.registerViewForInteraction(
                adView, nativeAdMedia, nativeAdIcon, clickableViews);
    }

    public static MaxNativeAdView inflateMaxNativeAdView(Activity activity, String nativeAdSize) {
        int layout = 0;
        if (nativeAdSize.equals(NativeAdSize.MINI.name())) {
            layout = R.layout.custom_applovin_native_mini;
        } else {
            layout = R.layout.custom_applovin_native_middle;
        }
        MaxNativeAdViewBinder binder = new MaxNativeAdViewBinder.Builder(layout)
                .setTitleTextViewId(R.id.title_text_view)
                .setBodyTextViewId(R.id.body_text_view)
                .setAdvertiserTextViewId(R.id.advertiser_textView)
                .setIconImageViewId(R.id.icon_image_view)
                .setMediaContentViewGroupId(R.id.media_view_container)
                .setCallToActionButtonId(R.id.cta_button)
                .setOptionsContentViewGroupId(R.id.ad_options_view)
                .build();
        return new MaxNativeAdView(binder, activity);
    }
}
