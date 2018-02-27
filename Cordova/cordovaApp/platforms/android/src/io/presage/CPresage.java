package io.presage;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import io.presage.Presage;
import io.presage.ads.PresageInterstitial;
import io.presage.ads.PresageEula;
import io.presage.ads.PresageOptinVideo;
import io.presage.ads.optinvideo.RewardItem;

import android.util.Log;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;
import org.apache.cordova.PluginResult.Status;

/**
 * This class is used to do Presage calls
 */
public class CPresage extends CordovaPlugin {

    private static final String AD_TO_SERVE				= "adToServe";
    private static final String LAUNCH_WITH_EULA		= "launchWithEula";
    private static final String LOAD					= "load";
    private static final String SHOW					= "show";
	private static final String CAN_SHOW				= "canShow";
	private static final String SET_AD_UNIT				= "setAdUnit";

    private static final String OPTIN_AD_TO_SERVE		= "optinVideoAdToServe";
    private static final String OPTIN_LOAD				= "optinVideoLoad";
    private static final String OPTIN_SHOW				= "optinVideoShow";
	private static final String OPTIN_CAN_SHOW			= "optinVideoCanShow";
	private static final String OPTIN_SET_AD_UNIT		= "optinVideoSetAdUnit";
	private static final String GET_REWARD				= "GetRewardItem";
	private static final String SET_REWARD				= "SetRewardItem";
	private static final String GET_USERID				= "GetUserId";
	private static final String SET_USERID				= "SetUserId";


