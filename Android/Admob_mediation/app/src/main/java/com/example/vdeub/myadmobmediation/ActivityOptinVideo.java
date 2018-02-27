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

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;


public class ActivityOptinVideo extends AppCompatActivity implements RewardedVideoAdListener {

    private RewardedVideoAd mInterstitialOptinVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        mInterstitialOptinVideo = MobileAds.getRewardedVideoAdInstance(this);
        mInterstitialOptinVideo.setRewardedVideoAdListener(this);

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        WebView.setWebContentsDebuggingEnabled(true);
    }


        final Button bt_load = (Button) findViewById(R.id.bt_load);
        final Button bt_canshow = (Button) findViewById(R.id.bt_canshow);
        final Button bt_show = (Button) findViewById(R.id.bt_show);
        final Button bt_newac = (Button) findViewById(R.id.bt_newact);

        bt_load.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                loadRewardedVideoAd();
            }
        });

        bt_newac.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        bt_canshow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("Ads", "Optin Video Ad is loaded ? " + mInterstitialOptinVideo.isLoaded());
                Toast.makeText(ActivityOptinVideo.this, "Optin Video Ad is loaded ? " + mInterstitialOptinVideo.isLoaded(), Toast.LENGTH_SHORT).show();
            }
        });

        bt_show.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (mInterstitialOptinVideo.isLoaded()) {
                    mInterstitialOptinVideo.show();
                }
            }
        });
    }

    private void loadRewardedVideoAd() {
        // Set your Admob Rewarded Video Ad unit here
        mInterstitialOptinVideo.loadAd("ca-app-pub-8953991002741575/7546286568",
                new AdRequest.Builder().build());
    }

    @Override
    public void onRewarded(RewardItem reward) {
        Toast.makeText(this, "onRewarded! currency: " + reward.getType() + "  amount: " +
                reward.getAmount(), Toast.LENGTH_SHORT).show();
        // Reward the user.
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
        Toast.makeText(this, "onRewardedVideoAdLeftApplication",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdClosed() {
        Toast.makeText(this, "onRewardedVideoAdClosed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int errorCode) {
        Toast.makeText(this, "onRewardedVideoAdFailedToLoad", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdLoaded() {
        Toast.makeText(this, "onRewardedVideoAdLoaded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdOpened() {
        Toast.makeText(this, "onRewardedVideoAdOpened", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoStarted() {
        Toast.makeText(this, "onRewardedVideoStarted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        mInterstitialOptinVideo.resume(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        mInterstitialOptinVideo.pause(this);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mInterstitialOptinVideo.destroy(this);
        super.onDestroy();
    }


}