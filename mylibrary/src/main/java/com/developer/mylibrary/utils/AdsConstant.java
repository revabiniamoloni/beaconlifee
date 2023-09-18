package com.developer.mylibrary.utils;

import com.developer.mylibrary.R;

public class AdsConstant {
    public static String FullScreenAdShowValuePos = "FullScreenAdShowValuePos";
    public static String FullScreenAdShowValuePosTips = "FullScreenAdShowValuePosTips";
    public static String FullScreenAdShowSeqPos = "FullScreenAdShowSeqPos";
    public static String Preload_FullScreenAdSeqPos = "Preload_FullScreenAdSeqPos";
    public static String ShowPreload_FullScreenAdSeqPos = "ShowPreload_FullScreenAdSeqPos";
    public static String BannerAdShowValuePos = "BannerAdShowValuePos";
    public static String BannerAdShowSeqPos = "BannerAdShowSeqPos";
    public static String NativeAdShowValuePos = "NativeAdShowValuePos";
    public static String NativeAdShowSeqPos = "NativeAdShowSeqPos";
    public static String RewardedAdShowValuePos = "RewardedAdShowValuePos";
    public static String RewardedAdShowSeqPos = "RewardedAdShowSeqPos";
    public static String FullScreenAdShowOnBackValuePos = "FullScreenAdShowOnBackValuePos";
    public static String RateDialogShowValuePos = "RateDialogShowValuePos";
    public static String QurekaAdShowSeqPos = "QurekaAdShowSeqPos";

    public static String GoogleFullScreenIdSeqPos = "GoogleFullScreenIdSeqPos";
    public static String GoogleBannerIdSeqPos = "GoogleBannerIdSeqPos";
    public static String GoogleNativeIdSeqPos = "GoogleNativeIdSeqPos";
    public static String GoogleRewardedIdSeqPos = "GoogleRewardedIdSeqPos";
    public static String GoogleAppOpenIdSeqPos = "GoogleAppOpenIdSeqPos";
    public static String FacebookFullScreenIdSeqPos = "FacebookFullScreenIdSeqPos";
    public static String FacebookBannerIdSeqPos = "FacebookBannerIdSeqPos";
    public static String FacebookNativeBannerIdSeqPos = "FacebookNativeBannerIdSeqPos";
    public static String FacebookNativeIdSeqPos = "FacebookNativeIdSeqPos";
    public static String ApplovinFullScreenIdSeqPos = "ApplovinFullScreenIdSeqPos";
    public static String ApplovinNativeIdSeqPos = "ApplovinNativeIdSeqPos";
    public static String ApplovinAppOpenIdSeqPos = "ApplovinAppOpenIdSeqPos";

    public static String FullScreenAdRandomValuePos = "FullScreenAdRandomValuePos";
    public static String FullScreenAdShowOnBackSeqPos = "FullScreenAdShowOnBackSeqPos";
    public static String FullScreenAdShowOnTipsSeqPos = "FullScreenAdShowOnTipsSeqPos";

    public static Integer[] QUREKA_FULL_SCREEN_LIST = new Integer[]{R.layout.qureka_full_ad_0, R.layout.qureka_full_ad_1, R.layout.qureka_full_ad_2, R.layout.qureka_full_ad_3, R.layout.qureka_full_ad_4, R.layout.qureka_full_ad_5, R.layout.qureka_full_ad_6, R.layout.qureka_full_ad_7, R.layout.qureka_full_ad_8, R.layout.qureka_full_ad_9, R.layout.qureka_full_ad_10};
    public static Integer[] QUREKA_NATIVE_LARGE_LIST = new Integer[]{R.drawable.ic_q_native_0, R.drawable.ic_q_native_1, R.drawable.ic_q_native_2, R.drawable.ic_q_native_3, R.drawable.ic_q_native_4, R.drawable.ic_q_native_5, R.drawable.ic_q_native_6, R.drawable.ic_q_native_7};
    public static Integer[] QUREKA_NATIVE_MIDDLE_LIST = new Integer[]{R.drawable.ic_q_native_middle_0, R.drawable.ic_q_native_middle_1, R.drawable.ic_q_native_middle_2, R.drawable.ic_q_native_middle_3, R.drawable.ic_q_native_middle_4};
    public static Integer[] QUREKA_NATIVE_MINI_LIST = new Integer[]{R.drawable.ic_q_banner_ad, R.drawable.ic_q_banner_ad};

    // put extra & preference key
    public static String BASE_URL = "BASE_URL";
    public static String TOKEN = "TOKEN";
    public static String RESPONSE = "RESPONSE";
    public static String RATE_US = "RATE_US";
    public static String RATE_NOT_NOW = "RATE_NOT_NOW";

    public static String NATIVE_BG_COLOR = "NATIVE_BG_COLOR";
    public static String NATIVE_TEXT_COLOR = "NATIVE_TEXT_COLOR";
    public static String NATIVE_BUTTON_COLOR = "NATIVE_BUTTON_COLOR";
    public static String NATIVE_BUTTON_TEXT_COLOR = "NATIVE_BUTTON_TEXT_COLOR";

    public static String START_WITH_FINISH = "START_WITH_FINISH";
    public static String ONLY_FINISH = "ONLY_FINISH";
    public static String IS_SHOW_TIPS = "IS_SHOW_TIPS";

    public static String getHexStringColor(int color) {
        return String.format("#%06X", (0xFFFFFF & color));
    }
}
