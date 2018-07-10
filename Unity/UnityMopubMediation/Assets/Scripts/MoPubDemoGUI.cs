﻿using System.Collections.Generic;
using System.Diagnostics.CodeAnalysis;
using UnityEngine;
#if mopub_native_beta

// This feature is still in Beta! If you're interested in our Beta Program, please contact support@mopub.com
using NativeAdData = AbstractNativeAd.Data;

#endif

[SuppressMessage("ReSharper", "AccessToStaticMemberViaDerivedType")]
public class MoPubDemoGUI : MonoBehaviour
{
    // State maps to enable/disable GUI ad state buttons
    private readonly Dictionary<string, bool> _adUnitToLoadedMapping = new Dictionary<string, bool>();

    private readonly Dictionary<string, bool> _adUnitToShownMapping = new Dictionary<string, bool>();

    private readonly Dictionary<string, List<MoPub.Reward>> _adUnitToRewardsMapping =
        new Dictionary<string, List<MoPub.Reward>>();

    private bool _consentDialogLoaded;

#if UNITY_IOS
    private readonly string[] _bannerAdUnits =
        { "0ac59b0996d947309c33f59d6676399f", "23b49916add211e281c11231392559e4" };

    private readonly string[] _interstitialAdUnits =
        { "4f117153f5c24fa6a3a92b818a5eb630", "3aba0056add211e281c11231392559e4" };

    private readonly string[] _rewardedVideoAdUnits =
        { "8f000bd5e00246de9c789eed39ff6096", "98c29e015e7346bd9c380b1467b33850" };

    private readonly string[] _rewardedRichMediaAdUnits = { };
#elif UNITY_ANDROID || UNITY_EDITOR
    private readonly string[] _bannerAdUnits = { "b195f8dd8ded45fe847ad89ed1d016da" };
    private readonly string[] _interstitialAdUnits = { "24534e1901884e398f1253216226017e" };
    private readonly string[] _rewardedVideoAdUnits = { "920b6145fb1546cf8b5cf2ac34638bb7" };
    private readonly string[] _rewardedRichMediaAdUnits = { "15173ac6d3e54c9389b9a5ddca69b34b" };
#endif

#if mopub_native_beta
    private Dictionary<string, MoPubStaticNativeAd> _nativeAds;

    private readonly Dictionary<string, NativeAdData> _adUnitToNativeAdDataMapping =
        new Dictionary<string, NativeAdData>();

    private readonly string[] _nativeAdUnits = {
#if UNITY_EDITOR
        "1"
#elif UNITY_ANDROID
        "11a17b188668469fb0412708c3d16813",
        "09d4e470ca534795b71041c6ca79bac4",
        "05eac26a22974c7f8005878840d4ca5e"
#elif UNITY_IOS

#endif
    };

#if mopub_mediation
    private Dictionary<string, FacebookNativeAd> _fbNativeAds;
    private readonly string[] _fbNativeAdUnits = { "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID" };
#endif
#endif// mopub_native_beta

    [SerializeField]
    private GUISkin _skin;

    // Label style for no ad unit messages
    private GUIStyle _smallerFont;

    // Buffer space between sections
    private int _sectionMarginSize;

    // Label style for plugin and SDK version banner
    private GUIStyle _centeredStyle;

    // Default text for custom data fields
    private static string _customDataDefaultText = "Optional custom data";

    // String to fill with custom data for Rewarded Videos
    private string _rvCustomData = _customDataDefaultText;

    // String to fill with custom data for Rewarded Rich Media
    private string _rrmCustomData = _customDataDefaultText;

    // Flag indicating that personally identifiable information can be collected
    private bool _canCollectPersonalInfo = false;

    // Current consent status of this user to collect personally identifiable information
    private MoPub.Consent.Status _currentConsentStatus = MoPub.Consent.Status.Unknown;

    // Flag indicating that consent should be acquired to collect personally identifiable information
    private bool _shouldShowConsentDialog = false;

