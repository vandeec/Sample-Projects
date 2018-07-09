using System;
using UnityEngine; 
using GoogleMobileAds;
using GoogleMobileAds.Api;
using UnityEngine.UI;
using PresageLib;

public class Ad : MonoBehaviour {
	
	private InterstitialAd interstitial;
	private bool hasInterstitial = false;
	private bool hasRewardedVideo = false;
	private bool rewardBasedEventHandlersSet = false;
	private RewardBasedVideoAd rewardBasedVideo;
	public Text interstitialStatus;
	public Text optinVideoStatus;
	
    string adUnitId = "ca-app-pub-8953991002741575/9008443366";
    string rewardedVideoAdUnitId = "ca-app-pub-8953991002741575/3784036845";
	
	// Use this for initialization
	void Start () {
		
		Screen.orientation = ScreenOrientation.AutoRotation;
		#if UNITY_ANDROID
		// Initializing Presage
        Presage.Initialize("270413");
		#endif
		interstitialStatus = GameObject.Find("InterstitialStatus").GetComponent<Text>();
		optinVideoStatus = GameObject.Find("OptinVideoStatus").GetComponent<Text>();
	}
	
	void Update (){
		interstitialStatus.text = "Interstitial is Loaded ? " + hasInterstitial.ToString();
		optinVideoStatus.text = "OptinVideo is Loaded ? " + hasRewardedVideo.ToString();
	}
	
	// Returns an ad request with custom ad targeting.
	private AdRequest createAdRequest()
	{
		return new AdRequest.Builder()
			.AddTestDevice(AdRequest.TestDeviceSimulator)
				.AddTestDevice("0123456789ABCDEF0123456789ABCDEF") 
				.Build(); 
	}
	
	public void RequestInterstitial()
	{ 
		if (interstitial == null) 
		{
			// Create an interstitial.

			interstitial = new InterstitialAd(adUnitId);
			// Register for ad events.
			interstitial.OnAdLoaded += HandleInterstitialLoaded;
			interstitial.OnAdFailedToLoad += HandleInterstitialFailedToLoad;
			interstitial.OnAdOpening += HandleInterstitialOpened;
			interstitial.OnAdClosed += HandleInterstitialClosed;
			interstitial.OnAdLeavingApplication += HandleInterstitialLeftApplication;
			// Load an interstitial ad.
			interstitial.LoadAd(createAdRequest()); 
		} 
		else 
		{
			print ("RequestInterstitial() ignored because interstitial ad is already requested");
		}
	}
	
	public void ShowInterstitial()
	{
		if(interstitial != null)
		{
			if (interstitial.IsLoaded())
			{
				interstitial.Show();
			}
			else
			{
				print("Interstitial is not ready yet.");
			}
		}
		else 
		{
			print ("ShowInterstitial() ignored because interstitial ad is not requested");
		}
	} 
	
	private void ClearInterstitial()
	{
		if (interstitial != null) 
		{
			interstitial.OnAdLoaded -= HandleInterstitialLoaded;
			interstitial.OnAdFailedToLoad -= HandleInterstitialFailedToLoad;
			interstitial.OnAdOpening -= HandleInterstitialOpened;
			interstitial.OnAdClosed -= HandleInterstitialClosed;
			interstitial.OnAdLeavingApplication -= HandleInterstitialLeftApplication;		
			interstitial = null;
		}
	}
	
	#region Interstitial callback handlers
	public void HandleInterstitialLoaded(object sender, EventArgs args)
	{
		print("HandleInterstitialLoaded event received.");
		hasInterstitial = true;
	}
	
	public void HandleInterstitialFailedToLoad(object sender, AdFailedToLoadEventArgs args)
	{ 
		print("HandleInterstitialFailedToLoad event received with message: " + args.Message);
		ClearInterstitial();
		hasInterstitial = false;
	}
	
	public void HandleInterstitialOpened(object sender, EventArgs args)
	{
		print("HandleInterstitialOpened event received");
	}
	
	void HandleInterstitialClosing(object sender, EventArgs args)
	{
		print("HandleInterstitialClosing event received");
	}
	
	public void HandleInterstitialClosed(object sender, EventArgs args)
	{ 
		print("HandleInterstitialClosed event received");
		ClearInterstitial();
		hasInterstitial = false;
	}
	
	public void HandleInterstitialLeftApplication(object sender, EventArgs args)
	{
		print("HandleInterstitialLeftApplication event received");
	}
	
	#endregion
	
	#region RewardedVideo functions
	public void RequestRewardedVideo()
	{
		rewardBasedVideo = RewardBasedVideoAd.Instance;

		AdRequest request = new AdRequest.Builder().Build();
		rewardBasedVideo.LoadAd(request, rewardedVideoAdUnitId);
		
		if (!rewardBasedEventHandlersSet) {
			// Ad event fired when the rewarded video ad
			// has been received.
			rewardBasedVideo.OnAdLoaded += HandleRewardBasedVideoLoaded;
			// has failed to load.
			rewardBasedVideo.OnAdFailedToLoad += HandleRewardBasedVideoFailedToLoad;
			// is opened.
			rewardBasedVideo.OnAdOpening += HandleRewardBasedVideoOpened;
			// has started playing.
			rewardBasedVideo.OnAdStarted += HandleRewardBasedVideoStarted;
			// has rewarded the user.
			rewardBasedVideo.OnAdRewarded += HandleRewardBasedVideoRewarded;
			// is closed.
			rewardBasedVideo.OnAdClosed += HandleRewardBasedVideoClosed;
			// is leaving the application.
			rewardBasedVideo.OnAdLeavingApplication += HandleRewardBasedVideoLeftApplication;

			rewardBasedEventHandlersSet = true;
		}
	}
	
		public void ShowRewardedVideo()
	{
				if (rewardBasedVideo.IsLoaded())
				{
						rewardBasedVideo.Show();
				}
	}

		public void HandleRewardBasedVideoRewarded(object sender, Reward args)
		{
				string type = args.Type;
				double amount = args.Amount;
				print("User rewarded with: " + amount.ToString() + " " + type);
		}

		public void HandleRewardBasedVideoLoaded(object sender, EventArgs args)
		{
				hasRewardedVideo = true;
				print ("HandleRewardBasedVideoLoaded");
				
		}

		public void HandleRewardBasedVideoFailedToLoad(object sender, AdFailedToLoadEventArgs args)
		{
				hasRewardedVideo = false;
				print ("HandleRewardBasedVideoFailedToLoad");
		}

		public void HandleRewardBasedVideoOpened(object sender, EventArgs args)
		{
				print ("HandleRewardBasedVideoOpened");
		}

		public void HandleRewardBasedVideoStarted(object sender, EventArgs args)
		{
				print ("HandleRewardBasedVideoStarted");
		}
		public void HandleRewardBasedVideoClosed(object sender, EventArgs args)
		{
				hasRewardedVideo = false;
				print ("HandleRewardBasedVideoClosed");
		}
		public void HandleRewardBasedVideoLeftApplication(object sender, EventArgs args)
		{
				print ("HandleRewardBasedVideoLeftApplication");
		}

	#endregion
}