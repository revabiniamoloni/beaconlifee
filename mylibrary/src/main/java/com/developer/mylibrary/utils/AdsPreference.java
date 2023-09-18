package com.developer.mylibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class AdsPreference {

    public static SharedPreferences getPreference(Context context) {
        return context.getSharedPreferences("AdsPref", Context.MODE_PRIVATE);
    }

    public static SharedPreferences.Editor getEditor(Context context) {
        return getPreference(context).edit();
    }

    public static void putString(Context context, String key, String value) {
        getEditor(context).putString(key, value).commit();
    }

    public static String getString(Context context, String key, String defaultValue) {
        return getPreference(context).getString(key, defaultValue);
    }

    public static void putInt(Context context, String key, int value) {
        getEditor(context).putInt(key, value).commit();
    }

    public static int getInt(Context context, String key, int defaultValue) {
        return getPreference(context).getInt(key, defaultValue);
    }

    public static void putBoolean(Context context, String key, boolean value) {
        getEditor(context).putBoolean(key, value).commit();
    }

    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        return getPreference(context).getBoolean(key, defaultValue);
    }
}