    // Flag indicating that the General Data Protection Regulation (GDPR) applies to this user
    private bool? _isGdprApplicable = false;

    // Flag indicating that the General Data Protection Regulation (GDPR) has been forcibly applied by the publisher
    private bool _isGdprForced = false;

    // Status string for tracking current state
    private string _status = string.Empty;


    private static bool IsAdUnitArrayNullOrEmpty(ICollection<string> adUnitArray)
    {
        return (adUnitArray == null || adUnitArray.Count == 0);
    }


    private void AddAdUnitsToStateMaps(IEnumerable<string> adUnits)
    {
        foreach (var adUnit in adUnits) {
            _adUnitToLoadedMapping.Add(adUnit, false);
            _adUnitToShownMapping.Add(adUnit, false);
        }
    }


    public void SdkInitialized()
    {
        UpdateConsentValues();
    }


    public void UpdateStatusLabel(string message)
    {
        _status = message;
    }


    public void ClearStatusLabel()
    {
        UpdateStatusLabel(string.Empty);
    }


    public void ConsentStatusChanged(MoPub.Consent.Status newStatus, bool canCollectPersonalInfo)
    {
        _canCollectPersonalInfo = canCollectPersonalInfo;
        _currentConsentStatus = newStatus;
        _shouldShowConsentDialog = MoPub.ShouldShowConsentDialog;

        UpdateStatusLabel("Consent status changed");
    }

    public void LoadAvailableRewards(string adUnitId, List<MoPub.Reward> availableRewards)
    {
        // Remove any existing available rewards associated with this AdUnit from previous ad requests
        _adUnitToRewardsMapping.Remove(adUnitId);

        if (availableRewards != null) {
            _adUnitToRewardsMapping[adUnitId] = availableRewards;
        }
    }


    public void BannerLoaded(string adUnitId, float height)
    {
        AdLoaded(adUnitId);
        _adUnitToShownMapping[adUnitId] = true;
    }


    public void AdLoaded(string adUnit)
    {
        _adUnitToLoadedMapping[adUnit] = true;
        UpdateStatusLabel("Loaded " + adUnit);
    }


    public void AdDismissed(string adUnit)
    {
        _adUnitToLoadedMapping[adUnit] = false;
        ClearStatusLabel();
    }


#if mopub_native_beta
    public void NativeAdLoaded(string adUnitId, NativeAdData nativeAdData)
    {
        if (!_adUnitToNativeAdDataMapping.ContainsKey(adUnitId)) {
            _adUnitToNativeAdDataMapping.Add(adUnitId, nativeAdData);
        }

        AdLoaded(adUnitId);
    }
#endif


    public bool ConsentDialogLoaded {
        private get { return _consentDialogLoaded; }
        set {
            _consentDialogLoaded = value;
            if (_consentDialogLoaded) UpdateStatusLabel("Consent dialog loaded");
        }
    }


    private void Awake()
    {
        if (Screen.width < 960 && Screen.height < 960) {
            _skin.button.fixedHeight = 50;
        }

        _smallerFont = new GUIStyle(_skin.label) { fontSize = _skin.button.fontSize };
        _centeredStyle = new GUIStyle(_skin.label) { alignment = TextAnchor.UpperCenter };

        // Buffer space between sections
        _sectionMarginSize = _skin.label.fontSize;

        AddAdUnitsToStateMaps(_bannerAdUnits);
        AddAdUnitsToStateMaps(_interstitialAdUnits);
        AddAdUnitsToStateMaps(_rewardedVideoAdUnits);
        AddAdUnitsToStateMaps(_rewardedRichMediaAdUnits);
#if mopub_native_beta
        AddAdUnitsToStateMaps(_nativeAdUnits);
#if mopub_mediation
        AddAdUnitsToStateMaps(_fbNativeAdUnits);
#endif
#endif
        ConsentDialogLoaded = false;
    }


