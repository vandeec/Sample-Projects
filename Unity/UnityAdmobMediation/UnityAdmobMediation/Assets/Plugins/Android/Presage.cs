using UnityEngine;
using System;
using System.Collections;
using System.Runtime.InteropServices;
using System.Collections.Generic;

public class Presage 
{
	
	private const string PRESAGE_ID = "io.presage.Presage";
	
	// Method used to Initialize Presage SDK
	public static void Initialize() 
	{
		AndroidJavaClass unityPlayer = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
		AndroidJavaObject activity = unityPlayer.GetStatic<AndroidJavaObject>("currentActivity");
		AndroidJavaObject context = activity.Call<AndroidJavaObject>("getApplicationContext");

		AndroidJavaClass presageClass = new AndroidJavaClass(PRESAGE_ID);
		AndroidJavaObject presage = presageClass.CallStatic<AndroidJavaObject> ("getInstance");
		presage.Call("setContext", context);
		presage.Call("start");
	}

	// Method used to Initialize Presage SDK with apikey
	public static void Initialize(String apiKey) 
	{
		AndroidJavaClass unityPlayer = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
		AndroidJavaObject activity = unityPlayer.GetStatic<AndroidJavaObject>("currentActivity");
		AndroidJavaObject context = activity.Call<AndroidJavaObject>("getApplicationContext");

		AndroidJavaClass presageClass = new AndroidJavaClass(PRESAGE_ID);
		AndroidJavaObject presage = presageClass.CallStatic<AndroidJavaObject> ("getInstance");
		presage.Call("setContext", context);
		presage.Call("start", apiKey);
	}
}