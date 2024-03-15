package com.developer.mylibrary.utils;

public class AdsConstant {
    public static String FullScreenAdShowValuePos = "FullScreenAdShowValuePos";
    public static String FullScreenAdShowValuePosTips = "FullScreenAdShowValuePosTips";
    public static String FullScreenAdShowSeqPos = "FullScreenAdShowSeqPos";
    public static String Preload_FullScreenAdSeqPos = "Preload_FullScreenAdSeqPos";
    public static String ShowPreload_FullScreenAdSeqPos = "ShowPreload_FullScreenAdSeqPos";
    public static String BannerAdShowValuePos = "BannerAdShowValuePos";
    public static String BannerAdShowSeqPos = "BannerAdShowSeqPos";
    public static String ExtraBannerAdShowSeqPos = "ExtraBannerAdShowSeqPos";
    public static String NativeAdShowValuePos = "NativeAdShowValuePos";
    public static String NativeAdShowSeqPos = "NativeAdShowSeqPos";
    public static String ExtraNativeAdShowSeqPos = "ExtraNativeAdShowSeqPos";
    public static String RewardedAdShowValuePos = "RewardedAdShowValuePos";
    public static String RewardedAdShowSeqPos = "RewardedAdShowSeqPos";
    public static String FullScreenAdShowOnBackValuePos = "FullScreenAdShowOnBackValuePos";

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

    // put extra & preference key
    public static String BASE_URL = "BASE_URL";
    public static String TOKEN = "TOKEN";
    public static String RESPONSE = "RESPONSE";
    public static String RATE_NOT_NOW = "RATE_NOT_NOW";

    public static String NATIVE_BG_COLOR = "NATIVE_BG_COLOR";
    public static String NATIVE_TEXT_COLOR = "NATIVE_TEXT_COLOR";
    public static String NATIVE_BUTTON_COLOR = "NATIVE_BUTTON_COLOR";
    public static String NATIVE_BUTTON_TEXT_COLOR = "NATIVE_BUTTON_TEXT_COLOR";
    public static String ADS_LOADING_DIALOG = "ADS_LOADING_DIALOG";
    public static String OLD = "OLD";
    public static String NEW = "NEW";

    public static String START_WITH_FINISH = "START_WITH_FINISH";
    public static String ONLY_FINISH = "ONLY_FINISH";
    public static String IS_SHOW_TIPS = "IS_SHOW_TIPS";

    public static String getHexStringColor(int color) {
        return String.format("#%06X", (0xFFFFFF & color));
    }
}
