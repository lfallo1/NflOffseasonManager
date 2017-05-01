angular.module('nflDraftApp')
        .controller('SharePlayerModalCtrl', ["$rootScope", "$scope", "$uibModalInstance", "player", "LoginService", "ApiService", "toaster", function ($rootScope, $scope, $uibModalInstance, player, LoginService, ApiService, toaster) {

        	$scope.init = function(){
        		$scope.selectedFriends = {};
        		$scope.notes = player.notes.summary;
        		$scope.mediaUrl = '';
        		$scope.player = player;
        		ApiService.apiSendGet('api/share/friends').then(function(data){
        			$scope.friends = data;
        		})
        	};
        	
        	$scope.share = function(){
        		
        		var friends = [];
        		for(var k in $scope.selectedFriends){
        			if($scope.selectedFriends[k]){
        				friends.push(k);
        			}
        		}
        		
        		//if no friends, just return (server also checks this)
        		if(friends.length === 0){ return; }
        		
        		//create the payload to match the server side UserShareDto
        		//UserShareDto accepts a list of players, which is why we're adding the player as an array even though from this modal it will only ever be one
        		var payload = {
        			players: [{
        				playerId: $scope.player.id, 
        				notes: $scope.notes, 
        				mediaUrl: $scope.mediaUrl
        			}],
        			usernames: friends
        		}
        		
        		ApiService.apiSendPost('api/share', payload).then(function(data){
        			toaster.pop('success', '', 'Player has been shared');
        			$scope.close();
        		}, function(err){
        			toaster.pop('error', '', 'Unable to share player at this time');
        		});
        	}

        	$scope.close = function(){
        		$uibModalInstance.dismiss();
        	};

        	$scope.init();

        }]);
