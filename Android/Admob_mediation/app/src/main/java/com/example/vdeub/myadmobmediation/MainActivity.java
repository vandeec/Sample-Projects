package com.example.vdeub.myadmobmediation;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import io.presage.Presage;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.InterstitialAd;


public class MainActivity extends AppCompatActivity {

    private InterstitialAd mInterstitialAd;
    private String app_api_key = "270413";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        // Set your Admob app id
        MobileAds.initialize(this, "ca-app-pub-8953991002741575~4717844567");
        mInterstitialAd = new InterstitialAd(this);
        // Set your Admob Interstitial Ad unit here
        mInterstitialAd.setAdUnitId("ca-app-pub-8953991002741575/9008443366");

        // Start Ogury SDK
        Presage.getInstance().start(app_api_key, this);

        final Button bt_load = (Button) findViewById(R.id.bt_load);
        final Button bt_canshow = (Button) findViewById(R.id.bt_canshow);
        final Button bt_show = (Button) findViewById(R.id.bt_show);
        final Button bt_newac = (Button) findViewById(R.id.bt_newact);



        bt_load.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });

        bt_newac.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityOptinVideo.class);
                startActivity(intent);
                finish();
            }
        });


        bt_canshow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("Ads", "InterstitialAd is loaded ? " + mInterstitialAd.isLoaded());
                Toast.makeText(MainActivity.this, "InterstitialAd is loaded ? " + mInterstitialAd.isLoaded(), Toast.LENGTH_SHORT).show();
            }
        });

        bt_show.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }
            }
        });

    }



    protected void onResume () {
        super.onResume();
    }

}