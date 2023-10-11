package com.developer.mylibrary.appopen_ad;

import static androidx.lifecycle.Lifecycle.Event.ON_RESUME;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxAppOpenAd;
import com.developer.mylibrary.R;
import com.developer.mylibrary.eum_class.AdsLogTag;
import com.developer.mylibrary.eum_class.AllAdsType;
import com.developer.mylibrary.utils.AdsConstant;
import com.developer.mylibrary.utils.AdsMasterClass;
import com.developer.mylibrary.utils.AdsShareUtils;
import com.developer.mylibrary.utils.MyAdApplication;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;

public class AppOpenAdLoad implements Application.ActivityLifecycleCallbacks, LifecycleObserver, MaxAdListener {

    protected AppOpenAd appOpenAd = null;
    protected AppOpenAd.AppOpenAdLoadCallback loadCallback;

    protected MaxAppOpenAd maxAppOpenAd;

    protected MyAdApplication myAdApplication;
    private Activity currentActivity;
    protected Activity appOpenActivity;

    private static boolean isShowingAd = false;
    private static boolean isLoaded = false;

    private String currentAdsType = "";

    public AppOpenAdLoad(MyAdApplication application, Activity appOpenActivity) {
        this.myAdApplication = application;
        this.appOpenActivity = appOpenActivity;
        this.myAdApplication.registerActivityLifecycleCallbacks(this);
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);

