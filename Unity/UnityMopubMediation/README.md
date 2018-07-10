## Get Started

Before using any projects, go to your account on the [Admin Ogury Website](https://admin.ogury.co) and get your api_key related to your app.

## Instructions

#### [Ogury integration with Mopub Mediation](https://github.com/Ogury/Sample-Projects/tree/master/Unity/UnityMopubMediation)
* Change your Api_key in the `testAds.cs` [here](https://github.com/Ogury/Sample-Projects/blob/master/Unity/UnityMopubMediation/Assets/testAds.cs#L16)
* Change your Mopub Interstitial ad_unit in the `testAds.cs` Script [here](https://github.com/Ogury/Sample-Projects/blob/master/Unity/UnityMopubMediation/Assets/testAds.cs#L8), [here](https://github.com/Ogury/Sample-Projects/blob/master/Unity/UnityMopubMediation/Assets/testAds.cs#L21), [here](https://github.com/Ogury/Sample-Projects/blob/master/Unity/UnityMopubMediation/Assets/testAds.cs#L41) and [here](https://github.com/Ogury/Sample-Projects/blob/master/Unity/UnityMopubMediation/Assets/testAds.cs#L46)
* On your Mopub Dashboard, add a new custom event for your Interstitial ad_unit.
	* in Class Name set `com.mopub.PresageMoPubEventInterstitial`
	* in Parameter set your Interstitial ad_unit from Ogury dashboard [optional]

* Change your Mopub Rewarded Video ad_unit in the `testAds.cs` Script [here](https://github.com/Ogury/Sample-Projects/blob/master/Unity/UnityMopubMediation/Assets/testAds.cs#L9), [here](https://github.com/Ogury/Sample-Projects/blob/master/Unity/UnityMopubMediation/Assets/testAds.cs#L52) and [here](https://github.com/Ogury/Sample-Projects/blob/master/Unity/UnityMopubMediation/Assets/testAds.cs#L58)
* On your Mopub Dashboard, add a new custom event for your Rewarded Video ad_unit.
	* in Class Name set `com.mopub.PresageMoPubEventOptinVideo`
	* in Parameter set your Optin Video ad_unit from Ogury dashboard [required]


	![alt text](https://s3-eu-west-1.amazonaws.com/ogury-cdn/Loicvdb-Github/mopub3.png)

## Documentation

Check out the [Ogury website](https://admin.ogury.co) for documentation on using the Ogury SDK.

## GitHub issue tracker

For any request regarding this repository or for integration issues, please contact techsupport@ogury.co.

