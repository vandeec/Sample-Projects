using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using PresageLib;

public class testAds : MonoBehaviour {

    private readonly string[] _InterstitialAdUnits = { "3fa7d633bf8246f3a4ec0d92f477d3ca" };
    private readonly string[] _RewardedAdUnits = { "b0f2446ffd564322bd5830ec23a4aed1" };

	// Use this for initialization
	void Start () {

		#if UNITY_ANDROID
		    // Initializing Ogury
		    Presage.Initialize("270413");
		#endif

		// Initializing MoPub
		var sdkConfig = new MoPub.SdkConfiguration {
            AdUnitId = "3fa7d633bf8246f3a4ec0d92f477d3ca"
    	};

	    MoPub.InitializeSdk(sdkConfig);
	    MoPub.LoadInterstitialPluginsForAdUnits(_InterstitialAdUnits);
        MoPub.LoadRewardedVideoPluginsForAdUnits(_RewardedAdUnits);

	}
	


	// Update is called once per frame
	void Update () {
		
	}



	public void LoadInterstitial(){
		Debug.Log("Button load");
        MoPub.RequestInterstitialAd("3fa7d633bf8246f3a4ec0d92f477d3ca");
	}

	public void ShowInterstitial(){
		Debug.Log("Button show");
        MoPub.ShowInterstitialAd ("3fa7d633bf8246f3a4ec0d92f477d3ca");
	}

    public void LoadRewarded()
    {
        Debug.Log("Button load");
        MoPub.RequestRewardedVideo("b0f2446ffd564322bd5830ec23a4aed1");
    }

    public void ShowRewarded()
    {
        Debug.Log("Button show");
        MoPub.ShowRewardedVideo("b0f2446ffd564322bd5830ec23a4aed1");
    }
}
