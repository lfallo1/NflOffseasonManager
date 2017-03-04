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
		
		service.sortAndFilter = function(filterParams, sortParam, favorite){
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
				if(filterParams.conferences.length > 0 && !valueExistsInArray(filterParams.conferences, 'id', d.college.conf.id)){
					return false;
				}
				if(filterParams.colleges.length > 0 && !valueExistsInArray(filterParams.colleges, 'id', d.college.id)){
					return false;
				}
				if(filterParams.years.length > 0 && !valueExistsInArray(filterParams.years, 'name', d.year)){
					return false;
				}
				return !favorite || d.notes.id;
			}), sortParam).filter(function(d,idx){return idx < 750;});
		};
		
		/**
		 * helper method to determine if valid grade
		 */
		service.isValidGrade = function(grade){
			return grade && !isNaN(grade) && grade >= 0 && grade <= 100;
		};
		
		service.getClassByPlayerGrade = function(prefix, grade){
			if(grade >= 85){
				return prefix + 'success';
			} else if(grade >= 70){
				return prefix + 'info';
			} else if(grade >= 50){
				return prefix + 'warning';
			} else{
				return prefix + 'danger';
			}
		};
		
		return service;
		
	}]);
	
})();