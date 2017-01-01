angular.module('nflDraftApp')
        .controller('PlayerNotesModalCtrl', ["$rootScope", "$scope", "$uibModalInstance", "player", function ($rootScope, $scope, $uibModalInstance, player) {

        	$scope.init = function(){
        		$scope.player = player;
        		
        		if(!$scope.player.notes.id){
        			$scope.player.notes = {
        				player : {id : $scope.player.id},
        				notes : '',
        				username : $rootScope.user.username
        			}
        		}
        	};
        	
        	$scope.submit = function(){
        		$uibModalInstance.close($scope.player);
        	};
        	
        	$scope.cancel = function(){
        		$uibModalInstance.dismiss();
        	};
        	
        	$scope.init();
        	
        }]);