    private void Start()
    {
        // NOTE: the MoPub SDK needs to be initialized on Start() to ensure all other objects have been enabled first.
        var anyAdUnitId = _bannerAdUnits[0];
        MoPub.InitializeSdk(anyAdUnitId);

        MoPub.LoadBannerPluginsForAdUnits(_bannerAdUnits);
        MoPub.LoadInterstitialPluginsForAdUnits(_interstitialAdUnits);
        MoPub.LoadRewardedVideoPluginsForAdUnits(_rewardedVideoAdUnits);
        MoPub.LoadRewardedVideoPluginsForAdUnits(_rewardedRichMediaAdUnits);
#if mopub_native_beta
        MoPub.LoadNativePluginsForAdUnits(_nativeAdUnits);
#endif

#if !(UNITY_ANDROID || UNITY_IOS)
        Debug.LogError("Please switch to either Android or iOS platforms to run sample app!");
#endif

#if UNITY_EDITOR
        Debug.LogWarning("No SDK was loaded since this is not on a mobile device! Real ads will not load.");
#endif

#if mopub_native_beta
        _nativeAds = new Dictionary<string, MoPubStaticNativeAd>();
        var staticNativeAds = GameObject.Find("MoPubNativeAds").GetComponentsInChildren<MoPubStaticNativeAd>();
        Debug.Log("Found " + staticNativeAds.Length + " mopub static native ads");
        foreach (var nativeAd in staticNativeAds) {
            _nativeAds.Add(nativeAd.AdUnitId, nativeAd);
            HideNativeAd(nativeAd);
        }

#if mopub_mediation
        _fbNativeAds = new Dictionary<string, FacebookNativeAd>();
        var fbStaticNativeAds = GameObject.Find("MoPubNativeAds").GetComponentsInChildren<FacebookNativeAd>();
        Debug.Log("Found " + fbStaticNativeAds.Length + " facebook native ads");
        foreach (var fbNativeAd in fbStaticNativeAds) {
            _fbNativeAds.Add(fbNativeAd.AdUnitId, fbNativeAd);
            HideNativeAd(fbNativeAd);
        }
#endif
#else
        var nativeAdsGO = GameObject.Find("MoPubNativeAds");
        if (nativeAdsGO != null)
            nativeAdsGO.SetActive(false);
#endif
    }


#if mopub_native_beta
    private void HideNativeAd(AbstractNativeAd nativeAd)
    {
        nativeAd.GetComponent<Rigidbody>().useGravity = false;
        nativeAd.Hide();
        _adUnitToShownMapping[nativeAd.AdUnitId] = false;
    }


    private void ShowNativeAd(AbstractNativeAd nativeAd)
    {
        nativeAd.transform.localPosition = new Vector3(0, 700, 115);
        nativeAd.transform.rotation = Quaternion.Euler(Vector3.up);
        nativeAd.transform.Rotate(new Vector3(Random.Range(15, 45), Random.Range(-10, 10), Random.Range(-10, 10)));
        nativeAd.GetComponent<Rigidbody>().useGravity = true;
        nativeAd.Show();
        _adUnitToShownMapping[nativeAd.AdUnitId] = true;

        MoPubManager.Instance.EmitNativeImpressionEvent(nativeAd.AdUnitId);
    }
#endif


    private void OnGUI()
    {
        GUI.skin = _skin;

#if UNITY_2017_3_OR_NEWER
        // Screen.safeArea was added in Unity 2017.2.0p1
        var guiArea = Screen.safeArea;
#else
        var guiArea = new Rect(0, 0, Screen.width, Screen.height);
#endif
        guiArea.x += 20;
        guiArea.width -= 40;
        GUILayout.BeginArea(guiArea);

        CreateTitleSection();
        CreateBannersSection();
        CreateInterstitialsSection();
        CreateRewardedVideosSection();
        CreateRewardedRichMediaSection();
#if mopub_native_beta
        CreateNativeSection();
#endif
        CreateUserConsentSection();
        CreateActionsSection();
        CreateStatusSection();

        GUILayout.EndArea();
    }


