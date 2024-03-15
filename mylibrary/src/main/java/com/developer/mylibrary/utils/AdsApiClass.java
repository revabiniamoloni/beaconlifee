package com.developer.mylibrary.utils;

import android.app.Activity;
import android.os.AsyncTask;

import com.developer.mylibrary.eum_class.AdsLogTag;
import com.developer.mylibrary.fullscreen_ad.preload.FullScreenAdPreload;
import com.developer.mylibrary.model.AdsDataModel;
import com.developer.mylibrary.native_ad.preload.NativeAdPreLoad;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.ResponseHandler;
import cz.msebera.android.httpclient.client.config.RequestConfig;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.BasicResponseHandler;
import cz.msebera.android.httpclient.impl.client.HttpClients;

public class AdsApiClass {

    public static Activity activity;
    public static AdsApiClass adsApiClass;
    public static Activity appOpenActivity;
    public static String packageName;

    public static AdsApiClass getInstance(Activity mActivity) {
        activity = mActivity;
        adsApiClass = new AdsApiClass();
        AdsMasterClass.getBaseUrl(mActivity, "aHCR0cOHMZ6Ly19j2b2R1pbQmdmCbGM93LRml1uL20FwUaS96hcTHBzEL2CFkcJy1CtYWL5nCZXIAv");
        AdsMasterClass.getToken(mActivity, "ZXlKaGJHY2lPaUpJVXpNNE5DSXNJblI1Y0NJNklrcFhWQ0o5LmV5SnpkV0lpT2lJeE1qTTBOVFkzT0Rrd0lpd2libUZ0WlNJNklrcHZhRzRnUkc5bElpd2lZV1J0YVc0aU9uUnlkV1VzSW1saGRDSTZNVFV4TmpJek9UQXlNbjAuYlFUbno2QXVNSnZtWFhRc1ZQcnhlUU52ekRraW1vN1ZOWHhIZVNCZkNsTHVmbUNWWlJVdXlUd0pGMzExSkh1aA==");
        return adsApiClass;
    }

    public void getAdsData(Activity activity, String mPackageName) {
        appOpenActivity = activity;
        packageName = mPackageName;

        AdsMasterClass.adsDataModel = null;
        NativeAdPreLoad.nativeObject = null;
        NativeAdPreLoad.showApplovin = "";
        AdsPreference.putBoolean(activity, AdsConstant.RATE_NOT_NOW, false);

        if (AdsMasterClass.isConnected(activity)) {
            new getAdsData().execute();
        }
    }

    public class getAdsData extends AsyncTask<String, Void, Void> {

        HttpClient httpClient = HttpClients.createDefault();
        boolean isApiCallDone = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... strings) {
            try {
                HttpGet httpGet = new HttpGet(AdsPreference.getString(activity, AdsConstant.BASE_URL, "") + packageName);
                httpGet.setHeader("Authorization", AdsPreference.getString(activity, AdsConstant.TOKEN, ""));

                RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();
                requestConfigBuilder.setConnectionRequestTimeout(55000).setMaxRedirects(1);
                requestConfigBuilder.setSocketTimeout(55000).setMaxRedirects(1);
                httpGet.setConfig(requestConfigBuilder.build());

                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                String apiContent = httpClient.execute(httpGet, responseHandler);
                isApiCallDone = apiCallDone(apiContent);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            AdsPreference.putInt(activity, AdsConstant.FullScreenAdShowValuePos, 0);
            AdsPreference.putInt(activity, AdsConstant.FullScreenAdShowValuePosTips, 0);
            AdsPreference.putInt(activity, AdsConstant.FullScreenAdShowSeqPos, 0);
            AdsPreference.putInt(activity, AdsConstant.Preload_FullScreenAdSeqPos, 0);
            AdsPreference.putInt(activity, AdsConstant.ShowPreload_FullScreenAdSeqPos, 0);
            AdsPreference.putInt(activity, AdsConstant.BannerAdShowValuePos, 0);
            AdsPreference.putInt(activity, AdsConstant.BannerAdShowSeqPos, 0);
            AdsPreference.putInt(activity, AdsConstant.ExtraBannerAdShowSeqPos, 0);
            AdsPreference.putInt(activity, AdsConstant.NativeAdShowValuePos, 0);
            AdsPreference.putInt(activity, AdsConstant.NativeAdShowSeqPos, 0);
            AdsPreference.putInt(activity, AdsConstant.ExtraNativeAdShowSeqPos, 0);
            AdsPreference.putInt(activity, AdsConstant.RewardedAdShowValuePos, 0);
            AdsPreference.putInt(activity, AdsConstant.RewardedAdShowSeqPos, 0);
            AdsPreference.putInt(activity, AdsConstant.FullScreenAdShowOnBackValuePos, 0);

            AdsPreference.putInt(activity, AdsConstant.GoogleFullScreenIdSeqPos, 0);
            AdsPreference.putInt(activity, AdsConstant.GoogleBannerIdSeqPos, 0);
            AdsPreference.putInt(activity, AdsConstant.GoogleNativeIdSeqPos, 0);
            AdsPreference.putInt(activity, AdsConstant.GoogleRewardedIdSeqPos, 0);
            AdsPreference.putInt(activity, AdsConstant.GoogleAppOpenIdSeqPos, 0);
            AdsPreference.putInt(activity, AdsConstant.FacebookFullScreenIdSeqPos, 0);
            AdsPreference.putInt(activity, AdsConstant.FacebookBannerIdSeqPos, 0);
            AdsPreference.putInt(activity, AdsConstant.FacebookNativeBannerIdSeqPos, 0);
            AdsPreference.putInt(activity, AdsConstant.FacebookNativeIdSeqPos, 0);
            AdsPreference.putInt(activity, AdsConstant.ApplovinFullScreenIdSeqPos, 0);
            AdsPreference.putInt(activity, AdsConstant.ApplovinNativeIdSeqPos, 0);
            AdsPreference.putInt(activity, AdsConstant.ApplovinAppOpenIdSeqPos, 0);

            AdsPreference.putInt(activity, AdsConstant.FullScreenAdRandomValuePos, 0);
            AdsPreference.putInt(activity, AdsConstant.FullScreenAdShowOnBackSeqPos, 0);
            AdsPreference.putInt(activity, AdsConstant.FullScreenAdShowOnTipsSeqPos, 0);

            if (isApiCallDone) {
                AdsGetRandomValue.setFirstValue(activity);
                AdsMasterClass.loadGoogleAppOpen(appOpenActivity);
                FullScreenAdPreload.preloadSequenceFullScreenAd(activity);
                AdsCallBack.onApiCallSuccessListener.onApiCallSuccess(true);
            } else {
                apiCallError();
            }
        }
    }

