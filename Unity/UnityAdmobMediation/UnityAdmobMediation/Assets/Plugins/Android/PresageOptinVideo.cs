using UnityEngine;
using System;
using System.Collections;
using System.Runtime.InteropServices;
using System.Collections.Generic;

public class PresageOptinVideo
{
	private const string PRESAGE_OPTINVIDEO_ID = "io.presage.ads.PresageOptinVideo";
    private const string HANDLER_ID = "io.presage.ads.PresageOptinVideo$PresageOptinVideoCallback";

	private AndroidJavaObject currentActivity;
	private AndroidJavaObject javaObject;
	public PresageOptinVideo (String adUnitId)
	{
		AndroidJavaClass unityPlayer = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
		currentActivity = unityPlayer.GetStatic<AndroidJavaObject>("currentActivity");
		javaObject = new AndroidJavaObject(PRESAGE_OPTINVIDEO_ID, currentActivity, adUnitId);
	}

	public PresageOptinVideo (String adUnitId, RewardItem rewardItem)
	{
		AndroidJavaClass unityPlayer = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
		currentActivity = unityPlayer.GetStatic<AndroidJavaObject>("currentActivity");
		javaObject = new AndroidJavaObject(PRESAGE_OPTINVIDEO_ID, currentActivity, adUnitId, createJavaRewardItemFromCS(rewardItem));
	}

	public PresageOptinVideo (String adUnitId, RewardItem rewardItem, String userId)
	{
		AndroidJavaClass unityPlayer = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
		currentActivity = unityPlayer.GetStatic<AndroidJavaObject>("currentActivity");
		javaObject = new AndroidJavaObject(PRESAGE_OPTINVIDEO_ID, currentActivity, adUnitId, createJavaRewardItemFromCS(rewardItem), userId);
	}

	public void SetRewardItem(RewardItem rewardItem)
	{
		javaObject.Call("setRewardItem", createJavaRewardItemFromCS(rewardItem));
	}
	public RewardItem GetRewardItem() 
	{
		return createRewardItemFromJava(javaObject.Call<AndroidJavaObject>("getRewardItem"));
    }

	private RewardItem createRewardItemFromJava(AndroidJavaObject javaObject)
	{
		return new RewardItem(javaObject.Call<String>("getType"),javaObject.Call<String>("getAmount"));
	}

	private AndroidJavaObject createJavaRewardItemFromCS(RewardItem rewardItem)
	{
		return new AndroidJavaObject(RewardItem.REWARD_ITEM_ID, rewardItem.GetType(), rewardItem.GetAmount());
	}

	public void SetPresageOptinVideoCallback(PresageOptinVideoCallback callback)
	{
		OptinVideoCallbackProxy proxy = new OptinVideoCallbackProxy (callback);
		javaObject.Call("setPresageOptinVideoCallback", proxy);
	}

	public void AdToServe() 
	{		
		currentActivity.Call("runOnUiThread", new AndroidJavaRunnable(() =>
		{
			javaObject.Call("adToServe");
		}));
	}
	
	public void Load()
	{
		currentActivity.Call("runOnUiThread", new AndroidJavaRunnable(() =>
		{
			javaObject.Call("load");
		}));
	}

	public  void Load(int adsToPrecache) 
	{	
		currentActivity.Call("runOnUiThread", new AndroidJavaRunnable(() =>
		{
			javaObject.Call("load", adsToPrecache);
		}));
	}
	
	public  bool CanShow() 
	{
		return javaObject.Call<bool>("canShow");
	}

	public  void Show() 
	{
		currentActivity.Call("runOnUiThread", new AndroidJavaRunnable(() =>
		{
			javaObject.Call("show");
		}));
	}

	public class OptinVideoCallbackProxy : AndroidJavaProxy 
	{	
		delegate void AdNotAvailable();
		delegate void AdAvailable();
		delegate void AdLoaded();
		delegate void AdClosed();
		
		delegate void AdError(int code);
		delegate void AdDisplayed();
		delegate void AdRewarded(RewardItem rewardItem);
		
		AdNotAvailable adNotAvailable;
		AdAvailable adAvailable;
		AdLoaded adLoaded;
		AdClosed adClosed;
		
		AdError adError;
		AdDisplayed adDisplayed;
		AdRewarded adRewarded;
		
		public OptinVideoCallbackProxy(PresageOptinVideoCallback handler) : base(HANDLER_ID) 
		{
			adNotAvailable = handler.OnAdNotAvailable;
			adAvailable = handler.OnAdAvailable;
			adLoaded = handler.OnAdLoaded;
			adClosed = handler.OnAdClosed;
			adError = handler.OnAdError;
			adDisplayed = handler.OnAdDisplayed;
			adRewarded = handler.OnAdRewarded;
		}
		
		void onAdNotAvailable() 
		{
			// Ad wasn't Available
			adNotAvailable();
		}
		
		void onAdAvailable() 
		{
			// Ad was Available
			adAvailable();
		}

		void onAdLoaded() 
		{
			// ads was loaded
			adLoaded();
		}
		
		void onAdClosed() 
		{
			// Ad was closed
			adClosed();
		}
		
		void onAdError(int code) 
		{
			// Ad sent an error
			adError(code);
		}
		
		void onAdDisplayed() 
		{
			// Ad was displayed
			adDisplayed();
		}

		void onAdRewarded(AndroidJavaObject rewardItemJava)
		{
			adRewarded(new RewardItem(rewardItemJava.Call<String>("getType"),rewardItemJava.Call<String>("getAmount")));
		}
	}
    public interface PresageOptinVideoCallback 
	{
        void OnAdAvailable();

        void OnAdNotAvailable();

        void OnAdLoaded();

        void OnAdClosed();

        void OnAdError(int code);

        void OnAdDisplayed();

		void OnAdRewarded(PresageOptinVideo.RewardItem rewardItem);
    }

	public class RewardItem
	{
		public const string REWARD_ITEM_ID = "io.presage.ads.optinvideo.RewardItem";

		private AndroidJavaObject javaObject;
				
		public RewardItem(String rewardType, String amount)
		{
			javaObject = new AndroidJavaObject(REWARD_ITEM_ID, rewardType, amount);
		}

		public String GetRewardType()
		{
			return javaObject.Call<String>("getType");
		}

		public String GetAmount()
		{
			return javaObject.Call<String>("getAmount");
		}
	}
}

