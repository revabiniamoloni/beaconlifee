package com.developer.mylibrary.model;

import java.util.ArrayList;

public class AdsDataModel {

    // ads data
    private String app_open_splash = "";
    private int app_open_main = 0;
    private String app_open_sequence = "";
    private String app_open_fail_sequence = "";

    private String interstitial_show_sequence = "";
    private int interstitial_show_value = 0;
    private String interstitial_show_fail_sequence = "";
    private int interstitial_show_ads_loading = 0;

    private String banner_show_sequence = "";
    private int banner_show_value = 0;
    private String banner_show_fail_sequence = "";

    private String native_show_sequence = "";
    private int native_show_value = 0;
    private String native_show_fail_sequence = "";
    private int native_preload = 0;
    private int native_list_show_value = 0;

    private String rewarded_show_sequence = "";
    private int rewarded_show_value = 0;
    private String rewarded_show_fail_sequence = "";

    private int app_version_code = 0;
    private int app_inreview = 0;
    private int app_update_dialog = 0;

    private int show_tips_screen = 0;
    private String show_tips_interstitial_value = "";
    private int show_rate_dialog_value = 0;

    private String google_interstitial_id = "";
    private String google_banner_id = "";
    private String google_native_id = "";
    private String google_appopen_id = "";
    private String google_rewarded_id = "";

    private String adx_interstitial_id = "";
    private String adx_banner_id = "";
    private String adx_native_id = "";
    private String adx_appopen_id = "";
    private String adx_rewarded_id = "";

    private String adx2_interstitial_id = "";
    private String adx2_banner_id = "";
    private String adx2_native_id = "";
    private String adx2_appopen_id = "";
    private String adx2_rewarded_id = "";

    private String appbaroda_interstitial_id = "";
    private String appbaroda_banner_id = "";
    private String appbaroda_native_id = "";
    private String appbaroda_appopen_id = "";
    private String appbaroda_rewarded_id = "";

    private String facebook_interstitial_id = "";
    private String facebook_banner_id = "";
    private String facebook_native_id = "";
    private String facebook_native_banner_id = "";

    private String applovin_interstitial_id = "";
    private String applovin_native_id = "";
    private String applovin_app_open_id = "";

    private String qureka_url = "";

    private String show_ads_on_back = "";
    private int show_native_second = 0;
    private String native_bg_color = "";
    private String native_button_color = "";
    private String show_notification = "";
    private int show_log = 0;

    // other ads data
    private String qureka_ads_image = "0";
    private int is_direct_qureka_open = 0;
    private int random_max_number = 0;
    private int show_exit_dialog_native = 0;
    private int check_app_status = 0;
    private String upgrade_package_name = "";
    private int show_extra_features = 0;

    // extra flag in other ads data
    private String extra_flag_a = "";
    private String extra_flag_b = "";
    private String extra_flag_c = "";
    private String extra_flag_d = "";
    private String extra_flag_e = "";
    private String extra_flag_f = "";
    private String extra_flag_g = "";
    private String extra_flag_h = "";
    private String extra_flag_i = "";
    private String extra_flag_j = "";

    // ads variable
    private boolean is_appopen_fail_check;
    private boolean is_splash_appopen_fail_check;
    private boolean is_appopen_loading;
    private ArrayList<Integer> maxNumberList = new ArrayList<>();
    private int next_on_back_value = 0;
    private int next_tips_fullscreen_value = 0;
    private int exit_app_native = 0;

    public String getApp_open_splash() {
        return app_open_splash;
    }

    public void setApp_open_splash(String app_open_splash) {
        this.app_open_splash = app_open_splash;
    }

    public int getApp_open_main() {
        return app_open_main;
    }

    public void setApp_open_main(int app_open_main) {
        this.app_open_main = app_open_main;
    }

    public String getApp_open_sequence() {
        return app_open_sequence;
    }

    public void setApp_open_sequence(String app_open_sequence) {
        this.app_open_sequence = app_open_sequence;
    }

    public String getApp_open_fail_sequence() {
        return app_open_fail_sequence;
    }

    public void setApp_open_fail_sequence(String app_open_fail_sequence) {
        this.app_open_fail_sequence = app_open_fail_sequence;
    }

