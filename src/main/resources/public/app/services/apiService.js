
	angular.module('nflDraftApp').service('ApiService', ['$rootScope', '$http', '$q', '$location', 'LoginService', function($rootScope, $http, $q, $location, LoginService){

		/**
		 * 				{
				  "access_token": "4039d976-f754-47d5-96ef-51788b61f126",
				  "token_type": "bearer",
				  "refresh_token": "374dfda9-9dd6-45a4-b9ba-cfb064fb2644",
				  "expires_in": 119,
				  "scope": "read write trust"
				}
		 */
		
		var service = {};
		
		var refreshToken = 'oauth/token?grant_type=refresh_token&refresh_token=';
		
		var getRefreshToken = function(){
			var deferred = $q.defer();
			$http.post(refreshToken + $rootScope.authentication.refresh_token, {headers : headers}).then(function(res){
				$rootScope.authentication = res.data;
				deferred.resolve();
			}, function(err){
				deferred.reject();
			});
			return deferred.promise;
		};
		
		service.apiSendGetNoAuth = function(url){
			var deferred = $q.defer();
			$http.get(url).then(function(res){
				deferred.resolve(res.data);
			}, function(err){
				deferred.reject(err);
			});
			return deferred.promise;
		}

		service.apiSendGet = function(url, deferred){
			var deferred = deferred || $q.defer();
			var token = JSON.parse(localStorage.getItem('authorization')).access_token
			if(token){
				var headers = {headers : {'Authorization' : 'Bearer ' + token}}
				$http.get(url, headers).then(function(res){
					deferred.resolve(res.data);
				}, function(err){
					if(err.data && err.data.error_description && err.data.error_description.indexOf('expired') > -1){
						tryRefreshToken().then(function(){
							return service.apiSendGet(url, deferred);
						}, function(err){
							deferred.reject();
						});
					} else{
						console.log(err.data);
//						LoginService.clearUser();
						deferred.reject(err);
					}
				});
			} else{
				deferred.reject();
			}
			return deferred.promise;
		};
		
		service.apiSendPost = function(url, payload, deferred){
			var deferred = deferred || $q.defer();
			var token = JSON.parse(localStorage.getItem('authorization')).access_token
			if(token){
				var headers = {headers : {'Authorization' : 'Bearer ' + token}}
				$http.post(url, payload, headers).then(function(res){
					deferred.resolve(res.data);
				}, function(err){
					if(err.data && err.data.error_description && err.data.error_description.indexOf('expired')){
						tryRefreshToken().then(function(){
							return service.apiSendPost(url, payload, deferred);
						}, function(err){
							deferred.reject();
						})
					} else{
						console.log(err);
//						LoginService.clearUser();
						deferred.reject(err);
					}
				});
			} else{
				deferred.reject();
			}
			return deferred.promise;
		};
		
		var tryRefreshToken = function(){
			var deferred = $q.defer();
			var url = 'oauth/token?grant_type=refresh_token&refresh_token=' + JSON.parse(localStorage.getItem('authorization')).refresh_token;
			service.tokenEndpoint(url).then(function(){
				console.log('got refresh token');
				deferred.resolve();
			}, function(err){
				console.log('failed to get refresh token, logging out and refreshing page...');
				LoginService.clearUser();
				deferred.reject();
			});
			return deferred.promise;
		}
		
		service.tokenEndpoint = function(url){
			var deferred = $q.defer();
			$http.post(url, {}, $rootScope.clientAuthHeader).then(function(res){
				LoginService.setToken(res);
				deferred.resolve();
			}, function(err){
				deferred.reject();
			});
			return deferred.promise;
		}

		return service;

	}]);
