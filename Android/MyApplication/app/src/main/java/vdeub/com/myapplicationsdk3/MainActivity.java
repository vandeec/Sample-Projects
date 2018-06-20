package vdeub.com.myapplicationsdk3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import io.presage.Presage;
import io.presage.common.AdConfig;
import io.presage.interstitial.PresageInterstitial;
import io.presage.interstitial.PresageInterstitialCallback;

public class MainActivity extends AppCompatActivity {

    private String app_api_key = "271896";
    private String ad_unit_id = "813bfa70-0ffd-0136-4e1f-0242ac120003";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button bt_load = (Button) findViewById(R.id.bt_load);
        final Button bt_isLoaded = (Button) findViewById(R.id.bt_isLoaded);
        final Button bt_show = (Button) findViewById(R.id.bt_show);
        final Button bt_newac = (Button) findViewById(R.id.bt_newac);

        Presage.getInstance().start(app_api_key, this);

        AdConfig adConfig = new AdConfig(ad_unit_id);

        final PresageInterstitial interstitial = new PresageInterstitial(this, adConfig);

        interstitial.setInterstitialCallback(new PresageInterstitialCallback() {
            @Override
            public void onAdNotLoaded () {
                Log.i( "PRESAGE_SDK" , "on ad not loaded" );
            }
            @Override
            public void onAdError (int code) {
                Log.i( "PRESAGE_SDK" , "onAdError " + code);
            }
            @Override
            public void onAdClosed () {
                Log.i( "PRESAGE_SDK" , "onAdClosed") ;
            }
            @Override
            public void onAdDisplayed () {
                Log.i( "PRESAGE_SDK" , "onAdDisplayed" );
            }
            @Override
            public void onAdLoaded () {
                Log.i( "PRESAGE_SDK" , "onAdLoaded" );
            }
            @Override
            public void onAdNotAvailable () {
                Log.i( "PRESAGE_SDK" , "onAdNotAvailable" );
            }
            @Override
            public void onAdAvailable () {
                Log.i( "PRESAGE_SDK" , "onAdAvailable" );
            }
        });

        bt_load.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                interstitial.load();
                Log.i("PRESAGE_SDK","Interstitial load");
            }
        });

        bt_isLoaded.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("Ads", "Interstitial is loaded ? " + interstitial.isLoaded());
                Toast.makeText(MainActivity.this, "Interstitial is loaded ? " +interstitial.isLoaded(), Toast.LENGTH_SHORT).show();
            }
        });

        bt_show.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                interstitial.show();
                Log.i("PRESAGE_SDK","Interstitial show");
            }
        });
        bt_newac.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OptinVideoActivity.class);
                startActivity(intent);
                finish();
            }
        });



    }
}