    public String getInterstitial_show_sequence() {
        return interstitial_show_sequence;
    }

    public void setInterstitial_show_sequence(String interstitial_show_sequence) {
        this.interstitial_show_sequence = interstitial_show_sequence;
    }

    public int getInterstitial_show_value() {
        return interstitial_show_value;
    }

    public void setInterstitial_show_value(int interstitial_show_value) {
        this.interstitial_show_value = interstitial_show_value;
    }

    public String getInterstitial_show_fail_sequence() {
        return interstitial_show_fail_sequence;
    }

    public void setInterstitial_show_fail_sequence(String interstitial_show_fail_sequence) {
        this.interstitial_show_fail_sequence = interstitial_show_fail_sequence;
    }

    public int getInterstitial_show_ads_loading() {
        return interstitial_show_ads_loading;
    }

    public void setInterstitial_show_ads_loading(int interstitial_show_ads_loading) {
        this.interstitial_show_ads_loading = interstitial_show_ads_loading;
    }

    public String getBanner_show_sequence() {
        return banner_show_sequence;
    }

    public void setBanner_show_sequence(String banner_show_sequence) {
        this.banner_show_sequence = banner_show_sequence;
    }

    public int getBanner_show_value() {
        return banner_show_value;
    }

    public void setBanner_show_value(int banner_show_value) {
        this.banner_show_value = banner_show_value;
    }

    public String getBanner_show_fail_sequence() {
        return banner_show_fail_sequence;
    }

    public void setBanner_show_fail_sequence(String banner_show_fail_sequence) {
        this.banner_show_fail_sequence = banner_show_fail_sequence;
    }

    public String getNative_show_sequence() {
        return native_show_sequence;
    }

    public void setNative_show_sequence(String native_show_sequence) {
        this.native_show_sequence = native_show_sequence;
    }

    public int getNative_show_value() {
        return native_show_value;
    }

    public void setNative_show_value(int native_show_value) {
        this.native_show_value = native_show_value;
    }

    public String getNative_show_fail_sequence() {
        return native_show_fail_sequence;
    }

    public void setNative_show_fail_sequence(String native_show_fail_sequence) {
        this.native_show_fail_sequence = native_show_fail_sequence;
    }

    public int getNative_preload() {
        return native_preload;
    }

    public void setNative_preload(int native_preload) {
        this.native_preload = native_preload;
    }

    public int getNative_list_show_value() {
        return native_list_show_value;
    }

    public void setNative_list_show_value(int native_list_show_value) {
        this.native_list_show_value = native_list_show_value;
    }

    public String getRewarded_show_sequence() {
        return rewarded_show_sequence;
    }

    public void setRewarded_show_sequence(String rewarded_show_sequence) {
        this.rewarded_show_sequence = rewarded_show_sequence;
    }

    public int getRewarded_show_value() {
        return rewarded_show_value;
    }

    public void setRewarded_show_value(int rewarded_show_value) {
        this.rewarded_show_value = rewarded_show_value;
    }

    public String getRewarded_show_fail_sequence() {
        return rewarded_show_fail_sequence;
    }

    public void setRewarded_show_fail_sequence(String rewarded_show_fail_sequence) {
        this.rewarded_show_fail_sequence = rewarded_show_fail_sequence;
    }

    public int getApp_version_code() {
        return app_version_code;
    }

    public void setApp_version_code(int app_version_code) {
        this.app_version_code = app_version_code;
    }

    public int getApp_inreview() {
        return app_inreview;
    }

    public void setApp_inreview(int app_inreview) {
        this.app_inreview = app_inreview;
    }

    public int getApp_update_dialog() {
        return app_update_dialog;
    }

    public void setApp_update_dialog(int app_update_dialog) {
        this.app_update_dialog = app_update_dialog;
    }

    public int getShow_tips_screen() {
        return show_tips_screen;
    }

    public void setShow_tips_screen(int show_tips_screen) {
        this.show_tips_screen = show_tips_screen;
    }

    public String getShow_tips_interstitial_value() {
        return show_tips_interstitial_value;
    }

    public void setShow_tips_interstitial_value(String show_tips_interstitial_value) {
        this.show_tips_interstitial_value = show_tips_interstitial_value;
    }

