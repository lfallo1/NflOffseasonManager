angular.module('nflDraftApp').controller('LoginCtrl', ['$q', '$http', '$scope', '$rootScope', "$location", "ApiService", "PlayerService",
                                                         function($q, $http, $scope, $rootScope, $location, ApiService, PlayerService){

	var setActivePanel = function(){
		$scope.loginActive = true;
		$scope.registerActive = false;
		if($location.search().view && $location.search().view === 'register'){
			$scope.loginActive = false;
			$scope.registerActive = true;		
		}
	}
	
	$scope.login = function(){
		var url = 'oauth/token?grant_type=password&username='+ $scope.username +'&password=' + $scope.password;
		$http.post(url, {}, $rootScope.clientAuthHeader).then(function(res){
			PlayerService.clear();
			localStorage.setItem("authorization", res.data.access_token);
			$rootScope.authentication = true;
			ApiService.apiSendGet('getuser').then(function(res){
				$rootScope.user = res;
				$location.path("/");
			});
			
		}, function(err){
			console.log(err);
		});
	};
	
	setActivePanel();
	
}]);