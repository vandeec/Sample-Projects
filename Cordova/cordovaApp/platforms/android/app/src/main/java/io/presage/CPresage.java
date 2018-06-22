package io.presage;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.apache.cordova.PluginResult.Status;
import org.json.JSONArray;
import org.json.JSONException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.presage.common.AdConfig;
import io.presage.common.SdkType;
import io.presage.common.network.models.RewardItem;
import io.presage.interstitial.PresageInterstitial;
import io.presage.interstitial.PresageInterstitialCallback;
import io.presage.interstitial.optinvideo.PresageOptinVideo;
import io.presage.interstitial.optinvideo.PresageOptinVideoCallback;

/**
 * This class is used to do Presage calls
 */
public class CPresage extends CordovaPlugin {


    private static final String LAUNCH_WITH_EULA = "launchWithEula";
    private static final String INITIALIZE_SDK = "initializeSdk";
    private static final String LOAD = "load";
    private static final String SHOW = "show";
    private static final String IS_LOADED = "isLoaded";
    private static final String SET_AD_UNIT = "setAdUnit";
    private static final String OPTIN_LOAD = "optinVideoLoad";
    private static final String OPTIN_SHOW = "optinVideoShow";
    private static final String OPTIN_IS_LOADED = "optinVideoIsLoaded";
    private static final String OPTIN_SET_AD_UNIT = "optinVideoSetAdUnit";
    private static final String SET_USERID = "SetUserId";

    private static final String PLUGIN_TYPE_KEY = "PLUGIN_TYPE";