	private PresageInterstitial presageInterstitial;
	private PresageOptinVideo presageOptinVideo;
	private PresageEula presageEula;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals(AD_TO_SERVE)) {
            adToServe(callbackContext);
            return true;
        }
	if (action.equals(LAUNCH_WITH_EULA)) {
	    launchWithEula(callbackContext);
            return true;
	}
	if (action.equals(SET_AD_UNIT)) {
	    setAdUnit(args.getString(0));
            return true;
	}
	if (action.equals(LOAD)) {
            load(callbackContext);
            return true;
        }
	if (action.equals(SHOW)) {
            show(callbackContext);
            return true;
        }
	if (action.equals(CAN_SHOW)) {
	    canShow(callbackContext);
            return true;
		}
	if (action.equals(OPTIN_AD_TO_SERVE)) {
            optinVideoAdToServe(callbackContext);
            return true;
        }
	if (action.equals(OPTIN_SET_AD_UNIT)) {
	    optinVideoSetAdUnit(args.getString(0));
            return true;
	}
	if (action.equals(OPTIN_LOAD)) {
            optinVideoLoad(callbackContext);
            return true;
        }
	if (action.equals(OPTIN_SHOW)) {
            optinVideoShow(callbackContext);
            return true;
        }
	if (action.equals(OPTIN_CAN_SHOW)) {
	    optinVideoCanShow(callbackContext);
            return true;
		}

	if (action.equals(GET_REWARD)) {
	    getRewardItem();
            return true;
		}
	if (action.equals(SET_REWARD)) {
	    setRewardItem(args.getString(0), args.getString(1));
            return true;
		}
	if (action.equals(GET_USERID)) {
	    getUserId();
            return true;
		}
	if (action.equals(SET_USERID)) {
	    setUserId(args.getString(0));
            return true;
        }		

        return false;
    }

    @Override
    public void initialize(final CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        cordova.getActivity().runOnUiThread(new Runnable() {
            public void run() {
				Presage.getInstance().setContext(cordova.getActivity().getApplicationContext());
            }
        });
        this.start();
    }

    private void start() {
        cordova.getActivity().runOnUiThread(new Runnable() {
            public void run() {
				Presage.getInstance().start();
				presageInterstitial = new PresageInterstitial(cordova.getActivity().getApplicationContext());
				presageOptinVideo = new PresageOptinVideo(cordova.getActivity().getApplicationContext(),"1234");
				presageEula = new PresageEula(cordova.getActivity().getApplicationContext());
            }
        });
    }
	public void launchWithEula(final CallbackContext callbackContext)
    {
	cordova.getActivity().runOnUiThread(new Runnable() {
		public void run() {
		    presageEula.setIEulaHandler( new IEulaHandler() {
			    @Override
			    public void onEulaFound() {
				PluginResult adFountResult = new PluginResult(PluginResult.Status.OK, "AdFound");
				adFountResult.setKeepCallback(false);
				callbackContext.sendPluginResult(adFountResult);
			    }
			    
			    @Override
			    public void onEulaNotFound() {
				PluginResult adNotFountResult = new PluginResult(PluginResult.Status.OK, "AdNotFound");
				adNotFountResult.setKeepCallback(false);
				callbackContext.sendPluginResult(adNotFountResult);
			    }
			    
			    @Override
			    public void onEulaClosed() {
				PluginResult adClosedResult = new PluginResult(PluginResult.Status.OK, "AdClosed");
				adClosedResult.setKeepCallback(true);
				callbackContext.sendPluginResult(adClosedResult);
			    }
			});
			presageEula.launchWithEula();
		}
		});
		
    }


    public void load(final CallbackContext callbackContext)
    {
	cordova.getActivity().runOnUiThread(new Runnable() {
		public void run() {
			setPresageInterstitialCallback(callbackContext);
			presageInterstitial.load();
		}
	    });
	}
	
    public void canShow(final CallbackContext callbackContext){
	boolean r =  presageInterstitial.canShow();
	callbackContext.sendPluginResult(new PluginResult(Status.OK, r));
	//callbackContext.sendPluginResult(r);
    }

    public void show(final CallbackContext callbackContext)
    {
	cordova.getActivity().runOnUiThread(new Runnable() {
		public void run() {
			setPresageInterstitialCallback(callbackContext);
		    presageInterstitial.show();
		}
	    });
	}
	
    
    private void adToServe(final CallbackContext callbackContext) {
		setPresageInterstitialCallback(callbackContext);
		presageInterstitial.adToServe();
	}

	private void setAdUnit(String adUnit) {
		presageInterstitial = new PresageInterstitial(cordova.getActivity().getApplicationContext(), adUnit);
	}
	
	private void optinVideoSetAdUnit(String adUnit) {
		presageOptinVideo = new PresageOptinVideo(cordova.getActivity().getApplicationContext(), adUnit);
    }

    public void optinVideoLoad(final CallbackContext callbackContext)
    {
		cordova.getActivity().runOnUiThread(new Runnable() {
			public void run() {
				setPresageOptinVideoCallback(callbackContext);
				presageOptinVideo.load();
			}
			});
		}
	
		public void optinVideoCanShow(final CallbackContext callbackContext){
		boolean r =  presageOptinVideo.canShow();
		callbackContext.sendPluginResult(new PluginResult(Status.OK, r));
		//callbackContext.sendPluginResult(r);
    }

    public void optinVideoShow(final CallbackContext callbackContext)
    {
		cordova.getActivity().runOnUiThread(new Runnable() {
			public void run() {
				setPresageOptinVideoCallback(callbackContext);
				presageOptinVideo.show();
			}
	    });
    }
    
    private void optinVideoAdToServe(final CallbackContext callbackContext) {
		setPresageOptinVideoCallback(callbackContext);
		presageOptinVideo.adToServe();

	}

	public RewardItem getRewardItem() {
        return presageOptinVideo.getRewardItem();
	}
	
    public void setRewardItem(String type, String amount) {
		
        presageOptinVideo.setRewardItem(new RewardItem (type, amount));
    }

    public String getUserId() {
        return presageOptinVideo.getUserId();
    }

    public void setUserId(String userId) {
        presageOptinVideo.setUserId(userId);
	}
	
	private void setPresageOptinVideoCallback(final CallbackContext callbackContext)
	{
		presageOptinVideo.setPresageOptinVideoCallback(new PresageOptinVideo.PresageOptinVideoCallback(){
		@Override
		public void onAdAvailable() {
		    PluginResult adFountResult = new PluginResult(PluginResult.Status.OK, "AdAvailable");
		    adFountResult.setKeepCallback(false);
		    callbackContext.sendPluginResult(adFountResult);
		}
		
		@Override
		public void onAdNotAvailable() {
		    callbackContext.error("AdNotAvailable");
		}
		
		@Override
		public void onAdLoaded() {
		    PluginResult adFountResult = new PluginResult(PluginResult.Status.OK, "AdLoaded");
		    adFountResult.setKeepCallback(false);
		    callbackContext.sendPluginResult(adFountResult);
		}
		
		@Override
		public void onAdClosed() {
		    PluginResult adClosedResult = new PluginResult(PluginResult.Status.OK, "AdClosed");
		    adClosedResult.setKeepCallback(true);
		    callbackContext.sendPluginResult(adClosedResult);
		}
		
		@Override
		public void onAdError(int code) {

		}
		
		@Override
		public void onAdDisplayed() {
		    
		}

		@Override
		public void onAdRewarded(RewardItem rewardItem){

		}
		});
	}

	private void setPresageInterstitialCallback(final CallbackContext callbackContext)
	{
		presageInterstitial.setPresageInterstitialCallback(new PresageInterstitial.PresageInterstitialCallback(){
		@Override
		public void onAdAvailable() {
		    PluginResult adFountResult = new PluginResult(PluginResult.Status.OK, "AdAvailable");
		    adFountResult.setKeepCallback(false);
		    callbackContext.sendPluginResult(adFountResult);
		}
		
		@Override
		public void onAdNotAvailable() {
		    callbackContext.error("AdNotAvailable");
		}
		
		@Override
		public void onAdLoaded() {
		    PluginResult adFountResult = new PluginResult(PluginResult.Status.OK, "AdLoaded");
		    adFountResult.setKeepCallback(false);
		    callbackContext.sendPluginResult(adFountResult);
		}
		
		@Override
		public void onAdClosed() {
		    PluginResult adClosedResult = new PluginResult(PluginResult.Status.OK, "AdClosed");
		    adClosedResult.setKeepCallback(true);
		    callbackContext.sendPluginResult(adClosedResult);
		}
		
		@Override
		public void onAdError(int code) {

		}
		
		@Override
		public void onAdDisplayed() {
		    
		}
		});
	}
}
