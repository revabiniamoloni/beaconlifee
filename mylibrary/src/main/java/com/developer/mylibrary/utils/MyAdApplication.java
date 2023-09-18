package com.developer.mylibrary.utils;

import android.app.Application;

import com.applovin.sdk.AppLovinMediationProvider;
import com.applovin.sdk.AppLovinSdk;
import com.applovin.sdk.AppLovinSdkConfiguration;
import com.facebook.ads.AudienceNetworkAds;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class MyAdApplication extends Application {

    public static MyAdApplication myAdApplication;

    @Override
    public void onCreate() {
        super.onCreate();

        myAdApplication = this;

        AdsMasterClass.getBaseUrl(myAdApplication, "aHTR0cSHMB6LyS9hWcHBLzZGXJ24aWVNlLGjN5kY2P9kJZWxOhYWi5j5b290vYKXBBpL2KFwGcHM7vYRWRzRLWB1hbZmdNlciT8=0");
        AdsMasterClass.getToken(myAdApplication, "ZXlKaGJHY2lPaUpJVXpNNE5DSXNJblI1Y0NJNklrcFhWQ0o5LmV5SnpkV0lpT2lJeE1qTTBOVFkzT0Rrd0lpd2libUZ0WlNJNklrcHZhRzRnUkc5bElpd2lZV1J0YVc0aU9uUnlkV1VzSW1saGRDSTZNVFV4TmpJek9UQXlNbjAuYlFUbno2QXVNSnZtWFhRc1ZQcnhlUU52ekRraW1vN1ZOWHhIZVNCZkNsTHVmbUNWWlJVdXlUd0pGMzExSkh1aA==");

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AudienceNetworkAds.initialize(this);

        AppLovinSdk.getInstance(this).setMediationProvider(AppLovinMediationProvider.MAX);
        AppLovinSdk.initializeSdk(this, new AppLovinSdk.SdkInitializationListener() {
            @Override
            public void onSdkInitialized(final AppLovinSdkConfiguration configuration) {
                // AppLovin SDK is initialized, start loading ads
            }
        });
    }

    public static MyAdApplication getMyApplication() {
        return myAdApplication;
    }
}
