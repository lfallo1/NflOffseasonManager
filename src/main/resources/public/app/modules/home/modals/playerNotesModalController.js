angular.module('nflDraftApp')
        .controller('PlayerNotesModalCtrl', ["$rootScope", "$scope", "$uibModalInstance", "$timeout", "player", 'PlayerService', function ($rootScope, $scope, $uibModalInstance, $timeout, player, PlayerService) {

        	$scope.init = function(){
        		
        		$scope.projectedRoundOptions = PlayerService.getProjectedRoundOptions();
        		$scope.likenessOptions = PlayerService.getLikenessOptions();

        		$scope.player = player;

        		if(!$scope.player.notes.id){
        			$scope.player.notes = {
        				player : {id : $scope.player.id},
        				username : $rootScope.user.username,
        				summary : '',
        				strengths : '',
        				weaknesses : '',
        				likeness : 3,
        				projectedRound : 2,
        				overallGrade : 75
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

        	$scope.isValidGrade = function(overallGrade){
        		return PlayerService.isValidGrade(overallGrade);
        	};

        	$scope.getColorByPlayerGrade = function(overallGrade){
        		if($scope.isValidGrade(overallGrade)){
        			return 'hsl(' + overallGrade * (1.25) + ', 58%, 50%)';
        		}
        		return 'hsl(0, 58%, 50%)';
        	};

        	//if the grade is invalid, the display value will be 100 for sake of the progress bar
        	$scope.sanitizedGradeValue = function(overallGrade){
        		return $scope.isValidGrade(overallGrade) ? overallGrade : 100;
        	};

        	$scope.submit = function(){
        		if($scope.isValidGrade($scope.player.notes.overallGrade)){
        			$uibModalInstance.close($scope.player);
        		}
        	};

        	$scope.cancel = function(){
        		$uibModalInstance.dismiss();
        	};

        	$scope.init();

        }]);
