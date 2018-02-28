## Get Started

Before using any projects, go to your account on the [Admin Ogury Website](https://admin.ogury.co) and get your api_key related to your app.

## Instructions

### Unity

* [Ogury integration with Admob Mediation](https://github.com/Ogury/Sample-Projects/tree/master/Unity/UnityAdmobMediation)
* Change your Api_key in the `AndroidManifest.xml` [here](https://github.com/Ogury/Sample-Projects/blob/master/Unity/UnityAdmobMediation/UnityAdmobMediation/Assets/Plugins/Android/AndroidManifest.xml#L34)
* Change your Admob Interstitial ad_unit in the `Ad.cs` Script [here](https://github.com/Ogury/Sample-Projects/blob/master/Unity/UnityAdmobMediation/UnityAdmobMediation/Assets/Ad.cs#L17)
* On your Admob Dashboard, add a new custom event for your Interstitial ad_unit.
	* in Class Name set `co.ogury.admob.PresageInterstitialCustomEvent`
	* in Parameter set your Interstitial ad_unit from Ogury dashboard [optional]

	![alt text](https://s3-eu-west-1.amazonaws.com/ogury-cdn/Loicvdb-Github/unity_admob_interstitial.png)

* Change your Admob Rewarded Video ad_unit in the `Ad.cs` Script [here](https://github.com/Ogury/Sample-Projects/blob/master/Unity/UnityAdmobMediation/UnityAdmobMediation/Assets/Ad.cs#L18)
* On your Admob Dashboard, add a new custom event for your Rewarded Video ad_unit.
	* in Class Name set `co.ogury.admob.PresageRewardedVideoAdapter`
	* in Parameter set your Interstitial ad_unit from Ogury dashboard [required]

	![alt text](https://s3-eu-west-1.amazonaws.com/ogury-cdn/Loicvdb-Github/unity_admob_rewarded_video.png)

## Documentation

Check out the [Ogury website](https://admin.ogury.co) for documentation on using the Ogury SDK.

## GitHub issue tracker

For any request regarding this repository or for integration issues, please contact techsupport@ogury.co.

