package com.example.vdeub.myapplication;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import com.smartadserver.android.library.SASInterstitialView;
import com.smartadserver.android.library.model.SASAdElement;
import com.smartadserver.android.library.ui.SASAdView;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SASInterstitialView mInterstitialView;
        final Button bt_load = (Button) findViewById(R.id.bt_load);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        /* create interstitial instance */
        mInterstitialView = new SASInterstitialView(this);


        bt_load.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // Please use your own parameters in order to display an ad
                // For more information, please visit : http://help.smartadserver.com/Android/V6.7/#refman/reference/com/smartadserver/android/library/SASInterstitialView.html

                mInterstitialView.loadAd(000000, "home", 00000, true, "", new SASAdView.AdResponseHandler() {

                    public void adLoadingCompleted(SASAdElement sasAdElement) {
                        // the Ad is fully loaded
                        // BEWARE : this method will be executed from a thread other than the Main Android thread.
                        // THEREFORE, any code that needs to be executed from the Main thread (like showing/hiding/resizing a banner
                        // MUST be wrapped in a Runnable Object which will be executed on the Main thread.
                        // This can be done using the convenience method of SASAdView: mBannerView.executeOnUIThread(runnable)
                        Log.i("PRESAGE", "ad loaded");

                    }

                    public void adLoadingFailed(Exception e) {
                        // the Ad failed to load
                        // BEWARE : this method will ALSO be executed from a thread other than the Main Android thread, so follow
                        // recommendations that apply for adLoadingCompleted method.
                        Log.d("PRESAGE", "ad loading failed " + e.toString());
                    }
                });
            }
        });
    }
}