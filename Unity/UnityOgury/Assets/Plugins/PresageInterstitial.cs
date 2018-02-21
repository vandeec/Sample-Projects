using UnityEngine;
using System;
using System.Collections;
using System.Runtime.InteropServices;
using System.Collections.Generic;

public class PresageInterstitial 
{
    private const string PRESAGE_INTERSTITIAL_ID = "io.presage.ads.PresageInterstitial";
	private const string HANDLER_ID = "io.presage.ads.PresageInterstitial$PresageInterstitialCallback";

	private AndroidJavaObject currentActivity;
	private AndroidJavaObject presageInterstitial;

	public PresageInterstitial() 
	{
		AndroidJavaClass unityPlayer = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
		currentActivity = unityPlayer.GetStatic<AndroidJavaObject>("currentActivity");
		presageInterstitial = new AndroidJavaObject(PRESAGE_INTERSTITIAL_ID, currentActivity);
	}

	public PresageInterstitial(String adUnitId)
	{
		AndroidJavaClass unityPlayer = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
		currentActivity = unityPlayer.GetStatic<AndroidJavaObject>("currentActivity");
		presageInterstitial = new AndroidJavaObject(PRESAGE_INTERSTITIAL_ID, currentActivity, adUnitId);
	}

    public void SetPresageInterstitialCallback(PresageInterstitialCallback callback)
	{
		InterstitialCallbackProxy proxy = new InterstitialCallbackProxy (callback);
		presageInterstitial.Call("setPresageInterstitialCallback", proxy);
	}


    /**
	 * Method used to Call AdToServe to show interstitial
	 */
	public void AdToServe() 
	{		
		currentActivity.Call("runOnUiThread", new AndroidJavaRunnable(() =>
		{
			presageInterstitial.Call("adToServe");
		}));
	}
	
	public void Load()
	{
		currentActivity.Call("runOnUiThread", new AndroidJavaRunnable(() =>
		{
			presageInterstitial.Call("load");
		}));
	}

	public  void Load(int adsToPrecache) 
	{	
		currentActivity.Call("runOnUiThread", new AndroidJavaRunnable(() =>
		{
			presageInterstitial.Call("load", adsToPrecache);
		}));
	}
	
	public  bool CanShow() 
	{
		return presageInterstitial.Call<bool>("canShow");
	}

	public  void Show() 
	{
		currentActivity.Call("runOnUiThread", new AndroidJavaRunnable(() =>
		{
			presageInterstitial.Call("show");
		}));
	}

    /**
	 * Proxy to interface with the IADHandler of the jar
	 */
	public class InterstitialCallbackProxy : AndroidJavaProxy 
	{	
		delegate void AdNotAvailable();
		delegate void AdAvailable();
		delegate void AdLoaded();
		delegate void AdClosed();
		
		delegate void AdError(int code);
		delegate void AdDisplayed();
		
		AdNotAvailable adNotAvailable;
		AdAvailable adAvailable;
		AdLoaded adLoaded;
		AdClosed adClosed;
		
		AdError adError;
		AdDisplayed adDisplayed;
		
		public InterstitialCallbackProxy(PresageInterstitialCallback handler) : base(HANDLER_ID) 
		{
			adNotAvailable = handler.OnAdNotAvailable;
			adAvailable = handler.OnAdAvailable;
			adLoaded = handler.OnAdLoaded;
			adClosed = handler.OnAdClosed;
			adError = handler.OnAdError;
			adDisplayed = handler.OnAdDisplayed;
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
	}
    public interface PresageInterstitialCallback 
	{
        void OnAdAvailable();

        void OnAdNotAvailable();

        void OnAdLoaded();

        void OnAdClosed();

        void OnAdError(int code);

        void OnAdDisplayed();
    }
}