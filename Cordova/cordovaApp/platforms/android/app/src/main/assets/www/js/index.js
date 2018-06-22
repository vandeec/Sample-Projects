/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
var app = {
    // Application Constructor
    initialize: function() {
        document.addEventListener('deviceready', this.onDeviceReady.bind(this), false);
        document.getElementById("adUnit").addEventListener("click", this.setAdUnit, false);
            document.getElementById("load").addEventListener("click", this.loadAd, false);
            document.getElementById("isLoaded").addEventListener("click", this.isLoaded, false);
            document.getElementById("show").addEventListener("click", this.showAd, false);
    },

    // deviceready Event Handler
    //
    // Bind any cordova events here. Common events are:
    // 'pause', 'resume', etc.
    onDeviceReady: function() {
        this.receivedEvent('deviceready');
        CPresage.presageSdk.initializeSdk(app.onAdEvent, app.onAdNotFound, "270413");
    },
    // Update DOM on a Received Event
    receivedEvent: function(id) {
        var parentElement = document.getElementById(id);
        var listeningElement = parentElement.querySelector('.listening');
        var receivedElement = parentElement.querySelector('.received');

        listeningElement.setAttribute('style', 'display:none;');
        receivedElement.setAttribute('style', 'display:none;');

        console.log('Received Event: ' + id);
    },
    onAdEvent: function (event) {
        console.log(event);

        if (event == 'AdFound') {

        } else if (event == "AdClosed"){

        } else if (event == "AdAvailable") {

        } else if (event == "AdNotLoaded") {

        } else if (event == "isAdLoaded") {
            //CPresage.presageInterstitial.show(app.onAdEvent, app.onAdNotFound); // example
        } else if (event == "AdLoaded") {
            //CPresage.presageInterstitial.isLoaded(app.onAdEvent,app.onAdNotFound); // example
        } else if (event == "isVideoLoaded") {
            //CPresage.presageOptinVideo.show(app.onAdEvent, app.onAdNotFound); // example
        }
    },
    onAdNotFound: function(error) {
        console.log('[Ogury] ad not available');
    },
    onAdError: function(error) {
        console.log('[Ogury] error');
    },

    onAdDisplayed: function(error) {
        console.log('[Ogury] ad displayed');
    },

    setAdUnit : function() {
        CPresage.presageInterstitial.setAdUnit(app.onAdEvent, app.onAdNotFound, "505101d0-bbe8-0135-6877-0242ac120003");
    },

    loadAd : function() {
        CPresage.presageInterstitial.load(app.onAdEvent, app.onAdNotFound);  
    },

    isLoaded : function() {
        CPresage.presageInterstitial.isLoaded(app.onAdEvent, app.onAdNotFound);
    },

    showAd : function() {
        CPresage.presageInterstitial.show(app.onAdEvent, app.sonAdNotFound);
    },
    isAdLoaded : function(){
        var adLoaded;
        if (CPresage.presageInterstitial.isLoaded(app.onAdEvent, app.onAdNotFound)){
            adLoaded=true;
            console.log ('' + adLoaded);
            return true;
        }
        else {
            adLoaded=false;
            console.log ('' + adLoaded);
            return false;
        }
    }
};

app.initialize();