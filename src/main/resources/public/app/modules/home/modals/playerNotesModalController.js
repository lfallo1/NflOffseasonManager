angular.module('nflDraftApp')
        .controller('PlayerNotesModalCtrl', ["$rootScope", "$scope", "$uibModalInstance", "$timeout", "player", 'PlayerService', function ($rootScope, $scope, $uibModalInstance, $timeout, player, PlayerService) {

        	$scope.init = function(){
        		
        		$scope.player = player;
        		
        		if(!$scope.player.notes.id){
        			$scope.player.notes = {
        				player : {id : $scope.player.id},
        				notes : '',
        				username : $rootScope.user.username,
        				grade : 75
        			}
        		}
        		$scope.sliderVisible = true;
        		refreshSlider();
        	};
        	
        	var refreshSlider = function(){
        		$timeout(function () {
        			$scope.$broadcast('rzSliderForceRender');
        	    });
        	}
        	
        	$scope.isValidGrade = function(grade){
        		return PlayerService.isValidGrade(grade);
        	};
        	
        	$scope.getClassByPlayerGrade = function(prefix, grade){
        		if($scope.isValidGrade(grade)){
        			return PlayerService.getClassByPlayerGrade(prefix, grade);
        		}
        		return 'danger';
        	};
        	
        	//if the grade is invalid, the display value will be 100 for sake of the progress bar
        	$scope.sanitizedGradeValue = function(grade){
        		return $scope.isValidGrade(grade) ? grade : 100;
        	};
        	
        	$scope.submit = function(){
        		if($scope.isValidGrade($scope.player.notes.grade)){
        			$uibModalInstance.close($scope.player);
        		}
        	};
        	
        	$scope.cancel = function(){
        		$uibModalInstance.dismiss();
        	};
        	
        	$scope.init();
        	
        }]);