angular.module('nflDraftApp')
        .controller('ConfirmModalCtrl', ["$rootScope", "$scope", "$uibModalInstance", "data", function ($rootScope, $scope, $uibModalInstance, data) {

        	$scope.init = function(){
        		$scope.message = data.message;
        		$scope.title = data.title;
        	};

        	$scope.confirm = function(){
        		$uibModalInstance.close();
        	};

        	$scope.cancel = function(){
        		$uibModalInstance.dismiss();
        	};

        	$scope.init();

        }]);
