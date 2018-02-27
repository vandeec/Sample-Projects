using System.Collections;
using System.Collections.Generic;
using UnityEngine;


public class testAds : MonoBehaviour {

	PresageInterstitial placement;
	// Use this for initialization
	void Awake () {
		#if UNITY_ANDROID
		// Creation of our HAndlerImpl to Handle Events
		InterstitialCallback handlerImpl = new InterstitialCallback();
		// Initializing Presage
		Presage.Initialize();
		placement = new PresageInterstitial("82022610-5a50-0135-7016-0242ac120003");
		placement.SetPresageInterstitialCallback(handlerImpl);
		#endif
	}

	public void Load(){
		#if UNITY_ANDROID
		// Making load call to try to load an Ad 
		placement.Load();
		#endif
	}

   public void	AdToServe(){
		
		#if UNITY_ANDROID
		// Making AdToServe call to try to show an Ad if available
		placement.AdToServe();
		#endif
	}

	public void	canShow(){

		#if UNITY_ANDROID
		// Making CanShow call to check ad availability
		placement.CanShow();
		#endif
	}


	public void ShowAds(){
		#if UNITY_ANDROID

		// Making Show call to show an ad.
		if (placement.CanShow())
			placement.Show();
		#endif
	}

}


public class InterstitialCallback : PresageInterstitial.PresageInterstitialCallback  {

	public void OnAdAvailable() {
		Debug.Log("ad available");
	}

	public void OnAdNotAvailable() {
		Debug.Log("no ad available");
	}

	public void OnAdLoaded() {
		Debug.Log("an ad in loaded, ready to be shown");
	}

	public void OnAdDisplayed() {
		Debug.Log("ad displayed");
	}

	public void OnAdClosed() {
		Debug.Log("ad closed");
	}

	public void OnAdError(int code) {
		Debug.Log("error with code" + code);
	}

}

