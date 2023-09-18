package com.developer.beaconlife;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.developer.mylibrary.utils.AdsShareUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdsShareUtils.shareApp(MainActivity.this, getResources().getString(R.string.app_name), "Download this app : ");
            }
        });
    }
}