angular.module('nflDraftApp').factory('httpRequestInterceptor', function ($q, $rootScope, $location) {
	
	//handle requests / responses. If api request and user logged in, then session timer on front end should reset
	var log = function(data){
		console.log(data)
	};
	
    return {
        'request': function (config) {
        	log(config);
            return config;
        },
        'response': function (response) {
        	log(response);
            return response;
        },
        'responseError': function (rejection) {
        	log(rejection);
            return $q.reject(rejection);
        }
    };
});