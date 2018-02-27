package com.example.vdeub.myadmobmediation;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.reward.mediation.MediationRewardedVideoAdAdapter;
import com.google.android.gms.ads.reward.mediation.MediationRewardedVideoAdListener;

import io.presage.ads.PresageOptinVideo;
import io.presage.ads.optinvideo.RewardItem;

/**
 * Created by loicvdb on 23/11/2017.
 */

public class PresageOptinVideoEventForwarder implements PresageOptinVideo.PresageOptinVideoCallback {

    private static final String TAG = PresageOptinVideoEventForwarder.class.getSimpleName();

    private MediationRewardedVideoAdListener mediationRewardedVideoAdListener;

    private MediationRewardedVideoAdAdapter mediationRewardedVideoAdAdapter;

    public PresageOptinVideoEventForwarder(MediationRewardedVideoAdAdapter mediationRewardedVideoAdAdapter, MediationRewardedVideoAdListener mediationRewardedVideoAdListener) {
        this.mediationRewardedVideoAdAdapter = mediationRewardedVideoAdAdapter;
        this.mediationRewardedVideoAdListener = mediationRewardedVideoAdListener;
    }

    @Override
    public void onAdAvailable() {

    }

    @Override
    public void onAdNotAvailable() {
        if (mediationRewardedVideoAdListener != null)
            mediationRewardedVideoAdListener.onAdFailedToLoad(mediationRewardedVideoAdAdapter, AdRequest.ERROR_CODE_NO_FILL);
    }

    @Override
    public void onAdLoaded() {
        if (mediationRewardedVideoAdListener != null)
            mediationRewardedVideoAdListener.onAdLoaded(mediationRewardedVideoAdAdapter);
    }

    @Override
    public void onAdClosed() {
        if (mediationRewardedVideoAdListener != null)
            mediationRewardedVideoAdListener.onAdClosed(mediationRewardedVideoAdAdapter);
    }

    @Override
    public void onAdError(int code) {
        if (mediationRewardedVideoAdListener != null)
            mediationRewardedVideoAdListener.onAdFailedToLoad(mediationRewardedVideoAdAdapter, AdRequest.ERROR_CODE_NO_FILL);
    }

    @Override
    public void onAdDisplayed() {
        if (mediationRewardedVideoAdListener != null)
            mediationRewardedVideoAdListener.onAdOpened(mediationRewardedVideoAdAdapter);
    }

    @Override
    public void onAdRewarded(RewardItem rewardItem) {
        if (mediationRewardedVideoAdListener != null)
            mediationRewardedVideoAdListener.onRewarded(mediationRewardedVideoAdAdapter, new RewardItemForwarder(rewardItem));
    }

    public static class RewardItemForwarder implements com.google.android.gms.ads.reward.RewardItem {

        private RewardItem presageRewardItem;

        public RewardItemForwarder(RewardItem presageRewardItem) {
            this.presageRewardItem = presageRewardItem;
        }

        @Override
        public String getType() {
            return presageRewardItem.getType();
        }

        @Override
        public int getAmount() {
            try {
                return Integer.parseInt(presageRewardItem.getAmount());
            } catch (Exception e) {
                return -1;
            }
        }
    }
}