    public int getShow_rate_dialog_value() {
        return show_rate_dialog_value;
    }

    public void setShow_rate_dialog_value(int show_rate_dialog_value) {
        this.show_rate_dialog_value = show_rate_dialog_value;
    }

    public String getGoogle_interstitial_id() {
        return google_interstitial_id;
    }

    public void setGoogle_interstitial_id(String google_interstitial_id) {
        this.google_interstitial_id = google_interstitial_id;
    }

    public String getGoogle_banner_id() {
        return google_banner_id;
    }

    public void setGoogle_banner_id(String google_banner_id) {
        this.google_banner_id = google_banner_id;
    }

    public String getGoogle_native_id() {
        return google_native_id;
    }

    public void setGoogle_native_id(String google_native_id) {
        this.google_native_id = google_native_id;
    }

    public String getGoogle_appopen_id() {
        return google_appopen_id;
    }

    public void setGoogle_appopen_id(String google_appopen_id) {
        this.google_appopen_id = google_appopen_id;
    }

    public String getGoogle_rewarded_id() {
        return google_rewarded_id;
    }

    public void setGoogle_rewarded_id(String google_rewarded_id) {
        this.google_rewarded_id = google_rewarded_id;
    }

    public String getAdx_interstitial_id() {
        return adx_interstitial_id;
    }

    public void setAdx_interstitial_id(String adx_interstitial_id) {
        this.adx_interstitial_id = adx_interstitial_id;
    }

    public String getAdx_banner_id() {
        return adx_banner_id;
    }

    public void setAdx_banner_id(String adx_banner_id) {
        this.adx_banner_id = adx_banner_id;
    }

    public String getAdx_native_id() {
        return adx_native_id;
    }

    public void setAdx_native_id(String adx_native_id) {
        this.adx_native_id = adx_native_id;
    }

    public String getAdx_appopen_id() {
        return adx_appopen_id;
    }

    public void setAdx_appopen_id(String adx_appopen_id) {
        this.adx_appopen_id = adx_appopen_id;
    }

    public String getAdx_rewarded_id() {
        return adx_rewarded_id;
    }

    public void setAdx_rewarded_id(String adx_rewarded_id) {
        this.adx_rewarded_id = adx_rewarded_id;
    }

    public String getAdx2_interstitial_id() {
        return adx2_interstitial_id;
    }

    public void setAdx2_interstitial_id(String adx2_interstitial_id) {
        this.adx2_interstitial_id = adx2_interstitial_id;
    }

    public String getAdx2_banner_id() {
        return adx2_banner_id;
    }

    public void setAdx2_banner_id(String adx2_banner_id) {
        this.adx2_banner_id = adx2_banner_id;
    }

    public String getAdx2_native_id() {
        return adx2_native_id;
    }

    public void setAdx2_native_id(String adx2_native_id) {
        this.adx2_native_id = adx2_native_id;
    }

    public String getAdx2_appopen_id() {
        return adx2_appopen_id;
    }

    public void setAdx2_appopen_id(String adx2_appopen_id) {
        this.adx2_appopen_id = adx2_appopen_id;
    }

    public String getAdx2_rewarded_id() {
        return adx2_rewarded_id;
    }

    public void setAdx2_rewarded_id(String adx2_rewarded_id) {
        this.adx2_rewarded_id = adx2_rewarded_id;
    }

    public String getAppbaroda_interstitial_id() {
        return appbaroda_interstitial_id;
    }

    public void setAppbaroda_interstitial_id(String appbaroda_interstitial_id) {
        this.appbaroda_interstitial_id = appbaroda_interstitial_id;
    }

    public String getAppbaroda_banner_id() {
        return appbaroda_banner_id;
    }

    public void setAppbaroda_banner_id(String appbaroda_banner_id) {
        this.appbaroda_banner_id = appbaroda_banner_id;
    }

    public String getAppbaroda_native_id() {
        return appbaroda_native_id;
    }

    public void setAppbaroda_native_id(String appbaroda_native_id) {
        this.appbaroda_native_id = appbaroda_native_id;
    }

    public String getAppbaroda_appopen_id() {
        return appbaroda_appopen_id;
    }

