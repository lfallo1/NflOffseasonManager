(function(){
	
	angular.module('nflDraftApp').service('PlayerService', ['$rootScope', 'ApiService', 'PlayersApiConstants', '$q', function($rootScope, ApiService, PlayersApiConstants, $q){
		
		var players = [];
		
		var service = {};
		
		service.load = function(filterParams, sortParam){
			
			var deferred = $q.defer();
			
			if(players.length == 0){
				if($rootScope.user){
					ApiService.apiSendGet(PlayersApiConstants.PLAYERS_FIND_ALL).then(function(data){
			    		players = data;
			    		deferred.resolve(sortAndFilter(players, filterParams, sortParam));
			    	}, function(err){
			    		console.log(err);
			    	});					
				} else{
					ApiService.apiSendGetNoAuth(PlayersApiConstants.PLAYERS_FIND_ALL).then(function(data){
			    		players = data;
			    		deferred.resolve(sortAndFilter(players, filterParams, sortParam));
			    	}, function(err){
			    		console.log(err);
			    	});
				}
			} else{
				deferred.resolve(sortAndFilter(players, filterParams, sortParam));
			}
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
		
		var sortAndFilter = function(data, filterParams, sortParam){
			return sort(data.filter(function(d){
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
				return d.positionRank > 0;
			}), sortParam);
		};
		
		return service;
		
	}]);
	
})();