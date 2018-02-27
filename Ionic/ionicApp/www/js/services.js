// Ionic starter App

// angular.module is a global place for creating, registering and retrieving Angular modules
// 'starter' is the name of this angular module example (also set in a <body> attribute in index.html)
// the 2nd parameter is an array of 'requires'
angular.module('starter.services',[])

.service('presage', function () {
        return {
            setAdUnit : function() {
                CPresage.presageInterstitial.setAdUnit(this.onAdEvent, this.onAdNotFound, "6e3b8e30-be56-0135-5539-0242ac120003");
            },        

            loadAd : function() {
                CPresage.presageInterstitial.load(this.onAdEvent, this.onAdNotFound);  
            },        

            isLoaded : function() {
                CPresage.presageInterstitial.canShow();
            },        

            showAd : function() {
                CPresage.presageInterstitial.show(this.onAdEvent, this.onAdNotFound);
            }

        };

    })