    public void setAppbaroda_appopen_id(String appbaroda_appopen_id) {
        this.appbaroda_appopen_id = appbaroda_appopen_id;
    }

    public String getAppbaroda_rewarded_id() {
        return appbaroda_rewarded_id;
    }

    public void setAppbaroda_rewarded_id(String appbaroda_rewarded_id) {
        this.appbaroda_rewarded_id = appbaroda_rewarded_id;
    }

    public String getFacebook_interstitial_id() {
        return facebook_interstitial_id;
    }

    public void setFacebook_interstitial_id(String facebook_interstitial_id) {
        this.facebook_interstitial_id = facebook_interstitial_id;
    }

    public String getFacebook_banner_id() {
        return facebook_banner_id;
    }

    public void setFacebook_banner_id(String facebook_banner_id) {
        this.facebook_banner_id = facebook_banner_id;
    }

    public String getFacebook_native_id() {
        return facebook_native_id;
    }

    public void setFacebook_native_id(String facebook_native_id) {
        this.facebook_native_id = facebook_native_id;
    }

    public String getFacebook_native_banner_id() {
        return facebook_native_banner_id;
    }

    public void setFacebook_native_banner_id(String facebook_native_banner_id) {
        this.facebook_native_banner_id = facebook_native_banner_id;
    }

    public String getApplovin_interstitial_id() {
        return applovin_interstitial_id;
    }

    public void setApplovin_interstitial_id(String applovin_interstitial_id) {
        this.applovin_interstitial_id = applovin_interstitial_id;
    }

    public String getApplovin_native_id() {
        return applovin_native_id;
    }

    public void setApplovin_native_id(String applovin_native_id) {
        this.applovin_native_id = applovin_native_id;
    }

    public String getApplovin_app_open_id() {
        return applovin_app_open_id;
    }

    public void setApplovin_app_open_id(String applovin_app_open_id) {
        this.applovin_app_open_id = applovin_app_open_id;
    }

    public String getQureka_url() {
        return qureka_url;
    }

    public void setQureka_url(String qureka_url) {
        this.qureka_url = qureka_url;
    }

    public String getShow_ads_on_back() {
        return show_ads_on_back;
    }

    public void setShow_ads_on_back(String show_ads_on_back) {
        this.show_ads_on_back = show_ads_on_back;
    }

    public int getShow_native_second() {
        return show_native_second;
    }

    public void setShow_native_second(int show_native_second) {
        this.show_native_second = show_native_second;
    }

    public String getNative_bg_color() {
        return native_bg_color;
    }

    public void setNative_bg_color(String native_bg_color) {
        this.native_bg_color = native_bg_color;
    }

    public String getNative_button_color() {
        return native_button_color;
    }

    public void setNative_button_color(String native_button_color) {
        this.native_button_color = native_button_color;
    }

    public String getShow_notification() {
        return show_notification;
    }

    public void setShow_notification(String show_notification) {
        this.show_notification = show_notification;
    }

    public int getShow_log() {
        return show_log;
    }

    public void setShow_log(int show_log) {
        this.show_log = show_log;
    }

    public int getIs_direct_qureka_open() {
        return is_direct_qureka_open;
    }

    public void setIs_direct_qureka_open(int is_direct_qureka_open) {
        this.is_direct_qureka_open = is_direct_qureka_open;
    }

    public String getQureka_ads_image() {
        return qureka_ads_image;
    }

    public void setQureka_ads_image(String qureka_ads_image) {
        this.qureka_ads_image = qureka_ads_image;
    }

    public boolean isIs_appopen_fail_check() {
        return is_appopen_fail_check;
    }

    public void setIs_appopen_fail_check(boolean is_appopen_fail_check) {
        this.is_appopen_fail_check = is_appopen_fail_check;
    }

    public boolean isIs_splash_appopen_fail_check() {
        return is_splash_appopen_fail_check;
    }

    public void setIs_splash_appopen_fail_check(boolean is_splash_appopen_fail_check) {
        this.is_splash_appopen_fail_check = is_splash_appopen_fail_check;
    }

    public boolean isIs_appopen_loading() {
        return is_appopen_loading;
    }

    public void setIs_appopen_loading(boolean is_appopen_loading) {
        this.is_appopen_loading = is_appopen_loading;
    }

