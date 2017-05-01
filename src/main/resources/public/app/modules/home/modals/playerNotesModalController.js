angular.module('nflDraftApp')
        .controller('PlayerNotesModalCtrl', ["$rootScope", "$scope", "$uibModal", "$uibModalInstance", "$timeout", "player", 'PlayerService', function ($rootScope, $scope, $uibModal, $uibModalInstance, $timeout, player, PlayerService) {

        	$scope.init = function(){
        		
        		$scope.projectedRoundOptions = PlayerService.getProjectedRoundOptions();
        		$scope.likenessOptions = PlayerService.getLikenessOptions();

        		$scope.player = player;
        		$scope.playerDetails = player.details.name;
        		if(player.details.position.name && player.details.collegeText){
        			$scope.playerDetails += ' (' + player.details.position.name +  ' - ' + player.details.collegeText + ')';
        		}

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
        			var hue = Math.max(overallGrade * (1.25) - (100-overallGrade)*.67, 0);
        			return 'hsl(' + hue + ', 58%, 50%)';
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
        	
        	$scope.delete = function(){
        		$uibModalInstance.close({
        			action: 'DELETE_NOTE'
        		});
        	};
        	
        	$scope.share = function(){
                $uibModal.open({
                    templateUrl: 'app/modules/home/modals/sharePlayerModal.html',
                    controller: 'SharePlayerModalCtrl',
                    size: 'lg',
                    resolve: {
                        player: function () {
                            return $scope.player;
                        }
                    }
                }).result.then(function (result) {
                	//TODO
                });
        	};

        	$scope.init();

        }]);
