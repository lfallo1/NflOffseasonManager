angular.module("springDemoApp").controller("ChangeDbCtrl", ["$scope", "DbService", "DbConstants", function($scope, DbService, DbConstants){
		
	$scope.DEFAULT = DbConstants.DB_DEFAULT;
	$scope.BACKUP = DbConstants.DB_BACKUP;
	$scope.selected = DbService.getDb();
	
	$scope.setDatabase = function(database){
		DbService.setDb(database);
		$scope.selected = DbService.getDb();
	};
	
}]);