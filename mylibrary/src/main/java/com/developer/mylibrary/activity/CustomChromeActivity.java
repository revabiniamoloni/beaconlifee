package com.developer.mylibrary.activity;

import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;

import com.developer.mylibrary.utils.AdsMasterClass;
import com.developer.mylibrary.utils.AdsShareUtils;

public class CustomChromeActivity extends AppCompatActivity {

    public static boolean isFinishActivity = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            CustomTabsIntent build = new CustomTabsIntent.Builder().build();
            build.intent.setPackage("com.android.chrome");
            build.launchUrl(this, Uri.parse(AdsMasterClass.getAdsDataModel().getQureka_url()));
            isFinishActivity = false;
        } catch (Exception e) {
            isFinishActivity = true;
            callAfterAdsShow();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        callAfterAdsShow();
    }

    private void callAfterAdsShow() {
        if (isFinishActivity) {
            if (AdsShareUtils.mActivity != null && AdsShareUtils.mIntent != null)
                AdsMasterClass.startNextActivity(AdsShareUtils.mActivity, AdsShareUtils.mIntent);
            finish();
        }
        isFinishActivity = true;
    }
}
