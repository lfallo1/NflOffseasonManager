angular.module('springDemoApp').controller('LoginCtrl', ['$q', '$http', '$scope', '$rootScope', "$location", "ApiService",
                                                         function($q, $http, $scope, $rootScope, $location, ApiService){
	
	$scope.login = function(){
		var url = 'oauth/token?grant_type=password&username='+ $scope.username +'&password=' + $scope.password;
		$http.post(url, {}, $rootScope.clientAuthHeader).then(function(res){
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
	
	
}]);