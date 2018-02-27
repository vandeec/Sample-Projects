package com.example.vdeub.myadmobmediation;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.mediation.MediationAdRequest;
import com.google.android.gms.ads.reward.mediation.MediationRewardedVideoAdAdapter;
import com.google.android.gms.ads.reward.mediation.MediationRewardedVideoAdListener;

import io.presage.ads.PresageOptinVideo;

/**
 * Created by loicvdb on 23/11/2017.
 */

public class PresageOptinVideoCustomEvent implements MediationRewardedVideoAdAdapter {

    private static final String TAG = PresageOptinVideoCustomEvent.class.getSimpleName();

    private PresageOptinVideo presageOptinVideo;
    private boolean isInitialized = false;
    private PresageOptinVideoEventForwarder presageOptinVideoEventForwarder;

    @Override
    public void initialize(Context context, MediationAdRequest mediationAdRequest, String s, MediationRewardedVideoAdListener mediationRewardedVideoAdListener, Bundle serverParameters, Bundle mediationExtras) {

        // presage ad unit id that you entered in admob
        String presageAdUnitId = serverParameters.getString(MediationRewardedVideoAdAdapter.CUSTOM_EVENT_SERVER_PARAMETER_FIELD);
        Log.d(TAG, "initialize");
        Log.d(TAG, "presageAdUnitId: " + presageAdUnitId);
        if (TextUtils.isEmpty(presageAdUnitId)) {
            mediationRewardedVideoAdListener.onInitializationFailed(PresageOptinVideoCustomEvent.this, AdRequest.ERROR_CODE_INVALID_REQUEST);
            return;
        }

        presageOptinVideoEventForwarder = new PresageOptinVideoEventForwarder(this, mediationRewardedVideoAdListener);
        presageOptinVideo = new PresageOptinVideo(context, presageAdUnitId);
        presageOptinVideo.setPresageOptinVideoCallback(presageOptinVideoEventForwarder);
        isInitialized = true;
        mediationRewardedVideoAdListener.onInitializationSucceeded(this);

    }

    @Override
    public void loadAd(MediationAdRequest mediationAdRequest, Bundle serverParameters, Bundle mediationExtras) {
        Log.d(TAG, "loadAd");
        if (presageOptinVideo != null)
            presageOptinVideo.load();
    }

    @Override
    public void showVideo() {
        Log.d(TAG, "showVideo");
        if (presageOptinVideo != null && presageOptinVideo.canShow())
            presageOptinVideo.show();
    }

    @Override
    public boolean isInitialized() {
        return isInitialized;
    }

    @Override
    public void onDestroy() {
        if (presageOptinVideo != null)
            presageOptinVideo.destroy();
        presageOptinVideo = null;
        presageOptinVideoEventForwarder = null;
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }
}
