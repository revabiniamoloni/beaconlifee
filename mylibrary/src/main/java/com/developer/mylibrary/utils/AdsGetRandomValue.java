package com.developer.mylibrary.utils;

import android.app.Activity;

import com.developer.mylibrary.eum_class.AdsLogTag;

import java.util.ArrayList;
import java.util.Random;

public class AdsGetRandomValue {

    public static int selectedNumber = 0;

    public static boolean getRandomAdShowValue(Activity activity) {
        int pos = AdsPreference.getInt(activity, AdsConstant.FullScreenAdRandomValuePos, 0);
        int nextPos = pos + 1;
        if (nextPos == selectedNumber) {
            AdsPreference.putInt(activity, AdsConstant.FullScreenAdRandomValuePos, nextPos);
            if (nextPos >= AdsMasterClass.getAdsDataModel().getRandom_max_number()) {
                getNewAdShowValue(activity);
            }
            return true;
        }
        if (nextPos >= AdsMasterClass.getAdsDataModel().getRandom_max_number()) {
            getNewAdShowValue(activity);
            return false;
        }
        AdsPreference.putInt(activity, AdsConstant.FullScreenAdRandomValuePos, nextPos);
        return false;
    }

    public static void getNewAdShowValue(Activity activity) {
        int randomIndex = new Random().nextInt(AdsMasterClass.getAdsDataModel().getMaxNumberList().size());
        selectedNumber = AdsMasterClass.getAdsDataModel().getMaxNumberList().remove(randomIndex);
        AdsMasterClass.showAdTag(AdsLogTag.AdsGetRandomValue.name(), "Ad Show Value " + selectedNumber);

        AdsPreference.putInt(activity, AdsConstant.FullScreenAdRandomValuePos, 0);

        if (AdsMasterClass.getAdsDataModel().getMaxNumberList().size() == 0) {
            ArrayList<Integer> arrayList = new ArrayList<>();
            for (int i = 1; i <= AdsMasterClass.getAdsDataModel().getRandom_max_number(); i++) {
                arrayList.add(i);
            }
            AdsMasterClass.getAdsDataModel().setMaxNumberList(arrayList);
        }
    }

    public static void setFirstValue(Activity activity) {
        if (AdsMasterClass.getAdsDataModel() != null) {
            AdsMasterClass.getAdsDataModel().setNext_on_back_value(AdsGetRandomValue.getNextFullScreenAdOnBackValue(activity));
            AdsMasterClass.getAdsDataModel().setNext_tips_fullscreen_value(AdsGetRandomValue.getNextTipsFullScreenAdValue(activity));
        }
    }

    public static int getNextFullScreenAdOnBackValue(Activity activity) {
        if (AdsMasterClass.getAdsDataModel() != null && AdsMasterClass.getAdsDataModel().getShow_ads_on_back() != null && AdsMasterClass.getAdsDataModel().getShow_ads_on_back().trim().length() > 0 && !AdsMasterClass.getAdsDataModel().getShow_ads_on_back().equals("0")) {
            String adsSequence = AdsMasterClass.getAdsDataModel().getShow_ads_on_back().trim();
            String[] values = adsSequence.split("-");
            if (values != null && values.length > 0) {
                int pos = AdsPreference.getInt(activity, AdsConstant.FullScreenAdShowOnBackSeqPos, 0);
                if (pos < values.length) {
                    String ad = values[pos];
                    int nextPos = pos + 1;
                    AdsPreference.putInt(activity, AdsConstant.FullScreenAdShowOnBackSeqPos, nextPos);
                    AdsMasterClass.showAdTag(AdsLogTag.AdsMasterClass.name(), "getNextFullScreenAdOnBackValue: " + ad);
                    return Integer.parseInt(ad);
                } else {
                    String ad = values[0];
                    AdsPreference.putInt(activity, AdsConstant.FullScreenAdShowOnBackSeqPos, 1);
                    AdsMasterClass.showAdTag(AdsLogTag.AdsMasterClass.name(), "getNextFullScreenAdOnBackValue: " + ad);
                    return Integer.parseInt(ad);
                }
            }
            return 0;
        }
        return 0;
    }

    public static int getNextTipsFullScreenAdValue(Activity activity) {
        if (AdsMasterClass.getAdsDataModel() != null && AdsMasterClass.getAdsDataModel().getShow_tips_interstitial_value() != null && AdsMasterClass.getAdsDataModel().getShow_tips_interstitial_value().trim().length() > 0 && !AdsMasterClass.getAdsDataModel().getShow_tips_interstitial_value().equals("0")) {
            String adsSequence = AdsMasterClass.getAdsDataModel().getShow_tips_interstitial_value().trim();
            String[] values = adsSequence.split("-");
            if (values != null && values.length > 0) {
                int pos = AdsPreference.getInt(activity, AdsConstant.FullScreenAdShowOnTipsSeqPos, 0);
                if (pos < values.length) {
                    String ad = values[pos];
                    int nextPos = pos + 1;
                    AdsPreference.putInt(activity, AdsConstant.FullScreenAdShowOnTipsSeqPos, nextPos);
                    AdsMasterClass.showAdTag(AdsLogTag.AdsMasterClass.name(), "getNextTipsFullScreenAdValue: " + ad);
                    return Integer.parseInt(ad);
                } else {
                    String ad = values[0];
                    AdsPreference.putInt(activity, AdsConstant.FullScreenAdShowOnTipsSeqPos, 1);
                    AdsMasterClass.showAdTag(AdsLogTag.AdsMasterClass.name(), "getNextTipsFullScreenAdValue: " + ad);
                    return Integer.parseInt(ad);
                }
            }
            return 0;
        }
        return 0;
    }
}
