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

import com.mopub.common.MoPub;
import com.mopub.common.MoPubReward;
import com.mopub.common.SdkConfiguration;
import com.mopub.common.SdkInitializationListener;
import com.mopub.common.logging.MoPubLog;
import com.mopub.common.privacy.ConsentDialogListener;
import com.mopub.common.privacy.PersonalInfoManager;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubRewardedVideoListener;
import com.mopub.mobileads.MoPubRewardedVideos;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class ActivityOptinVideo extends AppCompatActivity  {

    private MoPubRewardedVideoListener rewardedVideoListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        SdkConfiguration sdkConfiguration = new SdkConfiguration.Builder("882144e4b0bd422c905263e5cbb971a0")
                .build();

        MoPub.initializeSdk(this, sdkConfiguration, initSdkListener());

        rewardedVideoListener = new MoPubRewardedVideoListener() {
            @Override
            public void onRewardedVideoLoadSuccess(String adUnitId) {
                // Called when the video for the given adUnitId has loaded. At this point you should be able to call MoPubRewardedVideos.showRewardedVideo(String) to show the video.
            }
            @Override
            public void onRewardedVideoLoadFailure(String adUnitId, MoPubErrorCode errorCode) {
                // Called when a video fails to load for the given adUnitId. The provided error code will provide more insight into the reason for the failure to load.
            }

            @Override
            public void onRewardedVideoStarted(String adUnitId) {
                // Called when a rewarded video starts playing.
            }

            @Override
            public void onRewardedVideoPlaybackError(String adUnitId, MoPubErrorCode errorCode) {
                //  Called when there is an error during video playback.
            }

            @Override
            public void onRewardedVideoClicked(@NonNull String adUnitId) {

            }

            @Override
            public void onRewardedVideoClosed(String adUnitId) {
                // Called when a rewarded video is closed. At this point your application should resume.
            }

            @Override
            public void onRewardedVideoCompleted(Set<String> adUnitIds, MoPubReward reward) {
                // Called when a rewarded video is completed and the user should be rewarded.
                // You can query the reward object with boolean isSuccessful(), String getLabel(), and int getAmount().
            }
        };

        MoPubRewardedVideos.setRewardedVideoListener(rewardedVideoListener);

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

            }
        });

        bt_show.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Set your Mopub Rewarded Video Ad unit here
                MoPubRewardedVideos.showRewardedVideo("882144e4b0bd422c905263e5cbb971a0");
            }
        });
    }

    private void loadRewardedVideoAd() {
        // Set your Mopub Rewarded Video Ad unit here
        MoPubRewardedVideos.loadRewardedVideo("882144e4b0bd422c905263e5cbb971a0");
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

    @Override
    public void onPause() {
        super.onPause();
        MoPub.onPause(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        MoPub.onResume(this);
    }

    // The following methods are required for Chartboost rewarded video mediation
    @Override
    public void onStart() {
        super.onStart();
        MoPub.onStart(this);
    }

    @Override
    public void onRestart() {
        super.onRestart();
        MoPub.onRestart(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        MoPub.onStop(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MoPub.onDestroy(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MoPub.onBackPressed(this);
    }


}