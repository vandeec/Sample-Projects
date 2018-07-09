using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using PresageLib;


public class testAds : MonoBehaviour {

	PresageInterstitial PresageInterstitial;
    public PresageEula presageEula;
	// Use this for initialization
	void Awake () {
		#if UNITY_ANDROID
		PresageInterstitialImpl presageInterstitialCallback = new PresageInterstitialImpl();
		// Initializing Presage
		Presage.Initialize("270413");
		PresageInterstitial = new PresageInterstitial("82022610-5a50-0135-7016-0242ac120003");
		PresageInterstitial.SetPresageInterstitialCallback(presageInterstitialCallback);
        presageEula = new PresageEula();
        presageEula.SetIEulaHandler(new PresageEulaHandler());
        presageEula.LaunchWithEula();
		#endif
	}

	public void Load(){
		#if UNITY_ANDROID
		// Making load call to try to load an Ad 
		PresageInterstitial.Load();
		#endif
	}

	public void	isLoaded(){

		#if UNITY_ANDROID
		// Making CanShow call to check ad availability
		PresageInterstitial.IsLoaded();
		#endif
	}


	public void ShowAds(){
		#if UNITY_ANDROID

		// Making Show call to show an ad.
		if (PresageInterstitial.IsLoaded())
			PresageInterstitial.Show();
		#endif
	}

}


public class PresageInterstitialImpl : PresageInterstitial.PresageInterstitialCallback {

    public void OnAdAvailable() {
        Debug.Log("interstitial available");
    }
    public void OnAdNotAvailable() {
        Debug.Log("no interstitial available");
    }
    public void OnAdLoaded() {
        Debug.Log("an interstitial is loaded, ready to be shown");
    }
    public void OnAdDisplayed() {
        Debug.Log("interstitial displayed");
    }
    public void OnAdClosed() {
        Debug.Log("interstitial closed");
    }
    public void OnAdError(int code) {
        Debug.Log("interstitial on error with code: " + code);
    }
    public void OnAdNotLoaded() {
        Debug.Log("no interstitial loaded");
    }
}

public class PresageEulaHandler : PresageEula.IEulaHandler
{
    public void OnEulaClosed()
    {
        Debug.Log("Eula closed");
    }

    public void OnEulaFound()
    {
        Debug.Log("Eula found");
    }

    public void OnEulaNotFound()
    {
        Debug.Log("Eula not found");
    }
}

