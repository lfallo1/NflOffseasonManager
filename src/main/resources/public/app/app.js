'use strict';

angular.module('nflDraftApp', ['ngRoute', 'ngAnimate', 'angular-storage', 'angularjs-dropdown-multiselect', 'ui.bootstrap', 'toaster', 'ngClipboard', 'rzModule', 'angucomplete-alt'])
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
            .when('/admin', {
                title: 'Admin Dashboard',
                templateUrl: 'app/modules/admin/adminDashboard.html',
                controller: 'AdminDashboardCtrl',
                resolve: {
                    configured: LoadConfiguration,
                    hasRole: function (LoginService) {
                        return LoginService.hasRole(['ROLE_ADMIN']);
                    }
                }
            })
            .when('/admin/player/:id?', {
                title: 'Admin Player Management',
                templateUrl: 'app/modules/admin/playerManagement.html',
                controller: 'AdminPlayerManagementCtrl',
                resolve: {
                    configured: LoadConfiguration,
                    hasRole: function (LoginService) {
                        return LoginService.hasRole(['ROLE_ADMIN']);
                    }
                }
            })
            .when('/login', {
                title: 'Login',
                templateUrl: 'app/modules/login/login.html',
                controller: 'LoginCtrl',
                resolve: {
                    configured: LoadConfiguration,
                    rejectIfLoggedIn: function (LoginService) {
                        return LoginService.rejectIfLoggedIn();
                    }
                }
            })
            .otherwise({
                redirectTo: '/'
            });

    }])
    .run(['$rootScope', 'ApiService', 'SharedPlayerPollingService', '$location', '$http', 'LoginService', '$route', function ($rootScope, ApiService, SharedPlayerPollingService, $location, $http, LoginService, $route) {

        $rootScope.clientAuthHeader = {headers: {'Authorization': 'Basic ' + btoa('trustedclient:secret')}};

        if (localStorage.getItem('authorization')) {
            //set the user immediately & then hit the API to ensure the user is still actually logged in. this will
            //allow already authenticated users to access auth protected pages immediately instead of waiting for api
            //call. if they aren't actually logged in on the server, they will technically be directed to the
            //protected page but then immediately redirected home & logged out on the client.
            LoginService.setUser(JSON.parse(localStorage.getItem('authorization')).user_details);
            ApiService.apiSendGet('getuser').then(function (res) {
                LoginService.setUser(res);
                if ($route && $route.current) {
                    $location.path($route.current.$$route.originalPath);
                } else {
                    $location.path('/');
                }
            }, function (err) {
                LoginService.clearUser();
            });
        }
        ;

        $rootScope.logout = function () {
            $http.post("logout", {headers: {Authorization: 'Bearer ' + localStorage.getItem("authorization")}}).then(function () {
                LoginService.clearUser();
            })
        };

        $rootScope.isAdmin = function () {
            return $rootScope.user && $rootScope.user.authorities && $rootScope.user.authorities.map(function (r) {
                return r.authority
            }).indexOf('ROLE_ADMIN') > -1;
        };

    }]);