    private void CreateTitleSection()
    {
        // App title including Plugin and SDK versions
        var prevFontSize = _centeredStyle.fontSize;
        _centeredStyle.fontSize = 48;
        GUI.Label(new Rect(0, 10, Screen.width, 60), MoPub.PluginName, _centeredStyle);
        _centeredStyle.fontSize = prevFontSize;
        GUI.Label(new Rect(0, 70, Screen.width, 60), "with " + MoPub.SdkName, _centeredStyle);
    }


    private void CreateBannersSection()
    {
        const int titlePadding = 102;
        GUILayout.Space(titlePadding);
        GUILayout.Label("Banners");
        if (!IsAdUnitArrayNullOrEmpty(_bannerAdUnits)) {
            foreach (var bannerAdUnit in _bannerAdUnits) {
                GUILayout.BeginHorizontal();

                GUI.enabled = !_adUnitToLoadedMapping[bannerAdUnit];
                if (GUILayout.Button(CreateRequestButtonLabel(bannerAdUnit))) {
                    Debug.Log("requesting banner with AdUnit: " + bannerAdUnit);
                    UpdateStatusLabel("Requesting " + bannerAdUnit);
                    MoPub.CreateBanner(bannerAdUnit, MoPub.AdPosition.BottomCenter);
                }

                GUI.enabled = _adUnitToLoadedMapping[bannerAdUnit];
                if (GUILayout.Button("Destroy")) {
                    ClearStatusLabel();
                    MoPub.DestroyBanner(bannerAdUnit);
                    _adUnitToLoadedMapping[bannerAdUnit] = false;
                    _adUnitToShownMapping[bannerAdUnit] = false;
                }

                GUI.enabled = _adUnitToLoadedMapping[bannerAdUnit] && !_adUnitToShownMapping[bannerAdUnit];
                if (GUILayout.Button("Show")) {
                    ClearStatusLabel();
                    MoPub.ShowBanner(bannerAdUnit, true);
                    _adUnitToShownMapping[bannerAdUnit] = true;
                }

                GUI.enabled = _adUnitToLoadedMapping[bannerAdUnit] && _adUnitToShownMapping[bannerAdUnit];
                if (GUILayout.Button("Hide")) {
                    ClearStatusLabel();
                    MoPub.ShowBanner(bannerAdUnit, false);
                    _adUnitToShownMapping[bannerAdUnit] = false;
                }

                GUI.enabled = true;

                GUILayout.EndHorizontal();
            }
        } else {
            GUILayout.Label("No banner AdUnits available", _smallerFont, null);
        }
    }


    private void CreateInterstitialsSection()
    {
        GUILayout.Space(_sectionMarginSize);
        GUILayout.Label("Interstitials");
        if (!IsAdUnitArrayNullOrEmpty(_interstitialAdUnits)) {
            foreach (var interstitialAdUnit in _interstitialAdUnits) {
                GUILayout.BeginHorizontal();

                GUI.enabled = !_adUnitToLoadedMapping[interstitialAdUnit];
                if (GUILayout.Button(CreateRequestButtonLabel(interstitialAdUnit))) {
                    Debug.Log("requesting interstitial with AdUnit: " + interstitialAdUnit);
                    UpdateStatusLabel("Requesting " + interstitialAdUnit);
                    MoPub.RequestInterstitialAd(interstitialAdUnit);
                }

                GUI.enabled = _adUnitToLoadedMapping[interstitialAdUnit];
                if (GUILayout.Button("Show")) {
                    ClearStatusLabel();
                    MoPub.ShowInterstitialAd(interstitialAdUnit);
                }

                GUI.enabled = true;
                GUILayout.EndHorizontal();
            }
        } else {
            GUILayout.Label("No interstitial AdUnits available", _smallerFont, null);
        }
    }


