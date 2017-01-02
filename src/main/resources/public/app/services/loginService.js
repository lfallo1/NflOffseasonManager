(function(){
	
	angular.module('nflDraftApp').service('LoginService', ['PlayerService', '$rootScope', '$route', '$location', '$q', function(PlayerService, $rootScope, $route, $location, $q){
	
		var service = {};
		
		service.setToken = function(res){
			localStorage.setItem("authorization", JSON.stringify(res.data));
			$rootScope.authentication = true;
			service.setUser(res.data.user_details);
		};
		
		service.setUser = function(userDetails){
			$rootScope.user = userDetails;
		};
		
		service.clearUser = function(){
			PlayerService.clear();
			$rootScope.user = undefined;
			$rootScope.authentication = false;
			localStorage.removeItem('authorization');
			if($location.path() == '/'){
				$route.reload();
			}
		};
		
        service.rejectIfLoggedIn = function(){
        	var deferred = $q.defer();
        	if($rootScope.user){
        		deferred.reject();
        		$location.path('/');
        	} else{
        		deferred.resolve();
        	}
        	return deferred.promise;
        };
		
		return service;
		
	}]);
	
})();