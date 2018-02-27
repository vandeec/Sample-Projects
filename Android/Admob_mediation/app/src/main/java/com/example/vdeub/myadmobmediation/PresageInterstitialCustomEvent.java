package com.example.vdeub.myadmobmediation;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.ads.mediation.MediationAdRequest;
import com.google.android.gms.ads.mediation.customevent.CustomEventInterstitial;
import com.google.android.gms.ads.mediation.customevent.CustomEventInterstitialListener;

import java.lang.reflect.Method;

import io.presage.ads.PresageInterstitial;

/**
 * Created by loicvdb on 23/11/2017.
 */

public class PresageInterstitialCustomEvent implements CustomEventInterstitial {

    private PresageInterstitial presageInterstitial;
    private PresageInterstitialEventForwarder presageInterstitialEventForwarder;

    @Override
    public void requestInterstitialAd(Context context, CustomEventInterstitialListener customEventInterstitialListener, String serverParameter, MediationAdRequest mediationAdRequest, Bundle customEventExtras) {

        presageInterstitialEventForwarder = new PresageInterstitialEventForwarder(customEventInterstitialListener);

        if (TextUtils.isEmpty(serverParameter)) {
            presageInterstitial = new PresageInterstitial(context);
        } else
            Log.d("Ads", "ad unit param" + serverParameter);
            presageInterstitial = new PresageInterstitial(context, serverParameter);


        presageInterstitial.setPresageInterstitialCallback(presageInterstitialEventForwarder);

        presageInterstitial.load();

    }

    @Override
    public void showInterstitial() {
        if (presageInterstitial != null && presageInterstitial.canShow())
            presageInterstitial.show();
    }

    @Override
    public void onDestroy() {
        if (presageInterstitial != null)
            presageInterstitial.destroy();
        presageInterstitial = null;
        presageInterstitialEventForwarder = null;
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }
}
