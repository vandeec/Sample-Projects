package com.example.vdeub.myadmobmediation;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.mediation.customevent.CustomEventInterstitialListener;

import io.presage.ads.PresageInterstitial;

/**
 * Created by loicvdb on 23/11/2017.
 */

public class PresageInterstitialEventForwarder implements PresageInterstitial.PresageInterstitialCallback {
    private CustomEventInterstitialListener mInterstitialListener;

    public PresageInterstitialEventForwarder(CustomEventInterstitialListener mInterstitialListener) {
        this.mInterstitialListener = mInterstitialListener;
    }

    @Override
    public void onAdAvailable() {
    }

    @Override
    public void onAdNotAvailable() {
        if (mInterstitialListener != null)
            mInterstitialListener.onAdFailedToLoad(AdRequest.ERROR_CODE_NO_FILL);
    }

    @Override
    public void onAdLoaded() {
        if (mInterstitialListener != null)
            mInterstitialListener.onAdLoaded();
    }

    @Override
    public void onAdClosed() {
        if (mInterstitialListener != null)
            mInterstitialListener.onAdClosed();
    }

    @Override
    public void onAdError(int code) {
        if (mInterstitialListener != null)
            mInterstitialListener.onAdFailedToLoad(AdRequest.ERROR_CODE_NO_FILL);
    }

    @Override
    public void onAdDisplayed() {
        if (mInterstitialListener != null)
            mInterstitialListener.onAdOpened();
    }
}