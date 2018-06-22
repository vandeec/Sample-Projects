var CPresage = {

    presageSdk: {
        initializeSdk: function (onAdEvent, onAdNotFound, apiKey) {
            cordova.exec(onAdEvent, onAdNotFound, 'CPresage', 'initializeSdk', [apiKey]);
        }
    },
    presageInterstitial: {        
        load: function(onAdEvent, onAdNotFound) {
            cordova.exec(onAdEvent, onAdNotFound, 'CPresage', 'load', [{}]);
        },
        show: function(onAdEvent, onAdNotFound) {
            cordova.exec(onAdEvent, onAdNotFound, 'CPresage', 'show', [{}]);
        },
        isLoaded: function(onAdEvent, onAdNotFound) {
            cordova.exec(onAdEvent, onAdNotFound, 'CPresage', 'isLoaded', [{}]);
        },
        setAdUnit: function(onAdEvent, onAdNotFound, adUnit) {
            cordova.exec(onAdEvent, onAdNotFound, 'CPresage', 'setAdUnit', [adUnit]);
        }
    },
	presageOptinVideo: {
        load: function(onAdEvent, onAdNotFound) {
            cordova.exec(onAdEvent, onAdNotFound, 'CPresage', 'optinVideoLoad', [{}]);
        },
        show: function(onAdEvent, onAdNotFound) {
            cordova.exec(onAdEvent, onAdNotFound, 'CPresage', 'optinVideoShow', [{}]);
        },
        isLoaded: function(onAdEvent, onAdNotFound) {
            cordova.exec(onAdEvent, onAdNotFound, 'CPresage', 'optinVideoIsLoaded', [{}]);
        },
        setAdUnit: function(onAdEvent, onAdNotFound, adUnit) {
            cordova.exec(onAdEvent, onAdNotFound, 'CPresage', 'optinVideoSetAdUnit', [adUnit]);
        },
        setUserId: function(onAdEvent, onAdNotFound) {
            cordova.exec(onAdEvent, onAdNotFound, 'CPresage', 'SetUserId', [userId]);
        }
    },
    presageEula: {
        launchWithEula: function(onAdEvent, onAdNotFound) {
            cordova.exec(onAdEvent, onAdNotFound, 'CPresage', 'launchWithEula', [{}]);
        }
    }
};