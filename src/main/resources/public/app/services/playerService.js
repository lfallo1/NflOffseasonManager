(function(){
	
	angular.module('nflDraftApp').service('PlayerService', ['$rootScope', '$q', function($rootScope, $q){
		
		var players = [];
		
		var service = {};
		
		service.getPlayers = function(){
			return players;
		};
		
		service.setPlayers = function(data){
			players = data;
		};

		service.load = function(filterParams, sortParam){
			var deferred = $q.defer();
			deferred.resolve(sortAndFilter(players, filterParams, sortParam));
			return deferred.promise;
		};
		
		service.clear = function(){
			players = [];
		};
		
        var sort = function(data, sortParam){
        	var prop = sortParam.value;
        	prop = prop.split('.');
            var len = prop.length;
        	data = data.sort(function (a, b) {
            	
                //find property to sort by
                var i = 0;
                while( i < len ) { 
                	a = a[prop[i]]; b = b[prop[i]]; i++; 
                }
                
                //perform sort
                return a > b ? sortParam.direction : a < b ? -sortParam.direction : 0;
            });
        	return data;
        };
        
        var valueExistsInArray = function(arr, prop, value){
        	return arr.filter(function(d){
        		return d[prop] === value
        	}).length > 0;
        };
		
		service.sortAndFilter = function(filterParams, sortParam){
			return sort(players.filter(function(d){
				if(filterParams.positionSidesOfBall.length > 0 && !valueExistsInArray(filterParams.positionSidesOfBall, 'id', d.position.category.positionSideOfBall.id)){
					return false;
				}
				if(filterParams.positions.length > 0 && !valueExistsInArray(filterParams.positions, 'id', d.position.id)){
					return false;
				}
				if(filterParams.positionCategories.length > 0 && !valueExistsInArray(filterParams.positionCategories, 'id', d.position.category.id)){
					return false;
				}
				if(filterParams.conferences.length > 0 && !valueExistsInArray(filterParams.positionCategories, 'id', d.college.conference.id)){
					return false;
				}
				if(filterParams.colleges.length > 0 && !valueExistsInArray(filterParams.colleges, 'id', d.college.id)){
					return false;
				}
				if(filterParams.years.length > 0 && !valueExistsInArray(filterParams.years, 'name', d.year)){
					return false;
				}
				return true;
			}), sortParam).filter(function(d,idx){return idx < 750;});
		};
		
		return service;
		
	}]);
	
})();