    public int getRandom_max_number() {
        return random_max_number;
    }

    public void setRandom_max_number(int random_max_number) {
        this.random_max_number = random_max_number;
    }

    public int getShow_exit_dialog_native() {
        return show_exit_dialog_native;
    }

    public void setShow_exit_dialog_native(int show_exit_dialog_native) {
        this.show_exit_dialog_native = show_exit_dialog_native;
    }

    public ArrayList<Integer> getMaxNumberList() {
        return maxNumberList;
    }

    public void setMaxNumberList(ArrayList<Integer> maxNumberList) {
        this.maxNumberList = maxNumberList;
    }

    public int getNext_on_back_value() {
        return next_on_back_value;
    }

    public void setNext_on_back_value(int next_on_back_value) {
        this.next_on_back_value = next_on_back_value;
    }

    public int getNext_tips_fullscreen_value() {
        return next_tips_fullscreen_value;
    }

    public void setNext_tips_fullscreen_value(int next_tips_fullscreen_value) {
        this.next_tips_fullscreen_value = next_tips_fullscreen_value;
    }

    public int getExit_app_native() {
        return exit_app_native;
    }

    public void setExit_app_native(int exit_app_native) {
        this.exit_app_native = exit_app_native;
    }

    public int getCheck_app_status() {
        return check_app_status;
    }

    public void setCheck_app_status(int check_app_status) {
        this.check_app_status = check_app_status;
    }

    public String getUpgrade_package_name() {
        return upgrade_package_name;
    }

    public void setUpgrade_package_name(String upgrade_package_name) {
        this.upgrade_package_name = upgrade_package_name;
    }

    public int getShow_extra_features() {
        return show_extra_features;
    }

    public void setShow_extra_features(int show_extra_features) {
        this.show_extra_features = show_extra_features;
    }

    public String getExtra_flag_a() {
        return getExtraFlagValue(extra_flag_a);
    }

    public void setExtra_flag_a(String extra_flag_a) {
        this.extra_flag_a = extra_flag_a;
    }

    public String getExtra_flag_b() {
        return getExtraFlagValue(extra_flag_b);
    }

    public void setExtra_flag_b(String extra_flag_b) {
        this.extra_flag_b = extra_flag_b;
    }

    public String getExtra_flag_c() {
        return getExtraFlagValue(extra_flag_c);
    }

    public void setExtra_flag_c(String extra_flag_c) {
        this.extra_flag_c = extra_flag_c;
    }

    public String getExtra_flag_d() {
        return getExtraFlagValue(extra_flag_d);
    }

    public void setExtra_flag_d(String extra_flag_d) {
        this.extra_flag_d = extra_flag_d;
    }

    public String getExtra_flag_e() {
        return getExtraFlagValue(extra_flag_e);
    }

    public void setExtra_flag_e(String extra_flag_e) {
        this.extra_flag_e = extra_flag_e;
    }

    public String getExtra_flag_f() {
        return getExtraFlagValue(extra_flag_f);
    }

    public void setExtra_flag_f(String extra_flag_f) {
        this.extra_flag_f = extra_flag_f;
    }

    public String getExtra_flag_g() {
        return getExtraFlagValue(extra_flag_g);
    }

    public void setExtra_flag_g(String extra_flag_g) {
        this.extra_flag_g = extra_flag_g;
    }

    public String getExtra_flag_h() {
        return getExtraFlagValue(extra_flag_h);
    }

    public void setExtra_flag_h(String extra_flag_h) {
        this.extra_flag_h = extra_flag_h;
    }

    public String getExtra_flag_i() {
        return getExtraFlagValue(extra_flag_i);
    }

    public void setExtra_flag_i(String extra_flag_i) {
        this.extra_flag_i = extra_flag_i;
    }

    public String getExtra_flag_j() {
        return getExtraFlagValue(extra_flag_j);
    }

    public void setExtra_flag_j(String extra_flag_j) {
        this.extra_flag_j = extra_flag_j;
    }

    public static String getExtraFlagValue(String value) {
        if (value != null && value.trim().length() > 0) {
            String[] valuesArray = value.split("@");
            return valuesArray[1].trim();
        }
        return "";
    }
}