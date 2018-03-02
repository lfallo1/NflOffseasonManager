angular.module('nflDraftApp').controller('AdminDashboardCtrl', ['$q', '$http', '$scope', '$rootScope', "$location", "$stomp", "toaster", "$log", "ApiService", "PlayerService",
    function ($q, $http, $scope, $rootScope, $location, $stomp, toaster, $log, ApiService, PlayerService) {

        $stomp.setDebug(function (args) {
            $log.debug(args)
        })

        var init = function () {
            $scope.title = 'Admin Dashboard'

            findLastImport();

            //check if an import is running when the controller first loads
            pollActiveImports();
        };

        /**
         * starts a refresh / opens socket to listen for progress updates on the import
         * @param source
         * @param running
         */
        $scope.refresh = function (source, running) {

            //connect to the websocket.
            //including access_token as query param so that user principal gets recognized (websocket does not support auth headers, so this needs to be a query param)
            $stomp.connect(window.location.origin + window.location.pathname + 'shared?access_token=' + JSON.parse(localStorage.getItem('authorization')).access_token)
                .then(function (frame) {

                    //subscribe to the channel to which import updates will be sent
                    $scope.importSubscription = $stomp.subscribe('/topic/import', function (payload) {
                        $scope.importProgress = payload;
                        $scope.$apply(); //necessary since angular does not recognize updates in the websocket callback

                        //if the payload contains a finished value, the import has completed
                        if ($scope.importProgress.finished) {

                            //check if import finished successfully
                            if ($scope.importProgress.progress == 100) {
                                toaster.pop('success', '', 'Import complete!');
                                $scope.latestImport = $scope.importProgress; //update the latest import if successful
                                $scope.$apply();
                            } else {
                                toaster.pop('error', '', 'Import failed');
                            }

                            //clear the importProgress section after one second (looks better to have the final progress show for a bit before hiding)
                            setTimeout(function () {
                                $scope.importProgress = false;
                                $scope.importInProgress = false;
                                $scope.$apply();
                            }, 1000);

                            //close the socket
                            closeImportSocket();
                        }
                    });

                    //if not already running, then invoke the actual refresh.
                    //otherwise, we called this method simply to open a websocket and listen for updates.
                    if (!running) {
                        startRefresh(source);
                    }
                })
        };

        $scope.addPlayer = function () {
            $location.path('/admin/player');
        };

        /**
         * hit the application's endpoint, which will send a message triggering a data refresh
         * @param source
         */
        var startRefresh = function (source) {
            ApiService.apiSendGet('api/players/refresh/' + source).then(function () {
                $scope.importInProgress = true;
                toaster.pop('success', '', 'Import Started!');
            }, function (err) {

                //if the call fails, then immediately close the socket
                closeImportSocket();
                $scope.importProgress = false;
                $scope.importInProgress = false;
                toaster.pop('error', '', 'There is an import already in progress');

            })
        };

        /**
         * check if any imports are running
         */
        var pollActiveImports = function () {
            ApiService.apiSendGet('api/players/refresh-active').then(function (data) {
                if (data) {
                    //if there is an active import, then set the flags / properties & call
                    //the 'refresh' method with 'running' parameter set to true (so it won't attempt to start a new refresh)
                    $scope.importInProgress = true;
                    $scope.importProgress = data;
                    $scope.refresh(-1, true);
                }
            })
        };

        /**
         * unsubscribe and close socket
         */
        var closeImportSocket = function () {
            if ($scope.importSubscription) {
                // Unsubscribe
                $scope.importSubscription.unsubscribe()
            }
            $stomp.disconnect().then(function () {
                $log.info('disconnected')
            });
        };

        /**
         * find the last import / data refresh
         */
        var findLastImport = function () {
            ApiService.apiSendGet('api/players/refresh-latest').then(function (data) {
                $scope.latestImport = data;
            });
        }

        init();

    }]);