    private PresageInterstitial presageInterstitial;
    private PresageOptinVideo presageOptinVideo;
    private PresageEula presageEula;
    private int pluginType;
    private Context context;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals(INITIALIZE_SDK)) {
            initializeSdk(args.getString(0));
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
        if (action.equals(IS_LOADED)) {
            isLoaded(callbackContext);
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
        if (action.equals(OPTIN_IS_LOADED)) {
            optinVideoIsLoaded(callbackContext);
            return true;
        }
        if (action.equals(SET_USERID)) {
            setUserId(args.getString(0));
            return true;
        }
        if (action.equals(OPTIN_SET_AD_UNIT)) {
            optinVideoSetAdUnit(args.getString(0));
            return true;
        }

        return false;
    }

	//set the userID for the rewarded video
    public void setUserId(String userId) {
        presageOptinVideo.setUserId(userId);
    }

	//set OptinVideoAdUnitID
    private void optinVideoSetAdUnit(String adUnit) {
        presageOptinVideo = new PresageOptinVideo(cordova.getActivity(), new AdConfig(adUnit));
    }

    public void optinVideoLoad(final CallbackContext callbackContext) {
        cordova.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                setPresageOptinVideoCallback(callbackContext);
                presageOptinVideo.load();
            }
        });
    }

    public void optinVideoShow(final CallbackContext callbackContext) {
        cordova.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                setPresageOptinVideoCallback(callbackContext);
                presageOptinVideo.show();
            }
        });
    }

    private void setPresageOptinVideoCallback(final CallbackContext callbackContext) {
        presageOptinVideo.setOptinVideoCallback(new PresageOptinVideoCallback() {
            @Override
            public void onAdAvailable() {
                PluginResult adAvailable = new PluginResult(PluginResult.Status.OK, "AdAvailable");
                adAvailable.setKeepCallback(true);
                callbackContext.sendPluginResult(adAvailable);
            }

            @Override
            public void onAdNotAvailable() {
                PluginResult adNotAvailable = new PluginResult(PluginResult.Status.OK, "AdNotAvailable");
                adNotAvailable.setKeepCallback(false);
                callbackContext.sendPluginResult(adNotAvailable);
            }

            @Override
            public void onAdLoaded() {
                PluginResult adLoadedResult = new PluginResult(PluginResult.Status.OK, "AdVideoLoaded");
                adLoadedResult.setKeepCallback(true);
                callbackContext.sendPluginResult(adLoadedResult);
            }

            @Override
            public void onAdNotLoaded() {
                PluginResult adNotFountResult = new PluginResult(PluginResult.Status.OK, "AdNotLoaded");
                adNotFountResult.setKeepCallback(false);
                callbackContext.sendPluginResult(adNotFountResult);
            }

            @Override
            public void onAdClosed() {
                PluginResult adClosedResult = new PluginResult(PluginResult.Status.OK, "AdClosed");
                adClosedResult.setKeepCallback(true);
                callbackContext.sendPluginResult(adClosedResult);
            }

            @Override
            public void onAdError(int code) {
                PluginResult adErrorResult = new PluginResult(PluginResult.Status.OK, "AdError");
                adErrorResult.setKeepCallback(false);
                callbackContext.sendPluginResult(adErrorResult);
            }

            @Override
            public void onAdDisplayed() {
                PluginResult adDisplayedResult = new PluginResult(PluginResult.Status.OK, "AdDisplayed");
                adDisplayedResult.setKeepCallback(true);
                callbackContext.sendPluginResult(adDisplayedResult);
            }

            @Override
            public void onAdRewarded(RewardItem rewardItem) {
                PluginResult adReward = new PluginResult(PluginResult.Status.OK, rewardItem.toString());
                adReward.setKeepCallback(true);
                callbackContext.sendPluginResult(adReward);
            }
        });
    }

	//check if the OptinVideo is loaded...if so send the plugin result else send the isVideoNotLoaded result
    public void optinVideoIsLoaded(final CallbackContext callbackContext) {
        boolean loaded = presageOptinVideo.isLoaded();
        if (loaded) {
            PluginResult adIsLoaded = new PluginResult(PluginResult.Status.OK, "isVideoLoaded");
            adIsLoaded.setKeepCallback(true);
            callbackContext.sendPluginResult(adIsLoaded);
        } else {
            PluginResult adIsLoaded = new PluginResult(PluginResult.Status.OK, "isVideoNotLoaded");
            adIsLoaded.setKeepCallback(true);
            callbackContext.sendPluginResult(adIsLoaded);
        }
    }

	//used to parse the sdktype value from the plugin configuration file. Each platform has its own value hard-coded in the configuration file
    private Map loadConfigsFromXml(Resources res, int configXmlResourceId) {
        final String PREFERENCE_TAG = "preference";
        XmlResourceParser xrp = res.getXml(configXmlResourceId);
        Map configs = new HashMap();
        try {
            xrp.next();
            while (xrp.getEventType() != XmlResourceParser.END_DOCUMENT) {
                if (PREFERENCE_TAG.equals(xrp.getName())) {
                    String key = matchSupportedKeyName(xrp.getAttributeValue(null, "name"));
                    if (key != null && key.equals(PLUGIN_TYPE_KEY)) {
                        configs.put(key, xrp.getAttributeValue(null, "value"));
                    }
                }
                xrp.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return configs;
    }

    private String matchSupportedKeyName(String testKey) {
        if (PLUGIN_TYPE_KEY.equalsIgnoreCase(testKey)) {
            return testKey;
        }
        return null;
    }

	//Initialize the data sdk which in turn will start the ads sdk
    private void initializeSdk(String apiKey) {
        Presage.getInstance().start(apiKey, context);
    }

    @Override
    public void initialize(final CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        context = cordova.getActivity().getApplicationContext();
        cordova.getActivity().runOnUiThread(new Runnable() {
            public void run() {
				//set the plugin type
				//This is called only once when the plugin in initialized
                pluginType = getPluginType(context);
                SdkType.create(context).setType(pluginType);
            }
        });
        initPresage();
    }

    private int getPluginType(Context context) {
        Map<String, String> valuesList = loadConfigsFromXml(context.getResources(), context.getResources().getIdentifier("config", "xml", context.getPackageName()));
        if (!valuesList.isEmpty()) {
            return Integer.valueOf(valuesList.get(PLUGIN_TYPE_KEY));
        }
        return 0;
    }

    private void initPresage() {
        cordova.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                presageInterstitial = new PresageInterstitial(cordova.getActivity());
                presageEula = new PresageEula(cordova.getActivity());
                presageOptinVideo = new PresageOptinVideo(cordova.getActivity(), new AdConfig("test"));
            }
        });
    }


    public void launchWithEula(final CallbackContext callbackContext) {
        cordova.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                presageEula.setIEulaHandler(new IEulaHandler() {
                    @Override
                    public void onEulaFound() {
                        PluginResult adFountResult = new PluginResult(PluginResult.Status.OK, "EulaFound");
                        adFountResult.setKeepCallback(false);
                        callbackContext.sendPluginResult(adFountResult);
                    }

                    @Override
                    public void onEulaNotFound() {
                        PluginResult adNotFountResult = new PluginResult(PluginResult.Status.OK, "EulaNotFound");
                        adNotFountResult.setKeepCallback(false);
                        callbackContext.sendPluginResult(adNotFountResult);
                    }

                    @Override
                    public void onEulaClosed() {
                        PluginResult adClosedResult = new PluginResult(PluginResult.Status.OK, "EuClosed");
                        adClosedResult.setKeepCallback(true);
                        callbackContext.sendPluginResult(adClosedResult);
                    }
                });
                presageEula.launchWithEula();
            }
        });

    }


    public void load(final CallbackContext callbackContext) {
        cordova.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                setPresageInterstitialCallback(callbackContext);
                presageInterstitial.load();
            }
        });
    }


    public void isLoaded(final CallbackContext callbackContext) {
        boolean loaded = presageInterstitial.isLoaded();
        if (loaded) {
            PluginResult adIsLoaded = new PluginResult(PluginResult.Status.OK, "isAdLoaded");
            adIsLoaded.setKeepCallback(true);
            callbackContext.sendPluginResult(adIsLoaded);
        } else {
            PluginResult adIsLoaded = new PluginResult(PluginResult.Status.OK, "isAdNotLoaded");
            adIsLoaded.setKeepCallback(true);
            callbackContext.sendPluginResult(adIsLoaded);
        }

    }

    public void show(final CallbackContext callbackContext) {
        cordova.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                setPresageInterstitialCallback(callbackContext);
                presageInterstitial.show();
            }
        });
    }

    private void setAdUnit(String adUnit) {
        presageInterstitial = new PresageInterstitial(cordova.getActivity(), new AdConfig(adUnit));
    }


    private void setPresageInterstitialCallback(final CallbackContext callbackContext) {
        presageInterstitial.setInterstitialCallback(new PresageInterstitialCallback() {
            @Override
            public void onAdAvailable() {
                PluginResult adFoundResult = new PluginResult(PluginResult.Status.OK, "AdAvailable");
                adFoundResult.setKeepCallback(true);
                callbackContext.sendPluginResult(adFoundResult);
            }

            @Override
            public void onAdNotAvailable() {
                PluginResult adNotAvailable = new PluginResult(PluginResult.Status.OK, "AdNotAvailable");
                adNotAvailable.setKeepCallback(false);
                callbackContext.sendPluginResult(adNotAvailable);
            }

            @Override
            public void onAdLoaded() {
                PluginResult adLoadedResult = new PluginResult(Status.OK, "AdLoaded");
                adLoadedResult.setKeepCallback(true);
                callbackContext.sendPluginResult(adLoadedResult);
            }

            @Override
            public void onAdNotLoaded() {
                PluginResult adNotFountResult = new PluginResult(PluginResult.Status.OK, "AdNotLoaded");
                adNotFountResult.setKeepCallback(false);
                callbackContext.sendPluginResult(adNotFountResult);
            }

            @Override
            public void onAdClosed() {
                PluginResult adClosedResult = new PluginResult(PluginResult.Status.OK, "AdClosed");
                adClosedResult.setKeepCallback(true);
                callbackContext.sendPluginResult(adClosedResult);
            }

            @Override
            public void onAdError(int code) {
                PluginResult adErrorResult = new PluginResult(PluginResult.Status.OK, "AdError");
                adErrorResult.setKeepCallback(false);
                callbackContext.sendPluginResult(adErrorResult);
            }

            @Override
            public void onAdDisplayed() {
                PluginResult adDisplayedResult = new PluginResult(PluginResult.Status.OK, "AdDisplayed");
                adDisplayedResult.setKeepCallback(true);
                callbackContext.sendPluginResult(adDisplayedResult);
            }
        });
    }
}
