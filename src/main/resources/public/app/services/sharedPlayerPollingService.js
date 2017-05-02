
	angular.module('nflDraftApp').service('SharedPlayerPollingService', ['$rootScope', '$q', '$timeout', 'toaster', 'ApiService', function($rootScope, $q, $timeout, toaster, ApiService){
		
		var service = {};
		var sharedPlayers = [];
		var date = null;
		var running = false;
		
		$rootScope.$on('begin_polling', function(){
			service.beginPoll();
		});
		$rootScope.$on('stop_polling', function(){
			service.stopPoll();
		});
		
		//begin polling
		service.beginPoll = function(){
			
			//create a 1 sec delay to allow listeners to register for shared players received events
			$timeout(function(){
				sharedPlayers = [];
				//load all shared players that have already been viewed
				getSharedPlayers().then(function(){
					running = true;
					startPoll();
				});
			},1000);			
		};
		
		//turn off flag which will stop the poll after its current request
		service.stopPoll = function(){
			running = false;
			sharedPlayers = [];
		};
		
		service.getSharedPlayers = function(){
			return sharedPlayers;
		};
		
		var addSharedPlayers = function(data){
			for(var i = 0; i < data.length; i++){
				sharedPlayers.push(data[i]);
			}
			$rootScope.$emit('new_shared_players');
		}
		
		var getSharedPlayers = function(){
			var deferred = $q.defer();
			ApiService.apiSendPost('api/share/load', {fromDate: null, waitTimeSeconds: 0, hasViewed: true}).then(function(data){
				deferred.resolve();
				addSharedPlayers(data);
			}, function(err){
				console.log(err);
				deferred.reject();
			});	
			return deferred.promise;
		};
		
		//start the poll for new shared players
		var startPoll = function(date){
			if(running){
				ApiService.apiSendPost('api/share/load', {fromDate: date, waitTimeSeconds: 60, hasViewed: false}).then(function(data){
					displayResults(data);
//					startPoll(new Date());
					
					//for now, not gonna have the server sleep due to thread count issues
					$timeout(function(){
						startPoll(new Date())
					},5000);
					
				});	
			}
		};
		
		//show notification of new messages
		var displayResults = function(messages){
			if(messages.length > 0 && running){
				addSharedPlayers(messages);
				var body = '<div id="messages-toaster">';
				for(var i = 0; i < messages.length; i++){
					body += messages[i].usernameSender + ' shared ' + messages[i].player.name + ' ' + messages[i].player.collegeText + '<br/>';
				}
				body += '</div>'
				toaster.pop({
	                type: 'info',
	                title: 'You have new shared players (Click on this popup to dismiss)',
	                body: body,
	                timeout: 300000,
	                bodyOutputType: 'trustedHtml',
	                toasterId: 2
	            });
				
				//set shared players to having been viewed
				ApiService.apiSendPost('api/share/setviewed', messages).then(function(){
					console.log('hasViewed flag fipped');
				});
			}
		}

		return service;

	}]);
