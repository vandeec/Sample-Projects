package com.example.vdeub.myapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import io.presage.Presage;
import io.presage.ads.PresageInterstitial;


public class MainActivity extends AppCompatActivity {

    private PresageInterstitial adUnit;

    private PresageInterstitial.PresageInterstitialCallback presageInterstitialCallback = new PresageInterstitial.PresageInterstitialCallback() {
        @Override
        public void onAdAvailable() {
            Log.i("PRESAGE", "ad available");
        }

        @Override
        public void onAdNotAvailable() {
            Log.i("PRESAGE", "no ad available");
        }

        @Override
        public void onAdLoaded() {
            Log.i("PRESAGE", "an ad in loaded, ready to be shown");
        }

        @Override
        public void onAdDisplayed() {
            Log.i("PRESAGE", "ad displayed");
        }

        @Override
        public void onAdClosed() {
            Log.i("PRESAGE", "ad closed");
        }

        @Override
        public void onAdError(int code) {
            Log.i("PRESAGE", String.format("error with code %d", code));
        }
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        WebView.setWebContentsDebuggingEnabled(true);
    }

        // Start Ogury SDK
        Presage.getInstance().setContext(this.getBaseContext());
        Presage.getInstance().start();

        adUnit = new PresageInterstitial(this);
        adUnit.setPresageInterstitialCallback(presageInterstitialCallback);

        final Button bt_load = (Button) findViewById(R.id.bt_load);
        final Button bt_canshow = (Button) findViewById(R.id.bt_canshow);
        final Button bt_show = (Button) findViewById(R.id.bt_show);
        final Button bt_adtoserve = (Button) findViewById(R.id.bt_adtoserve);


        bt_load.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                adUnit.load();
            }
        });

        bt_canshow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (adUnit.canShow()) Log.i("PRESAGE", "can show true");
                else Log.i("PRESAGE", "can show false");
            }
        });

        bt_show.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                adUnit.show();
            }
        });




        bt_adtoserve.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                adUnit.adToServe();

            }
        });
    }
}