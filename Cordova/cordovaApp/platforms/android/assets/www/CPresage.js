var CPresage = {

    presageInterstitial: {
        adToServe: function(onAdEvent, onAdNotFound) {
            cordova.exec(onAdEvent, onAdNotFound, 'CPresage', 'adToServe', [{}]);
        },
        load: function(onAdEvent, onAdNotFound) {
            cordova.exec(onAdEvent, onAdNotFound, 'CPresage', 'load', [{}]);
        },
        show: function(onAdEvent, onAdNotFound) {
            cordova.exec(onAdEvent, onAdNotFound, 'CPresage', 'show', [{}]);
        },
        canShow: function(onAdEvent, onAdNotFound) {
            cordova.exec(onAdEvent, onAdNotFound, 'CPresage', 'canShow', [{}]);
        },
        setAdUnit: function(onAdEvent, onAdNotFound, adUnit) {
            cordova.exec(onAdEvent, onAdNotFound, 'CPresage', 'setAdUnit', [adUnit]);
        }
    },
    presageOptinVideo: {
        adToServe: function(onAdEvent, onAdNotFound) {
            cordova.exec(onAdEvent, onAdNotFound, 'CPresage', 'optinVideoAdToServe', [{}]);
        },
        load: function(onAdEvent, onAdNotFound) {
            cordova.exec(onAdEvent, onAdNotFound, 'CPresage', 'optinVideoLoad', [{}]);
        },
        show: function(onAdEvent, onAdNotFound) {
            cordova.exec(onAdEvent, onAdNotFound, 'CPresage', 'optinVideoShow', [{}]);
        },
        canShow: function(onAdEvent, onAdNotFound) {
            cordova.exec(onAdEvent, onAdNotFound, 'CPresage', 'optinVideoCanShow', [{}]);
        },
        setAdUnit: function(onAdEvent, onAdNotFound, adUnit) {
            cordova.exec(onAdEvent, onAdNotFound, 'CPresage', 'optinVideoSetAdUnit', [adUnit]);
        },
        getRewardItem: function(onAdEvent, onAdNotFound) {
            cordova.exec(onAdEvent, onAdNotFound, 'CPresage', 'GetRewardItem', [{}]);
        },
        setRewardItem: function(onAdEvent, onAdNotFound, [type, amount]) {
            cordova.exec(onAdEvent, onAdNotFound, 'CPresage', 'SetRewardItem', [{ type, amount }]);
        },
        getUserId: function(onAdEvent, onAdNotFound) {
            cordova.exec(onAdEvent, onAdNotFound, 'CPresage', 'GetUserId', [{}]);
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