    private void CreateRewardedVideosSection()
    {
        GUILayout.Space(_sectionMarginSize);
        GUILayout.Label("Rewarded Videos");
        if (!IsAdUnitArrayNullOrEmpty(_rewardedVideoAdUnits)) {
            CreateCustomDataField("rvCustomDataField", ref _rvCustomData);
            foreach (var rewardedVideoAdUnit in _rewardedVideoAdUnits) {
                GUILayout.BeginHorizontal();

                GUI.enabled = !_adUnitToLoadedMapping[rewardedVideoAdUnit];
                if (GUILayout.Button(CreateRequestButtonLabel(rewardedVideoAdUnit))) {
                    Debug.Log("requesting rewarded video with AdUnit: " + rewardedVideoAdUnit);
                    UpdateStatusLabel("Requesting " + rewardedVideoAdUnit);
                    MoPub.RequestRewardedVideo(
                        adUnitId: rewardedVideoAdUnit, keywords: "rewarded, video, mopub",
                        latitude: 37.7833, longitude: 122.4167, customerId: "customer101");
                }

                GUI.enabled = _adUnitToLoadedMapping[rewardedVideoAdUnit];
                if (GUILayout.Button("Show")) {
                    ClearStatusLabel();
                    MoPub.ShowRewardedVideo(rewardedVideoAdUnit, GetCustomData(_rvCustomData));
                }

                GUI.enabled = true;

                GUILayout.EndHorizontal();


                // Display rewards if there's a rewarded video loaded and there are multiple rewards available
                if (!MoPub.HasRewardedVideo(rewardedVideoAdUnit)
                    || !_adUnitToRewardsMapping.ContainsKey(rewardedVideoAdUnit)
                    || _adUnitToRewardsMapping[rewardedVideoAdUnit].Count <= 1) continue;

                GUILayout.BeginVertical();
                GUILayout.Space(_sectionMarginSize);
                GUILayout.Label("Select a reward:");

                foreach (var reward in _adUnitToRewardsMapping[rewardedVideoAdUnit]) {
                    if (GUILayout.Button(reward.ToString())) {
                        MoPub.SelectReward(rewardedVideoAdUnit, reward);
                    }
                }

                GUILayout.Space(_sectionMarginSize);
                GUILayout.EndVertical();
            }
        } else {
            GUILayout.Label("No rewarded video AdUnits available", _smallerFont, null);
        }
    }


