angular.module('nflDraftApp').controller('AdminDashboardCtrl', ['$q', '$http', '$scope', '$rootScope', "$location", "toaster", "ApiService", "PlayerService",
    function ($q, $http, $scope, $rootScope, $location, toaster, ApiService, PlayerService) {

        var init = function () {
            $scope.title = 'Admin Dashboard'
            pollActiveImports();
            ApiService.apiSendGet('api/players/refresh-latest').then(function (data) {
                $scope.latestImport = data;
            });
        }

        var pollActiveImports = function () {
            ApiService.apiSendGet('api/players/refresh-active').then(function (data) {
                if (data) {
                    $scope.importInProgress = true;
                    $scope.importProgress = data;
                    setTimeout(function () {
                        if ($scope.importInProgress) {
                            pollActiveImports();
                        }
                    }, 5000);
                    return;
                }
                if ($scope.importInProgress) {
                    $scope.importInProgress = false;
                    $scope.importProgress = null;
                    toaster.pop('success', '', 'Import complete!');
                }
                $scope.importInProgress = false;
            })
        }

        $scope.refresh = function (source) {
            if (!$scope.importInProgress) {
                ApiService.apiSendGet('api/players/refresh/' + source).then(function () {
                    toaster.pop('success', '', 'Import Started!');
                    $scope.importInProgress = true;
                    setTimeout(function () {
                        pollActiveImports()
                    }, 3000);
                }, function (err) {
                    toaster.pop('error', '', 'There is an import already in progress');
                })
            }
        }

        $scope.addPlayer = function () {
            $location.path('/admin/player');
        }

        init();

    }]);