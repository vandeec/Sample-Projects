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
    },

    // deviceready Event Handler
    //
    // Bind any cordova events here. Common events are:
    // 'pause', 'resume', etc.
    onDeviceReady: function() {
        this.receivedEvent('deviceready');
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

    setAdUnit : function() {
        CPresage.presageInterstitial.setAdUnit(app.onAdEvent, app.onAdNotFound, "505101d0-bbe8-0135-6877-0242ac120003");
    },

    loadAd : function() {
        CPresage.presageInterstitial.load(app.onAdEvent, app.onAdNotFound);  
    },

    canShow : function() {
        CPresage.presageInterstitial.canShow();
    },

    showAd : function() {
        CPresage.presageInterstitial.show(app.onAdEvent, app.onAdNotFound);
    },
    isAdLoaded : function(){
        var adLoaded;
        if (CPresage.presageInterstitial.canShow()){
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