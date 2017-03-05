(function(){
	
	angular.module('nflDraftApp').service('PlayerService', ['$rootScope', '$q', function($rootScope, $q){
		
		var players = [];
		var filtered = [];
		
		var ignoreNullOrZeroWhenSortingList = ['broadJump', 'verticalJump', 'benchPress', 'threeConeDrill', 'sixtyYardShuttle', 'twentyYardShuttle', 'fortyYardDash'];
		
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
            
            //determine if the property on which we are sorting should exclude nulls / zero values
            var ignoreZeroNull = ignoreNullOrZeroWhenSortingList.indexOf(sortParam.value) > -1;
            
            //objects to end to end of array. used if sorting should ignore zero values.
            //so the sorting takes place on objects with a non-zero property value, and all objects with
            //a zero prop value are added to this "end" array and then appended to the end of the sorted list
            var end = [];
            if(ignoreZeroNull){
            	data = data.filter(function(d){
                    var i = 0;
                    var val = d;
                    while( i < len ) { 
                    	val = val[prop[i]]; i++; 
                    }
                    
                    if(!val){ end.push(d); }
                    return val;
            	});
            }
            
        	data = data.sort(function (a, b) {
            	
                //find property to sort by
                var i = 0;
                while( i < len ) { 
                	a = a[prop[i]]; b = b[prop[i]]; i++; 
                }
                
                //perform sort
                return a > b ? sortParam.direction : a < b ? -sortParam.direction : 0;
            });
        	
        	if(ignoreZeroNull && end && end.length > 0){
        		end.forEach(function(d){
        			data.push(d);
        		});
        	}
        	
        	return data;
        };
        
        var valueExistsInArray = function(arr, prop, value){
        	return arr.filter(function(d){
        		return d[prop] === value
        	}).length > 0;
        };
		
		service.sortAndFilter = function(filterParams, sortParam, favorite, pagination){
			filtered = sort(players.filter(function(d){
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
			}), sortParam);
			
			updatePagination();
			return service.gotoPage(service.pagination.currentPage);
		};
		
		service.pagination = {
    			currentPage : 1,
    			totalPages : 1,
    			totalResults : 0,
    			startIdx : 0,
    			endIdx : 0,
    			resultsPerPage : 50
    		};
		
		var paginate = function(){
			return filtered.filter(function(d,idx){return idx >= service.pagination.startIdx && idx <= service.pagination.endIdx;});
		};
		
		service.gotoPage = function(page){
			
			if(page > 0 && page <= service.pagination.totalPages){
				service.pagination.currentPage = page;
			} else{
				service.pagination.currentPage = 1;
			}
			service.pagination.startIdx = service.pagination.resultsPerPage * (service.pagination.currentPage - 1);
			service.pagination.endIdx = service.pagination.resultsPerPage * service.pagination.currentPage - 1;
			return paginate();
		}
		
		var updatePagination = function(){
			service.pagination.totalResults = filtered.length;
			service.pagination.totalPages = Math.ceil(filtered.length / service.pagination.resultsPerPage);
		}
		
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