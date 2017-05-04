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
		ApiService.tokenEndpoint(url).then(function(res){
			ApiService.updateUserMeta(); //update the user metadata table
			PlayerService.clear();
			$location.path("/");
		}, function(err){
			$scope.error = true;
			console.log(err);
		});
	};
	
	setActivePanel();
	
}]);