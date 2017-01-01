'use strict';

angular.module('nflDraftApp', ['ngRoute', 'ngAnimate', 'angular-storage', 'angularjs-dropdown-multiselect', 'ui.bootstrap'])
        .config(['$routeProvider', '$httpProvider', function ($routeProvider, $httpProvider) {

        	//add an http request interceptor (userful for handling errors from server), or managing / monitoring api requests
            $httpProvider.interceptors.push('httpRequestInterceptor');

            var LoadConfiguration = function (ConfigurationService) {
                return ConfigurationService.initConfig();
            };
            
            $routeProvider
                .when('/', {
                    title: 'Home',
                    templateUrl: 'app/modules/home/home.html',
                    controller: 'HomeCtrl',
                    resolve: {
                        configured: LoadConfiguration
                    }
                })
                .when('/login', {
                    title: 'Login',
                    templateUrl: 'app/modules/login/login.html',
                    controller: 'LoginCtrl'
                })
                .otherwise({
                    redirectTo: '/'
                });
 
        }])
        .run(['$rootScope', 'ApiService', '$location', '$http', 'PlayerService', '$route', function ($rootScope, ApiService, $location, $http, PlayerService, $route) {
        		$rootScope.clientAuthHeader = {headers : {'Authorization' : 'Basic ' + btoa('trustedclient:secret')}};
        	
        		if(localStorage.getItem('authorization')){
        			ApiService.apiSendGet('getuser').then(function(res){
        				$rootScope.user = res;
        			});
        			$rootScope.authenticated = true;
        		}
        		
        		$rootScope.logout = function(){
        			$http.post("logout", {headers : {Authorization : 'Bearer ' + localStorage.getItem("authorization")}}).then(function(){
        				PlayerService.clear();
        				$rootScope.user = undefined;
        				$rootScope.authentication = false;
        				localStorage.removeItem('authorization');
        				if($location.path() == '/'){
        					$route.reload();
        				}
        			})
        		};
        		
            }]);
