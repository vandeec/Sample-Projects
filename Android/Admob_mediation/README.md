## Get Started

Before using any projects, go to your account on the [Admin Ogury Website](https://admin.ogury.co) and get your api_key related to your app.

## Instructions

#### [Ogury integration with Admob Mediation](https://github.com/Ogury/Sample-Projects/tree/master/Android/Admob_mediation)
* Change your app_api_key in the `MainActivity` [here](https://github.com/Ogury/Sample-Projects/tree/master/Android/Admob_mediation/app/src/main/java/com/example/vdeub/myadmobmediation/MainActivity.java#L23)
* Change your Admob app_id in the `MainActivity` [here](https://github.com/Ogury/Sample-Projects/blob/master/Android/Admob_mediation/app/src/main/java/com/example/vdeub/myadmobmediation/MainActivity.java#L36)
* Change your Admob Interstitial ad_unit in the `MainActivity` [here](https://github.com/Ogury/Sample-Projects/blob/master/Android/Admob_mediation/app/src/main/java/com/example/vdeub/myadmobmediation/MainActivity.java#L39)
* On your Admob Dashboard, add a new custom event for your Interstitial ad_unit.
	* in Class Name set `com.admob.mobileads.PresageInterstitialCustomEvent`
	* in Parameter set your Interstitial ad_unit from Ogury dashboard [optional]

	![alt text](https://s3-eu-west-1.amazonaws.com/ogury-cdn/Loicvdb-Github/admob_interstitial.png)

* Change your Admob Rewarded Video ad_unit in the `ActivityOptinVideo` [here](https://github.com/Ogury/Sample-Projects/blob/master/Android/Admob_mediation/app/src/main/java/com/example/vdeub/myadmobmediation/ActivityOptinVideo.java#L75)
* On your Admob Dashboard, add a new custom event for your Rewarded Video ad_unit.
	* in Class Name set `com.admob.mobileads.PresageOptinVideoCustomEvent`
	* in Parameter set your Interstitial ad_unit from Ogury dashboard [required]

	![alt text](https://s3-eu-west-1.amazonaws.com/ogury-cdn/Loicvdb-Github/admob_rewarded_video.png)

## Documentation

Check out the [Ogury website](https://admin.ogury.co) for documentation on using the Ogury SDK.

## GitHub issue tracker

For any request regarding this repository or for integration issues, please contact techsupport@ogury.co.

