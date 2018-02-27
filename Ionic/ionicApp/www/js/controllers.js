// Ionic starter App

// angular.module is a global place for creating, registering and retrieving Angular modules
// 'starter' is the name of this angular module example (also set in a <body> attribute in index.html)
// the 2nd parameter is an array of 'requires'
angular.module('starter.controllers', ['starter.services'])

.controller('MyController', ['$scope', 'presage', function($scope, presage) {
   $scope.loadAd = function() {
    presage.loadAd();
  };
  $scope.showAd = function() {
    presage.showAd();
  };
  $scope.setAdUnit = function() {
    presage.setAdUnit();
  };
  $scope.canShow = function() {
    presage.canShow();
  };
 }])