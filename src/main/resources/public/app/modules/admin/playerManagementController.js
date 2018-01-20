angular.module('nflDraftApp').controller('AdminPlayerManagementCtrl', ['$q', '$http', '$scope', '$rootScope', "$location", "$routeParams", "ApiService", "PlayerService", "ConfigurationService",
    function ($q, $http, $scope, $rootScope, $location, $routeParams, ApiService, PlayerService, ConfigurationService) {

        var init = function () {
            if ($routeParams.id) {
                ApiService.apiSendGet('api/players/' + $routeParams.id).then(function (player) {
                    $scope.player = player;
                }, function (err) {
                    $location.path('/admin/player');
                    $scope.loadNewPlayer();
                });
            } else {
                $scope.loadNewPlayer();
            }

        };

        $scope.loadNewPlayer = function () {
            $scope.player = {
                name: 'New Player',
                height: 72,
                weight: 200,
                college: $scope.collegeOptions[0],
                position: $scope.positionOptions[0],
                year: $scope.yearOptions[0]
            };
        }

        $scope.yearOptions = ConfigurationService.getYears();
        $scope.collegeOptions = ConfigurationService.getColleges();
        $scope.positionOptions = ConfigurationService.getPositions();
        
        init();

    }]);