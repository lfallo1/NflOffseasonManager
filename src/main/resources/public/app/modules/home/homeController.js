angular.module("springDemoApp")
        .controller("HomeCtrl", ["$rootScope", "$scope", "ApiService", "FilmApiConstants", "$timeout", function ($rootScope, $scope, ApiService, FilmApiConstants, $timeout) {
        	
    		$scope.loadFilms = function(){
    			$scope.filmApiError = false;
    			ApiService.apiSendGet(FilmApiConstants.FILMS_FIND_ALL).then(function(data){
            		$scope.films = data;
            	}, function(err){
            		$scope.filmApiError = true;
            	});	
    		};
    		
    		var init = function(){
    			$rootScope.currentPage = "Home";
    			$scope.loadFilms();
    		};
    		
    		init();
        	
        	
}]);