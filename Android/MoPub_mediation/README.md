## Get Started

Before using any projects, go to your account on the [Admin Ogury Website](https://admin.ogury.co) and get your api_key related to your app.

## Instructions

#### [Ogury integration with MoPub Mediation](https://github.com/Ogury/Sample-Projects/tree/master/Android/MoPub_mediation)
* Change your app_api_key in the `MainActivity` [here](https://github.com/Ogury/Sample-Projects/blob/master/Android/MoPub_mediation/app/src/main/java/com/example/vdeub/mymopubmediation/MainActivity.java#L37)
* Change your MoPub Interstitial ad_unit in the `MainActivity` [here](https://github.com/Ogury/Sample-Projects/blob/master/Android/MoPub_mediation/app/src/main/java/com/example/vdeub/mymopubmediation/MainActivity.java#L34)
* On your Mopub Dashboard, add a new custom native network for your Interstitial ad_unit.
	* in Class set `com.mopub.PresageMoPubEventInterstitial`
	* in Data set your Interstitial ad_unit from Ogury dashboard [optional]
	
* Change your MoPub Rewarded Video ad_unit in the `ActivityOptinVideo` [here](https://github.com/Ogury/Sample-Projects/blob/master/Android/MoPub_mediation/app/src/main/java/com/example/vdeub/mymopubmediation/ActivityOptinVideo.java#L115) and [here](https://github.com/Ogury/Sample-Projects/blob/master/Android/MoPub_mediation/app/src/main/java/com/example/vdeub/mymopubmediation/ActivityOptinVideo.java#L108)
* On your MoPub Dashboard, add a new custom event for your Rewarded Video ad_unit.
	* in Class set `com.mopub.PresageMoPubEventOptinVideo`
	* in Data set your Rewarded. ideo ad_unit from Ogury dashboard [required]

	![alt text](https://s3-eu-west-1.amazonaws.com/ogury-cdn/Loicvdb-Github/mopub3.png)

## Documentation

Check out the [Ogury website](https://admin.ogury.co) for documentation on using the Ogury SDK.

## GitHub issue tracker

For any request regarding this repository or for integration issues, please contact techsupport@ogury.co.