        AdsMasterClass.showAdTag(AdsLogTag.AppOpenAdLoad.name(), "AppOpenSequence - " + AdsMasterClass.getAdsDataModel().getApp_open_sequence());
        loadAd(MyAdApplication.getMyApplication(), AdsMasterClass.getAdsDataModel().getApp_open_sequence());
    }

    @OnLifecycleEvent(ON_RESUME)
    public void onResume() {
        AdsMasterClass.showAdTag(AdsLogTag.AppOpenAdLoad.name(), "loadGoogleAppOpen - ON_RESUME");
        showAdIfAvailable();
    }

    private void loadAd(Context context, String currentAd) {
        if (AdsMasterClass.getAdsDataModel() != null && AdsMasterClass.getAdsDataModel().getApp_open_main() == 1) {
            this.currentAdsType = currentAd;
            if (currentAd.equals(AllAdsType.a.name())) {
                loadAppLovinAd(AdsMasterClass.getApplovinAppOpenId(context, AdsMasterClass.getAdsDataModel().getApplovin_app_open_id()));
            } else if (!currentAd.equals(AllAdsType.q.name())) {
                loadGoogleAd(AdsMasterClass.getGoogleAppOpenId(context, currentAd));
            }
        }
    }

    private void loadGoogleAd(String adID) {
        if (adID != null && adID.trim().length() > 0) {
            if (isLoaded || isGoogleAdAvailable()) {
                return;
            }
            isLoaded = true;
            AdsMasterClass.getAdsDataModel().setIs_appopen_loading(true);
            this.loadCallback = new AppOpenAd.AppOpenAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull AppOpenAd appOpenAd) {
                    super.onAdLoaded(appOpenAd);
                    AdsMasterClass.showAdTag(AdsLogTag.AppOpenAdLoad.name(), "loadGoogleAppOpen - load");
                    AppOpenAdLoad.this.appOpenAd = appOpenAd;
                    isLoaded = false;
                    AdsMasterClass.getAdsDataModel().setIs_appopen_loading(false);
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    AdsMasterClass.showAdTag(AdsLogTag.AppOpenAdLoad.name(), "loadGoogleAppOpen - failed " + loadAdError.getMessage());
                    isLoaded = false;

                    if (AdsMasterClass.getAdsDataModel().isIs_appopen_fail_check()) {
                        AdsMasterClass.getAdsDataModel().setIs_appopen_loading(false);
                        AdsMasterClass.getAdsDataModel().setIs_appopen_fail_check(false);
                    } else {
                        AdsMasterClass.getAdsDataModel().setIs_appopen_fail_check(true);
                        String ad = AdsMasterClass.getNextAppOpenFailedAd(AdsMasterClass.getAdsDataModel().getApp_open_sequence());
                        loadAd(MyAdApplication.getMyApplication(), ad);
                    }
                }
            };

            AdRequest adRequest = getAdRequest();
            AppOpenAd.load(myAdApplication, adID, adRequest, loadCallback);
        }
    }

    private void loadAppLovinAd(String adID) {
        if (adID != null && adID.trim().length() > 0) {
            if (isLoaded || isApplovinAdAvailable()) {
                return;
            }
            isLoaded = true;
            AdsMasterClass.getAdsDataModel().setIs_appopen_loading(true);

            maxAppOpenAd = new MaxAppOpenAd(adID, MyAdApplication.getMyApplication());
            maxAppOpenAd.setListener(this);
            maxAppOpenAd.loadAd();
        }
    }

    private void loadQurekaAds(final Activity activity) {
        if (AdsMasterClass.getAdsDataModel() != null && AdsMasterClass.getAdsDataModel().getQureka_url().trim().length() > 0) {
            Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(AdsConstant.QUREKA_FULL_SCREEN_LIST[Integer.parseInt(AdsMasterClass.showQurekaImage(activity))]);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);

            CardView card_click = dialog.findViewById(R.id.card_click);
            ImageView iv_close = dialog.findViewById(R.id.iv_close_qureka);
            TextView tv_ad = dialog.findViewById(R.id.tv_ad_qureka);

            Animation slide_down = AnimationUtils.loadAnimation(activity, R.anim.slide_up);
            card_click.setAnimation(slide_down);
            Animation left_in = AnimationUtils.loadAnimation(activity, R.anim.slide_in_left);
            tv_ad.setAnimation(left_in);
            Animation right_in = AnimationUtils.loadAnimation(activity, R.anim.slide_in_right);
            iv_close.setAnimation(right_in);

            iv_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    isShowingAd = false;
                    AdsMasterClass.showAdTag(AdsLogTag.AppOpenAdLoad.name(), "Qureka Ad dismissed");
                    loadAd(MyAdApplication.getMyApplication(), AdsMasterClass.getAdsDataModel().getApp_open_sequence());
                }
            });

            card_click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AdsShareUtils.showQurekaAds(activity);
                }
            });

            dialog.show();
        } else {
            isShowingAd = false;
            AdsMasterClass.showAdTag(AdsLogTag.AppOpenAdLoad.name(), "Qureka Ad dismissed");
            loadAd(MyAdApplication.getMyApplication(), AdsMasterClass.getAdsDataModel().getApp_open_sequence());
        }
    }

    private void showAdIfAvailable() {
        if (currentActivity != null && currentActivity.getComponentName() != null && currentActivity.getComponentName().getClassName() != null
                && !currentActivity.getComponentName().getClassName().equals(appOpenActivity.getClass().getName())
                && AdsMasterClass.getAdsDataModel() != null && AdsMasterClass.getAdsDataModel().getApp_open_main() == 1) {
            if (isShowingAd) {
                return;
            }

            if (!isGoogleAdAvailable() && !isApplovinAdAvailable() && !currentAdsType.equals("q")) {
                if (!AdsMasterClass.getAdsDataModel().isIs_appopen_loading()) {
                    AdsMasterClass.getAdsDataModel().setIs_appopen_fail_check(false);
                    loadAd(MyAdApplication.getMyApplication(), AdsMasterClass.getAdsDataModel().getApp_open_sequence());
                }
                return;
            }

            if (currentAdsType.equals("q")) {
                isShowingAd = true;
                loadQurekaAds(currentActivity);
            } else if (maxAppOpenAd != null && maxAppOpenAd.isReady()) {
                isShowingAd = true;
                maxAppOpenAd.showAd();
            } else {
                FullScreenContentCallback fullScreenContentCallback = new FullScreenContentCallback() {
                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                        super.onAdFailedToShowFullScreenContent(adError);
                        AdsMasterClass.showAdTag(AdsLogTag.AppOpenAdLoad.name(), "loadGoogleAppOpen - failed to show : " + adError);
                        AppOpenAdLoad.this.appOpenAd = null;
                        isShowingAd = false;
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        super.onAdShowedFullScreenContent();
                        AdsMasterClass.showAdTag(AdsLogTag.AppOpenAdLoad.name(), "loadGoogleAppOpen - show");
                    }

                    @Override
                    public void onAdDismissedFullScreenContent() {
                        super.onAdDismissedFullScreenContent();
                        AdsMasterClass.showAdTag(AdsLogTag.AppOpenAdLoad.name(), "loadGoogleAppOpen - dismiss");

                        AppOpenAdLoad.this.appOpenAd = null;
                        isShowingAd = false;
                        AdsMasterClass.getAdsDataModel().setIs_appopen_loading(false);
                        AdsMasterClass.getAdsDataModel().setIs_appopen_fail_check(false);
                        loadAd(MyAdApplication.getMyApplication(), AdsMasterClass.getAdsDataModel().getApp_open_sequence());
                    }

                    @Override
                    public void onAdImpression() {
                        super.onAdImpression();
                    }
                };

                appOpenAd.setFullScreenContentCallback(fullScreenContentCallback);
                isShowingAd = true;
                appOpenAd.show(currentActivity);
            }
        }
    }

    private AdRequest getAdRequest() {
        return new AdRequest.Builder().build();
    }

    private boolean isGoogleAdAvailable() {
        return appOpenAd != null;
    }

    private boolean isApplovinAdAvailable() {
        return maxAppOpenAd != null && maxAppOpenAd.isReady();
    }

    @Override
    public void onAdLoaded(MaxAd ad) {
        AdsMasterClass.showAdTag(AdsLogTag.AppOpenAdLoad.name(), "Applovin Ad loaded");
        isLoaded = false;
        AdsMasterClass.getAdsDataModel().setIs_appopen_loading(false);
    }

    @Override
    public void onAdDisplayed(MaxAd ad) {
        AdsMasterClass.showAdTag(AdsLogTag.AppOpenAdLoad.name(), "Applovin Ad showed");
    }

    @Override
    public void onAdHidden(MaxAd ad) {
        AdsMasterClass.showAdTag(AdsLogTag.AppOpenAdLoad.name(), "Applovin Ad dismissed");

        AppOpenAdLoad.this.maxAppOpenAd.destroy();
        AppOpenAdLoad.this.maxAppOpenAd = null;
        isShowingAd = false;
        AdsMasterClass.getAdsDataModel().setIs_appopen_loading(false);
        AdsMasterClass.getAdsDataModel().setIs_appopen_fail_check(false);
        loadAd(MyAdApplication.getMyApplication(), AdsMasterClass.getAdsDataModel().getApp_open_sequence());
    }

    @Override
    public void onAdClicked(MaxAd ad) {

    }

    @Override
    public void onAdLoadFailed(String adUnitId, MaxError error) {
        AdsMasterClass.showAdTag(AdsLogTag.AppOpenAdLoad.name(), "Applovin Ad failed " + error.getMessage());
        isLoaded = false;

        if (AdsMasterClass.getAdsDataModel().isIs_appopen_fail_check()) {
            AdsMasterClass.getAdsDataModel().setIs_appopen_loading(false);
            AdsMasterClass.getAdsDataModel().setIs_appopen_fail_check(false);
        } else {
            AdsMasterClass.getAdsDataModel().setIs_appopen_fail_check(true);
            String ad = AdsMasterClass.getNextAppOpenFailedAd(AdsMasterClass.getAdsDataModel().getApp_open_sequence());
            loadAd(MyAdApplication.getMyApplication(), ad);
        }
    }

    @Override
    public void onAdDisplayFailed(MaxAd ad, MaxError error) {
        AppOpenAdLoad.this.maxAppOpenAd = null;
        isShowingAd = false;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    }

    @Override
    public void onActivityStarted(Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivityResumed(Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivityStopped(Activity activity) {
    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        currentActivity = null;
    }
}