    private void CreateRewardedRichMediaSection()
    {
        GUILayout.Space(_sectionMarginSize);
        GUILayout.Label("Rewarded Rich Media");
        if (!IsAdUnitArrayNullOrEmpty(_rewardedRichMediaAdUnits)) {
            CreateCustomDataField("rrmCustomDataField", ref _rrmCustomData);
            foreach (var rewardedRichMediaAdUnit in _rewardedRichMediaAdUnits) {
                GUILayout.BeginHorizontal();

                GUI.enabled = !_adUnitToLoadedMapping[rewardedRichMediaAdUnit];
                if (GUILayout.Button(CreateRequestButtonLabel(rewardedRichMediaAdUnit))) {
                    Debug.Log("requesting rewarded rich media with AdUnit: " + rewardedRichMediaAdUnit);
                    UpdateStatusLabel("Requesting " + rewardedRichMediaAdUnit);
                    MoPub.RequestRewardedVideo(
                        adUnitId: rewardedRichMediaAdUnit, keywords: "rewarded, video, mopub",
                        latitude: 37.7833, longitude: 122.4167, customerId: "customer101");
                }

                GUI.enabled = _adUnitToLoadedMapping[rewardedRichMediaAdUnit];
                if (GUILayout.Button("Show")) {
                    ClearStatusLabel();
                    MoPub.ShowRewardedVideo(rewardedRichMediaAdUnit, GetCustomData(_rrmCustomData));
                }

                GUI.enabled = true;

                GUILayout.EndHorizontal();

                // Display rewards if there's a rewarded rich media ad loaded and there are multiple rewards available
                if (!MoPub.HasRewardedVideo(rewardedRichMediaAdUnit)
                    || !_adUnitToRewardsMapping.ContainsKey(rewardedRichMediaAdUnit)
                    || _adUnitToRewardsMapping[rewardedRichMediaAdUnit].Count <= 1) continue;

                GUILayout.BeginVertical();
                GUILayout.Space(_sectionMarginSize);
                GUILayout.Label("Select a reward:");

                foreach (var reward in _adUnitToRewardsMapping[rewardedRichMediaAdUnit]) {
                    if (GUILayout.Button(reward.ToString())) {
                        MoPub.SelectReward(rewardedRichMediaAdUnit, reward);
                    }
                }

                GUILayout.Space(_sectionMarginSize);
                GUILayout.EndVertical();
            }
        } else {
            GUILayout.Label("No rewarded rich media AdUnits available", _smallerFont, null);
        }
    }


#if mopub_native_beta
    private void CreateNativeSection()
    {
        GUILayout.Space(_sectionMarginSize);
        GUILayout.Label("Native Ads");
        if (!IsAdUnitArrayNullOrEmpty(_nativeAdUnits)) {
            foreach (var nativeAdUnit in _nativeAdUnits) {
                GUILayout.BeginHorizontal();

                var nativeAd = _nativeAds[nativeAdUnit];

                GUI.enabled = !_adUnitToLoadedMapping[nativeAdUnit];
                if (GUILayout.Button(CreateRequestButtonLabel(nativeAdUnit))) {
                    Debug.Log("requesting native AdUnit: " + nativeAdUnit);
                    UpdateStatusLabel("Requesting " + nativeAdUnit);
                    nativeAd.LoadAd();
                }

                GUI.enabled = _adUnitToLoadedMapping[nativeAdUnit] && !_adUnitToShownMapping[nativeAdUnit];
                if (GUILayout.Button("Show")) {
                    ClearStatusLabel();
                    ShowNativeAd(nativeAd);
                }

                GUI.enabled = _adUnitToLoadedMapping[nativeAdUnit] && _adUnitToShownMapping[nativeAdUnit];
                if (GUILayout.Button("Hide")) {
                    ClearStatusLabel();
                    HideNativeAd(nativeAd);
                }

                GUI.enabled = true;

                GUILayout.EndHorizontal();
            }
        } else {
            GUILayout.Label("No native AdUnits available", _smallerFont, null);
        }

#if mopub_mediation
        if (!IsAdUnitArrayNullOrEmpty(_fbNativeAdUnits)) {
            foreach (var fbNativeAdUnit in _fbNativeAdUnits) {
                GUILayout.BeginHorizontal();

                var fbNativeAd = _fbNativeAds[fbNativeAdUnit];

                GUI.enabled = !_adUnitToLoadedMapping[fbNativeAdUnit];
                if (GUILayout.Button(CreateRequestButtonLabel(fbNativeAdUnit))) {
                    Debug.Log("requesting native AdUnit: " + fbNativeAdUnit);
                    UpdateStatusLabel("Requesting " + fbNativeAdUnit);
                    fbNativeAd.LoadAd();
                }

                GUI.enabled = _adUnitToLoadedMapping[fbNativeAdUnit] && !_adUnitToShownMapping[fbNativeAdUnit];
                if (GUILayout.Button("Show")) {
                    ClearStatusLabel();
                    ShowNativeAd(fbNativeAd);
                }

                GUI.enabled = _adUnitToLoadedMapping[fbNativeAdUnit] && _adUnitToShownMapping[fbNativeAdUnit];
                if (GUILayout.Button("Hide")) {
                    ClearStatusLabel();
                    HideNativeAd(fbNativeAd);
                }

                GUI.enabled = true;

                GUILayout.EndHorizontal();
            }
        } else {
            GUILayout.Label("No Facebook Audience Network native AdUnits available", _smallerFont, null);
        }
#endif
    }
#endif


