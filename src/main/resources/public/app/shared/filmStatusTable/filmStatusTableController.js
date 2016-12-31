angular.module('springDemoApp').controller('FilmStatusTableController', ['$scope', function($scope){
	
	$scope.films = [
		{title: 'Indana Jjone', genre : 'Action', release : 1970},
		{title: 'Independence Day', genre: 'Action/Sci-fi', release : 1996}
	];
	
}]);