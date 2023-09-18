package com.developer.mylibrary.utils;

import android.app.Activity;
import android.os.AsyncTask;

import com.developer.mylibrary.eum_class.AdsLogTag;

import java.net.HttpURLConnection;
import java.net.URL;

public class AppLiveAsyncTask extends AsyncTask<Void, Boolean, Boolean> {

    protected Activity activity;

    public AppLiveAsyncTask(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            String url = "https://play.google.com/store/apps/details?id=" + activity.getPackageName();
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(15000);
            connection.connect();
            int responseCode = connection.getResponseCode();
            AdsMasterClass.showAdTag(AdsLogTag.AdsMasterClass.name(), "App ResponseCode : " + responseCode);
            connection.disconnect();
            return responseCode == 200;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);

        if (!aBoolean) {
            AdsDefaultValueOnRemove.setDefaultValueOnRemove();
        }
    }
}
