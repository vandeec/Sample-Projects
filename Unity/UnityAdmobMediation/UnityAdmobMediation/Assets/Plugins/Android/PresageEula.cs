using UnityEngine;
using System;
using System.Collections;
using System.Runtime.InteropServices;
using System.Collections.Generic;

public class PresageEula
{
	private const string EULA_ID = "io.presage.IEulaHandler";

	private AndroidJavaObject currentActivity;
	private AndroidJavaObject presageEula;

	public PresageEula ()
	{
		AndroidJavaClass unityPlayer = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
		currentActivity = unityPlayer.GetStatic<AndroidJavaObject>("currentActivity");
		presageEula = new AndroidJavaObject(EULA_ID, currentActivity);
	}

	public void SetIEulaHandler(IEulaHandler iEulaHandler){
		IEulaHandlerProxy proxy = new IEulaHandlerProxy (iEulaHandler);
		presageEula.Call("setIEulaHandler", proxy);
	}

	public void LaunchWithEula() {		
		currentActivity.Call("runOnUiThread", new AndroidJavaRunnable(() =>
		                                                       {
			presageEula.Call("launchWithEula");
		}));
	}

	public class IEulaHandlerProxy : AndroidJavaProxy 
	{
		
		delegate void EulaFound();
		delegate void EulaNotFound();
		delegate void EulaClosed();
		
		EulaFound eulaFound;
		EulaNotFound eulaNotFound;
		EulaClosed eulaClosed;
		
		public IEulaHandlerProxy(IEulaHandler handler) : base(EULA_ID) 
		{
			eulaFound = handler.OnEulaFound;
			eulaNotFound = handler.OnEulaNotFound;
			eulaClosed = handler.OnEulaClosed;
		}

		void onEulaFound()
		{
			eulaFound();
		}
		void onEulaNotFound()
		{
			eulaNotFound();
		}
		void onEulaClosed()
		{
			eulaClosed();
		}
	}
	
	public interface IEulaHandler 
	{
		void OnEulaFound();
		void OnEulaNotFound();
		void OnEulaClosed();
	}


}