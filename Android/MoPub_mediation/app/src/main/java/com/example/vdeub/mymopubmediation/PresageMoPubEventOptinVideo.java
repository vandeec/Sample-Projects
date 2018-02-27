package com.example.vdeub.mymopubmediation;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.mopub.common.LifecycleListener;
import com.mopub.common.MoPubReward;
import com.mopub.mobileads.CustomEventRewardedVideo;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubRewardedVideoManager;

import java.util.Map;

import io.presage.ads.PresageOptinVideo;
import io.presage.ads.optinvideo.RewardItem;

/**
 * Created by loicvdb on 23/11/2017.
 */

public class PresageMoPubEventOptinVideo extends CustomEventRewardedVideo {

    private String ad_unit;

    private PresageOptinVideo placement;

    private PresageOptinVideo.PresageOptinVideoCallback presageOptinVideoCallback;

    {
        presageOptinVideoCallback = new PresageOptinVideo.PresageOptinVideoCallback() {
            @Override
            public void onAdAvailable() {
            }

            @Override
            public void onAdNotAvailable() {
                MoPubRewardedVideoManager.onRewardedVideoLoadFailure(PresageMoPubEventOptinVideo.class, ad_unit, MoPubErrorCode.NETWORK_NO_FILL);
            }

            @Override
            public void onAdLoaded() {
                MoPubRewardedVideoManager.onRewardedVideoLoadSuccess(PresageMoPubEventOptinVideo.class, ad_unit);
            }

            @Override
            public void onAdDisplayed() {
                MoPubRewardedVideoManager.onRewardedVideoStarted(PresageMoPubEventOptinVideo.class, ad_unit);
            }

            @Override
            public void onAdClosed() {
                MoPubRewardedVideoManager.onRewardedVideoClosed(PresageMoPubEventOptinVideo.class, ad_unit);
            }

            @Override
            public void onAdError(int code) {
                MoPubRewardedVideoManager.onRewardedVideoLoadFailure(PresageMoPubEventOptinVideo.class, ad_unit, MoPubErrorCode.NETWORK_NO_FILL);
            }

            @Override
            public void onAdRewarded(RewardItem rewardItem) {
                MoPubReward moPubReward;
                try {
                    moPubReward = MoPubReward.success(rewardItem.getType(), Integer.valueOf(rewardItem.getAmount()));
                } catch (Exception e) {
                    moPubReward = MoPubReward.success(rewardItem.getType(), 1);
                }
                MoPubRewardedVideoManager.onRewardedVideoCompleted(PresageMoPubEventOptinVideo.class, ad_unit, moPubReward);
            }
        };
    }

    @Override
    protected boolean hasVideoAvailable() {
        return (placement != null && placement.canShow());
    }

    @Override
    protected void showVideo() {
        if (placement != null && placement.canShow()) {
            placement.show();
        } else {
            MoPubRewardedVideoManager.onRewardedVideoLoadFailure(PresageMoPubEventOptinVideo.class, ad_unit, MoPubErrorCode.NETWORK_NO_FILL);
        }
    }

    @Nullable
    @Override
    protected LifecycleListener getLifecycleListener() {
        return null; // ?
    }

    @Override
    protected boolean checkAndInitializeSdk(@NonNull Activity launcherActivity, @NonNull Map<String, Object> localExtras, @NonNull Map<String, String> serverExtras) throws Exception {
        createPlacement(launcherActivity.getApplicationContext(), serverExtras);

        return false;
    }

    @Override
    protected void loadWithSdkInitialized(@NonNull Activity activity, @NonNull Map<String, Object> localExtras, @NonNull Map<String, String> serverExtras) throws Exception {
        if (placement != null) {
            createPlacement(activity.getApplicationContext(), serverExtras);
        }

        if (placement != null) {
            placement.load();
        }
    }

    @NonNull
    @Override
    protected String getAdNetworkId() {
        return ad_unit;
    }

    @Override
    protected void onInvalidate() {
        placement = null;
        presageOptinVideoCallback = null;
    }

    private void createPlacement(Context context, Map<String, String> serverExtras) {
        if (serverExtras != null && serverExtras.size() > 1) {
            Object firstKey = serverExtras.keySet().toArray()[1];
            Object valueForFirstKey = serverExtras.get(firstKey);
            ad_unit = "" + valueForFirstKey;

            if (ad_unit != "") {
                placement = new PresageOptinVideo(context, ad_unit);
                if (placement != null) {
                    placement.setPresageOptinVideoCallback(presageOptinVideoCallback);
                }
            }
        } else {
            Log.i("PRESAGE", "createPlacement ERROR");
        }
    }

}