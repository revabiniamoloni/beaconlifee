package com.developer.mylibrary.utils;

import com.developer.mylibrary.model.AdsDataModel;

public class AdsDefaultValueOnRemove {

    public static void setDefaultValueOnRemove() {
        AdsDataModel adsDataModel = AdsMasterClass.getAdsDataModel();

        adsDataModel.setApp_open_splash("0");
        adsDataModel.setApp_open_main(1);
        adsDataModel.setApp_open_sequence("q");
        adsDataModel.setInterstitial_show_sequence("a-q-a");
        adsDataModel.setInterstitial_show_value(3);
        adsDataModel.setInterstitial_show_ads_loading(0);
        adsDataModel.setInterstitial_show_fail_sequence("a-q");
        adsDataModel.setBanner_show_sequence("q");
        adsDataModel.setBanner_show_value(1);
        adsDataModel.setNative_show_sequence("q");
        adsDataModel.setNative_show_value(2);
        adsDataModel.setNative_show_fail_sequence("0");
        adsDataModel.setNative_preload(1);
        adsDataModel.setNative_list_show_value(5);
        adsDataModel.setShow_ads_on_back("1-2-3");
        adsDataModel.setShow_native_second(1);
        adsDataModel.setShow_exit_dialog_native(1);
    }
}
