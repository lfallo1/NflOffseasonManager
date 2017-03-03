angular.module('nflDraftApp')
        .controller('PlayerNotesModalCtrl', ["$rootScope", "$scope", "$uibModalInstance", "player", function ($rootScope, $scope, $uibModalInstance, player) {

        	$scope.init = function(){
        		$scope.player = player;
        		
        		if(!$scope.player.notes || !$scope.player.notes.id){
        			$scope.player.notes = {
        				player : {id : $scope.player.id},
        				notes : '',
        				grade : 75,
        				username : $rootScope.user.username
        			}
        		}
        	};
        	
        	$scope.isGradeValid = function(grade){
        		return grade >= 0 && grade <= 100;
        	}
        	
        	$scope.submit = function(){
        		if($scope.isGradeValid($scope.player.notes.grade)){
        			$uibModalInstance.close($scope.player);
        		}
        	};
        	
        	$scope.cancel = function(){
        		if(!$scope.player.notes.id){
        			$scope.player.notes = undefined;
        		}
        		$uibModalInstance.dismiss();
        	};
        	
        	$scope.init();
        	
        }]);