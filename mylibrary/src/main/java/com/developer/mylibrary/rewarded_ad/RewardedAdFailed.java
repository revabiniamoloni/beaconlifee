package com.developer.mylibrary.rewarded_ad;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.developer.mylibrary.eum_class.AdsLogTag;
import com.developer.mylibrary.eum_class.AllAdsType;
import com.developer.mylibrary.utils.AdsMasterClass;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.admanager.AdManagerAdRequest;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

public class RewardedAdFailed {

    public static Activity activityRewardedAdFailed;

    public static void showRewardedAdOnFailed(Activity activity, String currentAd) {
        activityRewardedAdFailed = activity;
        String nextFailedAd = AdsMasterClass.getNextRewardedFailedAd(currentAd);
        AdsMasterClass.showAdTag(AdsLogTag.RewardedAdFailed.name(), "showRewardedAdOnFailed - " + nextFailedAd);
        if (nextFailedAd.equals(AllAdsType.g.name())) {
            loadGoogleRewarded(activity, AdsMasterClass.getGoogleRewardedId(activity, AdsMasterClass.getAdsDataModel().getGoogle_rewarded_id()));
        } else if (nextFailedAd.equals(AllAdsType.adx.name())) {
            loadGoogleRewarded(activity, AdsMasterClass.getGoogleRewardedId(activity, AdsMasterClass.getAdsDataModel().getAdx_rewarded_id()));
        }else {
            AdsMasterClass.dismissAdsProgressDialog();
            AdsMasterClass.onRewardEarnedListener.onRewardEarned(false);
        }
    }

    public static void loadGoogleRewarded(final Activity activity, String adID) {
        if (adID != null && adID.trim().length() > 0) {
            AdManagerAdRequest adRequest = new AdManagerAdRequest.Builder().build();
            RewardedAd.load(activity, adID, adRequest, new RewardedAdLoadCallback() {
                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    AdsMasterClass.showAdTag(AdsLogTag.RewardedAdFailed.name(), "loadGoogleRewardedFailed - failed " + loadAdError.getMessage());
                    AdsMasterClass.dismissAdsProgressDialog();
                    AdsMasterClass.onRewardEarnedListener.onRewardEarned(false);
                }

                @Override
                public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                    super.onAdLoaded(rewardedAd);
                    AdsMasterClass.showAdTag(AdsLogTag.RewardedAdFailed.name(), "loadGoogleRewardedFailed - loaded");

                    AdsMasterClass.dismissAdsProgressDialog();
                    rewardedAd.show(activity, new OnUserEarnedRewardListener() {
                        @Override
                        public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                            AdsMasterClass.showAdTag(AdsLogTag.RewardedAdFailed.name(), "onUserEarnedReward - complete");
                            RewardedAdLoad.isEarnedReward = true;
                        }
                    });

                    rewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdFailedToShowFullScreenContent(@NonNull com.google.android.gms.ads.AdError adError) {
                            super.onAdFailedToShowFullScreenContent(adError);
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            super.onAdShowedFullScreenContent();
                        }

                        @Override
                        public void onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent();
                            AdsMasterClass.showAdTag(AdsLogTag.RewardedAdFailed.name(), "loadGoogleRewardedFailed - dismiss");

                            if (RewardedAdLoad.isEarnedReward) {
                                RewardedAdLoad.isEarnedReward = false;
                                AdsMasterClass.onRewardEarnedListener.onRewardEarned(true);
                            } else {
                                AdsMasterClass.onRewardEarnedListener.onRewardEarned(false);
                            }
                        }
                    });
                }
            });
        } else {
            AdsMasterClass.dismissAdsProgressDialog();
            AdsMasterClass.onRewardEarnedListener.onRewardEarned(false);
        }
    }
}