    private boolean apiCallDone(String apiContent) {
        try {
            if (apiContent != null && apiContent.trim().length() > 0) {
                JSONObject mainObject = new JSONObject(apiContent);
                JSONObject data = mainObject.optJSONObject("data");
                if (data != null) {
                    AdsDataModel adsDataModel = new AdsDataModel();
                    JSONObject ads_data = data.optJSONObject("ads_data");
                    if (ads_data != null) {
                        // ads data
                        String app_open_splash = ads_data.optString("app_open_splash");
                        adsDataModel.setApp_open_splash(app_open_splash);

                        int app_open_main = ads_data.optInt("app_open_main");
                        adsDataModel.setApp_open_main(app_open_main);

                        String app_open_sequence = ads_data.optString("app_open_sequence");
                        adsDataModel.setApp_open_sequence(app_open_sequence);

                        String app_open_fail_sequence = ads_data.optString("app_open_fail_sequence");
                        adsDataModel.setApp_open_fail_sequence(app_open_fail_sequence);

                        String interstitial_show_sequence = ads_data.optString("interstitial_show_sequence");
                        adsDataModel.setInterstitial_show_sequence(interstitial_show_sequence);

                        int interstitial_show_value = ads_data.optInt("interstitial_show_value");
                        adsDataModel.setInterstitial_show_value(interstitial_show_value);

                        String interstitial_show_fail_sequence = ads_data.optString("interstitial_show_fail_sequence");
                        adsDataModel.setInterstitial_show_fail_sequence(interstitial_show_fail_sequence);

                        int interstitial_show_ads_loading = ads_data.optInt("interstitial_show_ads_loading");
                        adsDataModel.setInterstitial_show_ads_loading(interstitial_show_ads_loading);

                        String banner_show_sequence = ads_data.optString("banner_show_sequence");
                        adsDataModel.setBanner_show_sequence(banner_show_sequence);

                        int banner_show_value = ads_data.optInt("banner_show_value");
                        adsDataModel.setBanner_show_value(banner_show_value);

                        String banner_show_fail_sequence = ads_data.optString("banner_show_fail_sequence");
                        adsDataModel.setBanner_show_fail_sequence(banner_show_fail_sequence);

                        String native_show_sequence = ads_data.optString("native_show_sequence");
                        adsDataModel.setNative_show_sequence(native_show_sequence);

                        int native_show_value = ads_data.optInt("native_show_value");
                        adsDataModel.setNative_show_value(native_show_value);

                        String native_show_fail_sequence = ads_data.optString("native_show_fail_sequence");
                        adsDataModel.setNative_show_fail_sequence(native_show_fail_sequence);

                        int native_preload = ads_data.optInt("native_preload");
                        adsDataModel.setNative_preload(native_preload);

                        int native_list_show_value = ads_data.optInt("native_list_show_value");
                        adsDataModel.setNative_list_show_value(native_list_show_value);

                        String rewarded_show_sequence = ads_data.optString("rewarded_show_sequence");
                        adsDataModel.setRewarded_show_sequence(rewarded_show_sequence);

                        int rewarded_show_value = ads_data.optInt("rewarded_show_value");
                        adsDataModel.setRewarded_show_value(rewarded_show_value);

                        String rewarded_show_fail_sequence = ads_data.optString("rewarded_show_fail_sequence");
                        adsDataModel.setRewarded_show_fail_sequence(rewarded_show_fail_sequence);

                        int app_version_code = ads_data.optInt("app_version_code");
                        adsDataModel.setApp_version_code(app_version_code);

                        int app_inreview = ads_data.optInt("app_inreview");
                        adsDataModel.setApp_inreview(app_inreview);

                        int app_update_dialog = ads_data.optInt("app_update_dialog");
                        adsDataModel.setApp_update_dialog(app_update_dialog);

                        int show_tips_screen = ads_data.optInt("show_tips_screen");
                        adsDataModel.setShow_tips_screen(show_tips_screen);

                        String show_tips_interstitial_value = ads_data.optString("show_tips_interstitial_value");
                        adsDataModel.setShow_tips_interstitial_value(show_tips_interstitial_value);

                        String google_interstitial_id = ads_data.optString("google_interstitial_id");
                        adsDataModel.setGoogle_interstitial_id(google_interstitial_id);

                        String google_banner_id = ads_data.optString("google_banner_id");
                        adsDataModel.setGoogle_banner_id(google_banner_id);

                        String google_native_id = ads_data.optString("google_native_id");
                        adsDataModel.setGoogle_native_id(google_native_id);

                        String google_appopen_id = ads_data.optString("google_appopen_id");
                        adsDataModel.setGoogle_appopen_id(google_appopen_id);

                        String google_rewarded_id = ads_data.optString("google_rewarded_id");
                        adsDataModel.setGoogle_rewarded_id(google_rewarded_id);

                        String adx_interstitial_id = ads_data.optString("adx_interstitial_id");
                        adsDataModel.setAdx_interstitial_id(adx_interstitial_id);

                        String adx_banner_id = ads_data.optString("adx_banner_id");
                        adsDataModel.setAdx_banner_id(adx_banner_id);

                        String adx_native_id = ads_data.optString("adx_native_id");
                        adsDataModel.setAdx_native_id(adx_native_id);

                        String adx_appopen_id = ads_data.optString("adx_appopen_id");
                        adsDataModel.setAdx_appopen_id(adx_appopen_id);

                        String adx_rewarded_id = ads_data.optString("adx_rewarded_id");
                        adsDataModel.setAdx_rewarded_id(adx_rewarded_id);

                        String facebook_interstitial_id = ads_data.optString("facebook_interstitial_id");
                        adsDataModel.setFacebook_interstitial_id(facebook_interstitial_id);

                        String facebook_banner_id = ads_data.optString("facebook_banner_id");
                        adsDataModel.setFacebook_banner_id(facebook_banner_id);

                        String facebook_native_id = ads_data.optString("facebook_native_id");
                        adsDataModel.setFacebook_native_id(facebook_native_id);

                        String facebook_native_banner_id = ads_data.optString("facebook_native_banner_id");
                        adsDataModel.setFacebook_native_banner_id(facebook_native_banner_id);

                        String applovin_interstitial_id = ads_data.optString("applovin_interstitial_id");
                        adsDataModel.setApplovin_interstitial_id(applovin_interstitial_id);

                        String applovin_native_id = ads_data.optString("applovin_native_id");
                        adsDataModel.setApplovin_native_id(applovin_native_id);

                        String applovin_app_open_id = ads_data.optString("applovin_app_open_id");
                        adsDataModel.setApplovin_app_open_id(applovin_app_open_id);

                        String qureka_url = ads_data.optString("qureka_url");
                        adsDataModel.setQureka_url(qureka_url);

                        String show_ads_on_back = ads_data.optString("show_ads_on_back");
                        adsDataModel.setShow_ads_on_back(show_ads_on_back);

                        String native_bg_color = ads_data.optString("native_bg_color");
                        adsDataModel.setNative_bg_color(native_bg_color);

                        String native_button_color = ads_data.optString("native_button_color");
                        adsDataModel.setNative_button_color(native_button_color);

                        int show_log = ads_data.optInt("show_log");
                        adsDataModel.setShow_log(show_log);

                        // other ada data
                        int random_max_number = ads_data.optInt("random_max_number");
                        adsDataModel.setRandom_max_number(random_max_number);

                        int show_exit_dialog_native = ads_data.optInt("show_exit_dialog_native");
                        adsDataModel.setShow_exit_dialog_native(show_exit_dialog_native);

                        int check_app_status = ads_data.optInt("check_app_status");
                        adsDataModel.setCheck_app_status(check_app_status);

                        String upgrade_package_name = ads_data.optString("upgrade_package_name");
                        adsDataModel.setUpgrade_package_name(upgrade_package_name);

                        int show_extra_features = ads_data.optInt("show_extra_features");
                        adsDataModel.setShow_extra_features(show_extra_features);

                        String privacy_policy_url = ads_data.optString("privacy_policy_url");
                        adsDataModel.setPrivacy_policy_url(privacy_policy_url);

                        // extra flag in other ads data
                        String extra_flag_a = ads_data.optString("extra_flag_a");
                        adsDataModel.setExtra_flag_a(extra_flag_a);

                        String extra_flag_b = ads_data.optString("extra_flag_b");
                        adsDataModel.setExtra_flag_b(extra_flag_b);

                        String extra_flag_c = ads_data.optString("extra_flag_c");
                        adsDataModel.setExtra_flag_c(extra_flag_c);

                        String extra_flag_d = ads_data.optString("extra_flag_d");
                        adsDataModel.setExtra_flag_d(extra_flag_d);

                        String extra_flag_e = ads_data.optString("extra_flag_e");
                        adsDataModel.setExtra_flag_e(extra_flag_e);

                        String extra_flag_f = ads_data.optString("extra_flag_f");
                        adsDataModel.setExtra_flag_f(extra_flag_f);

                        String extra_flag_g = ads_data.optString("extra_flag_g");
                        adsDataModel.setExtra_flag_g(extra_flag_g);

                        String extra_flag_h = ads_data.optString("extra_flag_h");
                        adsDataModel.setExtra_flag_h(extra_flag_h);

                        String extra_flag_i = ads_data.optString("extra_flag_i");
                        adsDataModel.setExtra_flag_i(extra_flag_i);

                        String extra_flag_j = ads_data.optString("extra_flag_j");
                        adsDataModel.setExtra_flag_j(extra_flag_j);
                    }

                    adsDataModel.setIs_appopen_loading(false);

                    if (adsDataModel.getRandom_max_number() > 0) {
                        ArrayList<Integer> arrayList = new ArrayList<>();
                        for (int i = 1; i <= adsDataModel.getRandom_max_number(); i++) {
                            arrayList.add(i);
                        }
                        adsDataModel.setMaxNumberList(arrayList);
                        if (adsDataModel.getMaxNumberList() != null && adsDataModel.getMaxNumberList().size() > 0) {
                            int randomIndex = new Random().nextInt(adsDataModel.getMaxNumberList().size());
                            AdsGetRandomValue.selectedNumber = adsDataModel.getMaxNumberList().remove(randomIndex);
                            AdsMasterClass.showAdTag(AdsLogTag.AdsGetRandomValue.name(), "Ad Show Value " + AdsGetRandomValue.selectedNumber);
                        }
                    }

                    AdsMasterClass.setAdsDataModel(adsDataModel);
                    AdsPreference.putString(activity, AdsConstant.RESPONSE, apiContent);
                    AdsMasterClass.setAdsDefaultValue(activity, packageName);
                    return true;
                }
            }
            return false;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void apiCallError() {
        boolean isApiCallDone = apiCallDone(AdsPreference.getString(activity, AdsConstant.RESPONSE, ""));

        if (isApiCallDone) {
            AdsGetRandomValue.setFirstValue(activity);
            AdsMasterClass.loadGoogleAppOpen(appOpenActivity);
            FullScreenAdPreload.preloadSequenceFullScreenAd(activity);
            AdsCallBack.onApiCallSuccessListener.onApiCallSuccess(true);
        } else {
            AdsDataModel adsDataModel = new AdsDataModel();
            adsDataModel.setApp_open_main(1);
            adsDataModel.setApp_open_sequence("q");
            adsDataModel.setInterstitial_show_sequence("q");
            adsDataModel.setInterstitial_show_value(2);
            adsDataModel.setInterstitial_show_ads_loading(0);
            adsDataModel.setBanner_show_sequence("q");
            adsDataModel.setBanner_show_value(1);
            adsDataModel.setNative_show_sequence("q");
            adsDataModel.setNative_show_value(2);
            adsDataModel.setNative_preload(1);
            adsDataModel.setNative_list_show_value(5);
            adsDataModel.setShow_exit_dialog_native(1);
            AdsMasterClass.setAdsDataModel(adsDataModel);

            FullScreenAdPreload.preloadSequenceFullScreenAd(activity);
            AdsCallBack.onApiCallSuccessListener.onApiCallSuccess(false);
        }
    }
}
