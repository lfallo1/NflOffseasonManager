

	angular.module('springDemoApp').service('DbService', ['DbConstants', function(DbConstants){

		var service = {};

		var currentDb = DbConstants.DB_DEFAULT;

		service.setDb = function(db){
			if(db === DbConstants.DB_DEFAULT || db === DbConstants.DB_BACKUP){
				currentDb = db;
			}
		};

		service.getDb = function(){
			return currentDb;
		};

		service.getDbKey = function(){
			return "?key=" + currentDb;
		};

		return service;

	}]);

