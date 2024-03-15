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

public class RewardedAdLoad {

    public static Activity activityRewarded;
    public static boolean isEarnedReward = false;

    public static void loadSequenceRewardedAd(Activity activity) {
        activityRewarded = activity;
        String nextAd = AdsMasterClass.getNextRewardedAd(activity);
        if (nextAd.equals(AllAdsType.g.name())) {
            loadGoogleRewarded(activity, AdsMasterClass.getGoogleRewardedId(activity, AdsMasterClass.getAdsDataModel().getGoogle_rewarded_id()), nextAd);
        } else if (nextAd.equals(AllAdsType.adx.name())) {
            loadGoogleRewarded(activity, AdsMasterClass.getGoogleRewardedId(activity, AdsMasterClass.getAdsDataModel().getAdx_rewarded_id()), nextAd);
        }
    }

    public static void loadGoogleRewarded(final Activity activity, String adID, String currentAd) {
        if (adID != null && adID.trim().length() > 0) {
            AdsMasterClass.showAdProgressDialog(activity);
            AdManagerAdRequest adRequest = new AdManagerAdRequest.Builder().build();
            RewardedAd.load(activity, adID, adRequest, new RewardedAdLoadCallback() {
                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    AdsMasterClass.showAdTag(AdsLogTag.RewardedAdLoad.name(), "loadGoogleRewarded - failed " + loadAdError.getMessage());
                    RewardedAdFailed.showRewardedAdOnFailed(activity, currentAd);
                }

                @Override
                public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                    super.onAdLoaded(rewardedAd);
                    AdsMasterClass.showAdTag(AdsLogTag.RewardedAdLoad.name(), "loadGoogleRewarded - loaded");

                    AdsMasterClass.dismissAdsProgressDialog();
                    rewardedAd.show(activity, new OnUserEarnedRewardListener() {
                        @Override
                        public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                            AdsMasterClass.showAdTag(AdsLogTag.RewardedAdLoad.name(), "onUserEarnedReward - complete");
                            isEarnedReward = true;
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
                            AdsMasterClass.showAdTag(AdsLogTag.RewardedAdLoad.name(), "loadGoogleRewarded - dismiss");

                            if (isEarnedReward) {
                                isEarnedReward = false;
                                AdsMasterClass.onRewardEarnedListener.onRewardEarned(true);
                            } else {
                                AdsMasterClass.onRewardEarnedListener.onRewardEarned(false);
                            }
                        }
                    });
                }
            });
        }
    }
}
