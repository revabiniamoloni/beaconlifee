package com.developer.mylibrary.utils;

import com.developer.mylibrary.model.AdsDataModel;

public class AdsDefaultValue {

    public static void setDefaultValue() {
        AdsDataModel adsDataModel = AdsMasterClass.getAdsDataModel();

        adsDataModel.setApp_open_splash("0");
        adsDataModel.setApp_open_main(1);
        adsDataModel.setApp_open_sequence("g");
        adsDataModel.setInterstitial_show_sequence("g");
        adsDataModel.setInterstitial_show_value(4);
        adsDataModel.setInterstitial_show_ads_loading(1);
        adsDataModel.setInterstitial_show_fail_sequence("0");
        adsDataModel.setBanner_show_value(0);
        adsDataModel.setNative_show_sequence("g");
        adsDataModel.setNative_show_value(4);
        adsDataModel.setNative_show_fail_sequence("0");
        adsDataModel.setNative_preload(2);
        adsDataModel.setNative_list_show_value(0);
        adsDataModel.setRewarded_show_value(0);
        adsDataModel.setApp_update_dialog(0);
        adsDataModel.setShow_tips_screen(0);
        adsDataModel.setShow_tips_interstitial_value("0");
        adsDataModel.setShow_rate_dialog_value(0);
        adsDataModel.setShow_log(0);
        adsDataModel.setShow_ads_on_back("0");
        adsDataModel.setShow_native_second(0);
        adsDataModel.setQureka_url("");
        adsDataModel.setQureka_ads_image("0");
        adsDataModel.setIs_direct_qureka_open(0);
        adsDataModel.setRandom_max_number(0);
        adsDataModel.setShow_exit_dialog_native(0);
        adsDataModel.setUpgrade_package_name("");

        adsDataModel.setGoogle_appopen_id("ca-app-pub-3940256099942544/3419835294");
        adsDataModel.setGoogle_interstitial_id("/6499/example/interstitial");
        adsDataModel.setGoogle_native_id("/6499/example/native");
    }
}