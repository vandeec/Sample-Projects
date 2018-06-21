package com.example.vdeub.mymopubmediation;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import io.presage.Presage;

import com.mopub.common.MoPub;
import com.mopub.common.SdkConfiguration;
import com.mopub.common.SdkInitializationListener;
import com.mopub.common.logging.MoPubLog;
import com.mopub.common.privacy.ConsentDialogListener;
import com.mopub.common.privacy.PersonalInfoManager;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubInterstitial;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements MoPubInterstitial.InterstitialAdListener {

    public boolean adclick;
    private MoPubInterstitial mInterstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        SdkConfiguration sdkConfiguration = new SdkConfiguration.Builder("1c72da79c32945de8bce05ecdbdd1527")
                .build();

        MoPub.initializeSdk(this, sdkConfiguration, initSdkListener());
        // Set your Mopub Interstitial Ad unit here
        mInterstitialAd = new MoPubInterstitial(this, "1c72da79c32945de8bce05ecdbdd1527");
        mInterstitialAd.setInterstitialAdListener(this);

        Presage.getInstance().start("270413", this);


        final Button bt_load = (Button) findViewById(R.id.bt_load);
        final Button bt_canshow = (Button) findViewById(R.id.bt_canshow);
        final Button bt_show = (Button) findViewById(R.id.bt_show);
        final Button bt_newac = (Button) findViewById(R.id.bt_newact);



        bt_load.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                mInterstitialAd.load();
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
                Log.d("Ads", "InterstialAd is loaded ? " + mInterstitialAd.isReady());
                Toast.makeText(MainActivity.this, "InterstitialAd is loaded ? " + mInterstitialAd.isReady(), Toast.LENGTH_SHORT).show();
            }
        });

        bt_show.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (mInterstitialAd.isReady()) {
                    mInterstitialAd.show();
                }
            }
        });

    }



    @Override
    public void onInterstitialLoaded(MoPubInterstitial interstitial) {
        // The interstitial has been cached and is ready to be shown.
    }

    @Override
    public void onInterstitialFailed(MoPubInterstitial interstitial, MoPubErrorCode errorCode) {
        // The interstitial has failed to load. Inspect errorCode for additional information.
        // This is an excellent place to load more ads.
        final String errorMessage = (errorCode != null) ? errorCode.toString() : "";
        Log.i( "MoPub","Interstitial failed to load: " + errorMessage);
    }

    @Override
    public void onInterstitialShown(MoPubInterstitial interstitial) {
        // The interstitial has been shown. Pause / save state accordingly.
    }

    @Override
    public void onInterstitialClicked(MoPubInterstitial interstitial) {}

    @Override
    public void onInterstitialDismissed(MoPubInterstitial interstitial) {
        // The interstitial has being dismissed. Resume / load state accordingly.
        // This is an excellent place to load more ads.
    }
    private SdkInitializationListener initSdkListener() {
        return new SdkInitializationListener() {
            @Override
            public void onInitializationFinished() {
           /* MoPub SDK initialized.
           Check if you should show the consent dialog here, and make your ad requests. */
                final PersonalInfoManager mPersonalInfoManager = MoPub.getPersonalInformationManager();
                if(mPersonalInfoManager.shouldShowConsentDialog()) {
                    mPersonalInfoManager.loadConsentDialog( new ConsentDialogListener() {

                        @Override
                        public void onConsentDialogLoaded() {
                            if (mPersonalInfoManager != null) {
                                mPersonalInfoManager.showConsentDialog();
                            }
                        }

                        @Override
                        public void onConsentDialogLoadFailed(@NonNull MoPubErrorCode moPubErrorCode) {
                            MoPubLog.i("Consent dialog failed to load.");
                        }
                    });
                }
            }
        };
    }

}