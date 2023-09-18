package com.developer.mylibrary.utils;

import android.app.Activity;
import android.content.Intent;

public class AdsCallBack {

    public static OnApiCallSuccessListener onApiCallSuccessListener;
    public static OnFullscreenAdDismissListener onFullscreenAdDismissListener;

    public interface OnApiCallSuccessListener {
        void onApiCallSuccess(boolean isCheckUpdate);
    }

    public interface OnFullscreenAdDismissListener {
        void onFullscreenAdDismiss(Activity activity, Intent intent);
    }

    public static void setOnOnApiCallDoneListener(OnApiCallSuccessListener mOnApiCallSuccessListener) {
        onApiCallSuccessListener = mOnApiCallSuccessListener;
    }

    public static void setOnFullscreenAdDismissListener(OnFullscreenAdDismissListener mOnFullscreenAdDismissListener) {
        onFullscreenAdDismissListener = mOnFullscreenAdDismissListener;
    }
}
