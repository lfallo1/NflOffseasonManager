angular.module("nflDraftApp")
        .controller("HomeCtrl", ["$rootScope", "$scope", "PlayerService", "ConfigurationService", "ApiService", "$uibModal", "PlayersApiConstants", "toaster", "ngClipboard", function ($rootScope, $scope, PlayerService, ConfigurationService, ApiService, $uibModal, PlayersApiConstants, toaster, ngClipboard) {
        	
        	$scope.loadPlayers = function(){
        		$scope.loading = true;
        		var players = PlayerService.getPlayers();
        		if(players.length == 0){
    				if($rootScope.user){
    					ApiService.apiSendGet(PlayersApiConstants.PLAYERS_FIND_ALL).then(function(data){
    						$scope.loading = false;
    						PlayerService.setPlayers(data);
    						$scope.players = PlayerService.sortAndFilter($scope.filterParams, $scope.sortParam, $scope.favorite && $rootScope.user);
    			    	}, function(err){
    			    		$scope.loading = false;
    			    		console.log(err);
    			    	});					
    				} else{
    					ApiService.apiSendGetNoAuth(PlayersApiConstants.PLAYERS_FIND_ALL).then(function(data){
    						$scope.loading = false;
    						PlayerService.setPlayers(data);
    						$scope.players = PlayerService.sortAndFilter($scope.filterParams, $scope.sortParam, $scope.favorite && $rootScope.user);
    			    	}, function(err){
    			    		$scope.loading = false;
    			    		console.log(err);
    			    	});
    				}
    			} else{
    				$scope.loading = false;
					$scope.players = PlayerService.sortAndFilter($scope.filterParams, $scope.sortParam, $scope.favorite && $rootScope.user);
    			}
    		};
        	
        	$scope.sort = function(sortParam){
        		$scope.sortParam.value = sortParam;
        		$scope.sortParam.direction = $scope.sortParam.direction * -1; 
        		$scope.loadPlayers();
        	};
    		
    		$scope.deleteNote = function(player){
    			ApiService.apiSendPost('api/notes/delete', player.notes).then(function(){
    				console.log('deleted');
    				player.notes = {};
    				$scope.showMessage('danger', '', 'Player notes removed');
    			});
    		};
    		
    		$scope.openNotesModal = function(player){
    			
    			var input = {
    				id : player.id,
    				name : player.name,
    				notes : {
    					id : player.notes.id,
    					notes : player.notes.notes,
    					username : player.notes.username,
    					grade : player.notes.grade,
    					player : player.notes.player
    				}
    			}
    			
                //open notes modal
                var modalInstance = $uibModal.open({
                    templateUrl: 'app/modules/home/modals/playerNotesModal.html',
                    controller: 'PlayerNotesModalCtrl',
                    size: 'md',
                    resolve: {
                        player: function () {
                            return input;
                        }
                    }
                });

                //wait for the modal instance to resolve
                modalInstance.result.then(function (result) {

                	player.notes = result.notes;
                	
                    //save / update notes for the player
                	ApiService.apiSendPost('api/notes', player.notes).then(function (data) {
                    	if(!player.notes.id){
                    		player.notes.id = data;
                    	}
                    	$scope.showMessage('success', '', 'Notes saved for ' + player.name);
                    	console.log('success');
                    }, function (err) {
                    	$scope.showMessage('error', '', 'Something tragic happened & we couldn\'t save :-(');
                        console.log(err);
                    });
                });
    			
    		};
    		
    		$scope.displayNotes = function(notes){
    			if(notes){
    				return notes.substring(0,50) + (notes.length > 30 ? '...' : '');
    			}
    		};
    		
        	$scope.getClassByPlayerGrade = function(prefix, grade){
        		return PlayerService.getClassByPlayerGrade(prefix, grade);
        	};
        	
        	$scope.copyToClipboard = function(player){
        		var text = player.name;
        		if($rootScope.user && player.notes.notes){
        			text += '- ' + player.notes.notes;
        		}
        		ngClipboard.toClipboard(text);
        		toaster.pop('success', '', 'Player copied to clipboard');
        	}
        	
        	$scope.showMessage = function(type, message, title){
        		toaster.pop(type, message, title);
        	};
        	
        	$scope.isCurrentYearSelected = function(){
        		var currentYear = new Date().getFullYear();
        		return $scope.filterParams.years.filter(function(y){
        			return y.name == currentYear;
        		}).length > 0;
        	};
    		
    		var init = function(){
    			
    			$scope.favorite = false;
    			
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
    			
    			$rootScope.currentPage = "Home";
    			$scope.loadPlayers();
    			
            	$scope.yearOptions = ConfigurationService.getYears();
            	$scope.collegeOptions = ConfigurationService.getColleges();
            	$scope.conferenceOptions = ConfigurationService.getConferences();
            	$scope.positionOptions = ConfigurationService.getPositions();
            	$scope.positionCategoryOptions = ConfigurationService.getPositionCategories();
            	$scope.positionSideOfBallOptions = ConfigurationService.getPositionSidesOfBall();
            	
            	//flag determining whether to show the combine results row
            	$scope.combine = {
            		expanded : false
            	};
    		};
    		
    		init();
        	
        	
}]);