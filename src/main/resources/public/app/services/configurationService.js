(function(){
	
	angular.module('nflDraftApp').service('ConfigurationService', ['ApiService', '$q', function(ApiService, $q){
		
		var service = {};
		
		var years = [];
		var positions = [];
		var positionCategories = [];
		var positionSidesOfBall = [];
		var colleges = [];
		var conferences = [];
		
		service.initConfig = function(){
			var deferred = $q.defer();
			ApiService.apiSendGetNoAuth('api/config').then(function(data){
				years = data.years.map(function(d, idx){
					return {
						id: idx,
						name: d
					}
				});
				positions = data.positions;
				positionCategories = data.positionCategories;
				positionSidesOfBall = data.positionSidesOfBall;
				colleges = data.colleges;
				conferences = data.conferences;
				deferred.resolve();
			});
			return deferred.promise;
		};
		
		service.getYears = function(){
			return years;
		};
		
		service.getPositions = function(){
			return positions;
		};
		
		service.getPositionCategories = function(){
			return positionCategories;
		};
		
		service.getPositionSidesOfBall = function(){
			return positionSidesOfBall;
		};
		
		service.getColleges = function(){
			return colleges;
		};
		
		service.getConferences = function(){
			return conferences;
		};
		
		return service;
		
	}]);
	
})();