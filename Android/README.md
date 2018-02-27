## Get Started

Before using any projects, go to your account on the [Admin Ogury Website](https://admin.ogury.co) and get your api_key related to your app.

## Instructions

### Android

#### [Ogury Basic Integration](https://github.com/Ogury/Sample-Projects/tree/master/Android/MyApplication)
* Change your Api_key in the `AndroidManifest.xml` [here](https://github.com/Ogury/Sample-Projects/tree/master/Android/MyApplication/app/src/main/AndroidManifest.xml#L25)
* Build your app

#### [Ogury integration with Admob Mediation](https://github.com/Ogury/Sample-Projects/tree/master/Android/Admob_mediation)
* Change your Api_key in the `AndroidManifest.xml` [here](https://github.com/Ogury/Sample-Projects/tree/master/Android/Admob_mediation/app/src/main/AndroidManifest.xml#L26)
* Change your Admob app_id in the `MainActivity` [here](https://github.com/Ogury/Sample-Projects/blob/master/Android/Admob_mediation/app/src/main/java/com/example/vdeub/myadmobmediation/MainActivity.java#L35)
* Change your Admob Interstitial ad_unit in the `MainActivity` [here](https://github.com/Ogury/Sample-Projects/blob/master/Android/Admob_mediation/app/src/main/java/com/example/vdeub/myadmobmediation/MainActivity.java#L38)
* On your Admob Dashboard, add a new custom event for your Interstitial ad_unit.
	* in Class Name set `com.example.vdeub.myadmobmediation.PresageInterstitialCustomEvent`
	* in Parameter set your Interstitial ad_unit from Ogury dashboard [optional]

	![alt text](https://s3-eu-west-1.amazonaws.com/ogury-cdn/Github-Loic/admob_interstitial.png)
	
* Change your Admob Rewarded Video ad_unit in the `ActivityOptinVideo` [here](https://github.com/Ogury/Sample-Projects/blob/master/Android/Admob_mediation/app/src/main/java/com/example/vdeub/myadmobmediation/ActivityOptinVideo.java#L75)
* On your Admob Dashboard, add a new custom event for your Rewarded Video ad_unit.
	* in Class Name set `com.example.vdeub.myadmobmediation.PresageOptinVideoCustomEvent`
	* in Parameter set your Interstitial ad_unit from Ogury dashboard [required]

	![alt text](https://s3-eu-west-1.amazonaws.com/ogury-cdn/Github-Loic/admob_rewarded_video.png)

#### [Ogury integration with MoPub Mediation](https://github.com/Ogury/Sample-Projects/tree/master/Android/MoPub_mediation)
* Change your Api_key in the `AndroidManifest.xml` [here](https://github.com/Ogury/Sample-Projects/tree/master/Android/MoPub_mediation/app/src/main/AndroidManifest.xml#L29)

## Documentation

Check out the [Ogury website](https://admin.ogury.co) for documentation on using the Ogury SDK.

## GitHub issue tracker

For any request regarding this repository or for integration issues, please contact techsupport@ogury.co.

