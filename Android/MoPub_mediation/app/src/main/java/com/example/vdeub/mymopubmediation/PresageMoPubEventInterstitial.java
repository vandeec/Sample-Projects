package com.example.vdeub.mymopubmediation;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.mopub.mobileads.CustomEventInterstitial;
import com.mopub.mobileads.MoPubErrorCode;

import java.lang.reflect.Method;
import java.util.Map;

import io.presage.ads.PresageInterstitial;

/**
 * Created by loicvdb on 23/11/2017.
 */

public class PresageMoPubEventInterstitial extends CustomEventInterstitial {

    private static String AD_UNIT_ID = "ad_unit_id";

    private CustomEventInterstitialListener mListener;

    private PresageInterstitial placement;

    public PresageMoPubEventInterstitial() {
        super();
    }

    private PresageInterstitial.PresageInterstitialCallback presageInterstitialCallback = new PresageInterstitial.PresageInterstitialCallback() {
        @Override
        public void onAdAvailable() {
        }

        @Override
        public void onAdNotAvailable() {
            if (mListener != null)
                mListener.onInterstitialFailed(MoPubErrorCode.NETWORK_NO_FILL);
        }

        @Override
        public void onAdLoaded() {
            if (mListener != null)
                mListener.onInterstitialLoaded();
        }

        @Override
        public void onAdDisplayed() {
            if (mListener != null)
                mListener.onInterstitialShown();
        }

        @Override
        public void onAdClosed() {
            if (mListener != null)
                mListener.onInterstitialDismissed();
        }

        @Override
        public void onAdError(int code) {
            if (mListener != null)
                mListener.onInterstitialFailed(MoPubErrorCode.NO_FILL);
        }
    };


    @Override
    protected void loadInterstitial(Context context,
                                    CustomEventInterstitialListener listener, Map<String, Object> localExtras,
                                    Map<String, String> serverExtras) {

        String ad_unit = "";
        mListener = listener;
        if (listener == null) {
            // log error
            return;
        }

        if (serverExtras != null && serverExtras.size() > 1) {
            Object valueForFirstKey = serverExtras.get(AD_UNIT_ID);
            ad_unit = "" + valueForFirstKey;

            if (ad_unit != "") {
                placement = new PresageInterstitial(context, ad_unit);
                if (placement != null) {
                    placement.setPresageInterstitialCallback(presageInterstitialCallback);
                    placement.load();
                    //ad_unit = null;
                }
            }
        }
        else {
            placement = new PresageInterstitial(context);
            if (placement != null) {
                placement.setPresageInterstitialCallback(presageInterstitialCallback);
                placement.load();
            }
        }
    }

    @Override
    protected void showInterstitial() {

        if (placement != null && placement.canShow()) {
            placement.show();
        }
        else {
            if (mListener != null)
                mListener.onInterstitialFailed(MoPubErrorCode.NETWORK_NO_FILL);
        }
    }

    @Override
    protected void onInvalidate() {
        placement = null;
        presageInterstitialCallback = null;
        mListener = null;
    }
}