    private void CreateUserConsentSection()
    {
        GUILayout.Space(_sectionMarginSize);
        GUILayout.Label("User Consent");
        GUILayout.Label("Can collect personally identifiable information: " + _canCollectPersonalInfo, _smallerFont);
        GUILayout.Label("Current consent status: " + _currentConsentStatus, _smallerFont);
        GUILayout.Label("Should show consent dialog: " + _shouldShowConsentDialog, _smallerFont);
        GUILayout.Label(
            "Is GDPR applicable: " + (_isGdprApplicable != null ? _isGdprApplicable.ToString() : "Unknown"),
            _smallerFont);

        GUILayout.BeginHorizontal();
        GUI.enabled = !ConsentDialogLoaded;
        if (GUILayout.Button("Load Consent Dialog")) {
            UpdateStatusLabel("Loading consent dialog");
            MoPub.LoadConsentDialog();
        }
        GUI.enabled = ConsentDialogLoaded;
        if (GUILayout.Button("Show Consent Dialog")) {
            ClearStatusLabel();
            MoPub.ShowConsentDialog();
        }
        GUI.enabled = !_isGdprForced;
        if (GUILayout.Button("Force GDPR")) {
            ClearStatusLabel();
            MoPub.ForceGdprApplicable();
            UpdateConsentValues();
            _isGdprForced = true;
        }
        GUI.enabled = true;
        if (GUILayout.Button("Grant Consent")) {
            MoPub.PartnerApi.GrantConsent();
        }
        if (GUILayout.Button("Revoke Consent")) {
            MoPub.PartnerApi.RevokeConsent();
        }

        GUI.enabled = true;

        GUILayout.EndHorizontal();
    }


    private void CreateActionsSection()
    {
        GUILayout.Space(_sectionMarginSize);
        GUILayout.Label("Actions");
        if (GUILayout.Button("Report App Open")) {
            ClearStatusLabel();
            MoPub.ReportApplicationOpen();
        }

        if (!GUILayout.Button("Enable Location Support")) return;

        ClearStatusLabel();
        MoPub.EnableLocationSupport(true);
    }


    private void UpdateConsentValues()
    {
        _canCollectPersonalInfo = MoPub.CanCollectPersonalInfo;
        _currentConsentStatus = MoPub.CurrentConsentStatus;
        _shouldShowConsentDialog = MoPub.ShouldShowConsentDialog;
        _isGdprApplicable = MoPub.IsGdprApplicable;
    }


    private static void CreateCustomDataField(string fieldName, ref string customDataValue)
    {
        GUI.SetNextControlName(fieldName);
        customDataValue = GUILayout.TextField(customDataValue, GUILayout.MinWidth(200));
        if (Event.current.type != EventType.Repaint) return;
        if (GUI.GetNameOfFocusedControl() == fieldName && customDataValue == _customDataDefaultText) {
            // Clear default text when focused
            customDataValue = string.Empty;
        } else if (GUI.GetNameOfFocusedControl() != fieldName && string.IsNullOrEmpty(customDataValue)) {
            // Restore default text when unfocused and empty
            customDataValue = _customDataDefaultText;
        }
    }


    private void CreateStatusSection()
    {
        GUILayout.Space(40);
        GUILayout.Label(_status, _smallerFont);
    }


    private static string GetCustomData(string customDataFieldValue)
    {
        return customDataFieldValue != _customDataDefaultText ? customDataFieldValue : null;
    }


    private static string CreateRequestButtonLabel(string adUnit)
    {
        return adUnit.Length > 10 ? "Request " + adUnit.Substring(0, 10) + "..." : adUnit;
    }
}
