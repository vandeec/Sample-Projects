package vdeub.com.myapplicationsdk3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import io.presage.common.AdConfig;
import io.presage.common.network.models.RewardItem;
import io.presage.interstitial.optinvideo.PresageOptinVideo;
import io.presage.interstitial.optinvideo.PresageOptinVideoCallback;

public class OptinVideoActivity extends AppCompatActivity {

    private String ad_unit_id_video = "d0e6f610-52e1-0136-389f-0242ac120003";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        final Button bt_load = (Button) findViewById(R.id.bt_load);
        final Button bt_isLoaded = (Button) findViewById(R.id.bt_isLoaded);
        final Button bt_show = (Button) findViewById(R.id.bt_show);
        final Button bt_newac = (Button) findViewById(R.id.bt_newac);

        AdConfig adConfig_video = new AdConfig(ad_unit_id_video);

        final PresageOptinVideo optinVideo = new PresageOptinVideo (this, adConfig_video);

        optinVideo.setOptinVideoCallback(new PresageOptinVideoCallback() {
            @Override
            public void onAdAvailable() {
                Log.i("PRESAGE_SDK", "ad available");
            }

            @Override
            public void onAdNotAvailable() {
                Log.i("PRESAGE_SDK", "no ad available");
            }

            @Override
            public void onAdLoaded() {
                Log.i("PRESAGE_SDK", "an ad in loaded, ready to be shown");
            }
            @Override
            public void onAdNotLoaded () {
                Log.i( "PRESAGE_SDK" , "on ad not loaded" );
            }
            @Override
            public void onAdDisplayed() {
                Log.i("PRESAGE_SDK", "ad displayed");
            }

            @Override
            public void onAdClosed() {
                Log.i("PRESAGE_SDK", "ad closed");
            }

            @Override
            public void onAdError(int code) {
                Log.i( "PRESAGE_SDK" , "on ad error " + code);
        /*
        code 0: load failed
        code 1: phone not connected to internet
        code 2: ad disabled
        code 3: various error (configuration file not synced)
        code 4: ad expires in 4 hours if it was not shown
        code 5: start method not called
        */
            }

            @Override
            public void onAdRewarded(RewardItem rewardItem) {
                Log.i("PRESAGE_SDK", rewardItem.getValue());
            }
        });

        bt_load.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                optinVideo.load();
                Log.i("PRESAGE_SDK","Optin Video load");
            }
        });

        bt_isLoaded.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("PRESAGE_SDK", "Optin Video is loaded ? " + optinVideo.isLoaded());
                Toast.makeText(OptinVideoActivity.this, "Optin Video is loaded ? " + optinVideo.isLoaded(), Toast.LENGTH_SHORT).show();
            }
        });

        bt_show.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                optinVideo.show();
                Log.i("PRESAGE_SDK","Optin Video show");
            }
        });
        bt_newac.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });



    }
}