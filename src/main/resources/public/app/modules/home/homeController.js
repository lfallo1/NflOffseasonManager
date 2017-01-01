angular.module("nflDraftApp")
        .controller("HomeCtrl", ["$rootScope", "$scope", "PlayerService", "ConfigurationService", "ApiService", "$uibModal", function ($rootScope, $scope, PlayerService, ConfigurationService, ApiService, $uibModal) {
        	
    		$scope.yearOptions = [];
        	$scope.collegeOptions = [];
        	$scope.conferenceOptions = [];
        	$scope.positionOptions = [];
        	$scope.positionCategoryOptions = [];
        	$scope.positionSideOfBallOptions = [];
        	
        	$scope.filterParams = {
        		years : [{id:0,name:2017}],
        		positions : [],
        		positionCategories : [],
        		positionSidesOfBall : [],
        		conferences : [],
        		colleges : []
        	};

            $scope.smartButtonSettings = {
            	displayProp: 'name',
            	externalIdProp: '',
            	smartButtonMaxItems: 3,
            	smartButtonTextConverter: function(itemText, originalItem) {
                    return itemText;
                }
            };
            
            $scope.onMultiSelectEvents = {
            	onItemSelect : function(){$scope.loadPlayers();},
	            onItemDeselect : function(){$scope.loadPlayers();}	
            };
        	
        	$scope.sortParam = {
        		value : 'positionRank',
        		direction : 1
        	}
        	
        	$scope.sort = function(sortParam){
        		$scope.sortParam.value = sortParam;
        		$scope.sortParam.direction = $scope.sortParam.direction * -1; 
        		$scope.loadPlayers();
        	};
        	
        	$scope.loadPlayers = function(){
        		$scope.loading = true;
    			PlayerService.load($scope.filterParams, $scope.sortParam).then(function(players){
    				$scope.players = players;
    				$scope.loading = false;
    			});
    		};
    		
    		$scope.deleteNote = function(player){
    			ApiService.apiSendPost('api/notes/delete', player.notes).then(function(){
    				console.log('deleted');
    				player.notes = {};
    			});
    		};
    		
    		$scope.openNotesModal = function(player){
    			
                //open notes modal
                var modalInstance = $uibModal.open({
                    templateUrl: 'app/modules/home/modals/playerNotesModal.html',
                    controller: 'PlayerNotesModalCtrl',
                    size: 'md',
                    resolve: {
                        player: function () {
                            return player;
                        }
                    }
                });

                //wait for the modal instance to resolve
                modalInstance.result.then(function (player) {

                    //save / update notes for the player
                	ApiService.apiSendPost('api/notes', player.notes).then(function (data) {
                    	if(!player.notes.id){
                    		player.notes.id = data;
                    	}
                    	console.log('success');
                    }, function (err) {
                        console.log(err);
                    });
                });
    			
    		};
    		
    		var init = function(){
    			$rootScope.currentPage = "Home";
    			$scope.loadPlayers();
    			
            	$scope.yearOptions = ConfigurationService.getYears();
            	$scope.collegeOptions = ConfigurationService.getColleges();
            	$scope.conferenceOptions = ConfigurationService.getConferences();
            	$scope.positionOptions = ConfigurationService.getPositions();
            	$scope.positionCategoryOptions = ConfigurationService.getPositionCategories();
            	$scope.positionSideOfBallOptions = ConfigurationService.getPositionSidesOfBall();
    		};
    		
    		init();
        	
        	
}]);