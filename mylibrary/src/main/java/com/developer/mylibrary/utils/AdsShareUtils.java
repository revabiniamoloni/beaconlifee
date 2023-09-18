package com.developer.mylibrary.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import androidx.browser.customtabs.CustomTabsIntent;

import com.developer.mylibrary.activity.CustomChromeActivity;

public class AdsShareUtils {

    public static Activity mActivity;
    public static Intent mIntent;

    public static void rateApp(Activity activity, String packageName) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://play.google.com/store/apps/details?id=" + packageName));
        intent.setPackage("com.android.vending");
        activity.startActivity(intent);
    }

    public static void shareApp(Activity activity, String appName, String shareText) {
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, appName);
            intent.putExtra(Intent.EXTRA_TEXT, shareText + "\n\n" + "https://play.google.com/store/apps/details?id=" + activity.getPackageName());
            activity.startActivity(Intent.createChooser(intent, "Choose One"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showQurekaAds(Activity activity) {
        if (!AdsMasterClass.getAdsDataModel().getQureka_url().isEmpty()) {
            CustomTabsIntent build = new CustomTabsIntent.Builder().build();
            build.intent.setPackage("com.android.chrome");
            build.launchUrl(activity, Uri.parse(AdsMasterClass.getAdsDataModel().getQureka_url()));
        }
    }

    public static void showDirectQurekaAds(Activity activity, Intent intent) {
        mActivity = activity;
        mIntent = intent;
        activity.startActivity(new Intent(activity, CustomChromeActivity.class));
    }
}
