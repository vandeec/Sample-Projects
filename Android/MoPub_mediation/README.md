## Get Started

Before using any projects, go to your account on the [Admin Ogury Website] (https://admin.ogury.co) and get your api_key related to your app.

## Instructions

### Android

#### [Ogury integration with MoPub Mediation](https://github.com/Ogury/Sample-Projects/tree/master/Android/MoPub_mediation)

* Change your Api_key in the `AndroidManifest.xml` [here](https://github.com/Ogury/Sample-Projects/tree/master/Android/MoPub_mediation/app/src/main/AndroidManifest.xml#L29)
* Change your Admob Interstitial ad_unit in the `MainActivity` [here](https://github.com/Ogury/Sample-Projects/blob/master/Android/MoPub_mediation/app/src/main/java/com/example/vdeub/mymopubmediation/MainActivity.java#L34)
* On your Mopub Dashboard, add a new custom native network for your Interstitial ad_unit.
	* in Class set `com.example.vdeub.mymopubmediation.PresageMoPubEventInterstitial`
	* in Data set your Interstitial ad_unit from Ogury dashboard [optional]

	![alt text](https://s3-eu-west-1.amazonaws.com/ogury-cdn/Loicvdb-Github/mopub_interstitial.png)
	
* Change your Admob Rewarded Video ad_unit in the `ActivityOptinVideo` [here](https://github.com/Ogury/Sample-Projects/blob/master/Android/MoPub_mediation/app/src/main/java/com/example/vdeub/mymopubmediation/ActivityOptinVideo.java#L115) and [here](https://github.com/Ogury/Sample-Projects/blob/master/Android/MoPub_mediation/app/src/main/java/com/example/vdeub/mymopubmediation/ActivityOptinVideo.java#L108)
* On your Admob Dashboard, add a new custom event for your Rewarded Video ad_unit.
	* in Class set `com.example.vdeub.mymopubmediation.PresageMoPubEventOptinVideo`
	* in Data set your Interstitial ad_unit from Ogury dashboard [required]

	![alt text](https://s3-eu-west-1.amazonaws.com/ogury-cdn/Loicvdb-Github/mopub_rewarded_video.png)

## Documentation

Check out the [Ogury website](https://admin.ogury.co) for documentation on using the Ogury SDK.

## GitHub issue tracker

For any request regarding this repository or for integration issues, please contact techsupport@ogury.co.

