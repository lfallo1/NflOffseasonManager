angular.module('springDemoApp').controller('AdminCtrl', ['$scope', 'users', '$stateParams', function($scope, users, $stateParams){

	var init = function(){
		$scope.myUsernameParam = $stateParams.username || 'N/A';
		$scope.users = users;
	};
	
	init();
	
}])
