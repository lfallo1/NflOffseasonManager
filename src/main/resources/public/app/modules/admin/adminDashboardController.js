angular.module('nflDraftApp').controller('AdminDashboardCtrl', ['$q', '$http', '$scope', '$rootScope', "$location", "ApiService", "PlayerService",
    function ($q, $http, $scope, $rootScope, $location, ApiService, PlayerService) {

        var init = function () {
            $scope.title = 'Admin Dashboard'
        }

        $scope.refresh = function (source) {
            console.log('call service to reload data from ' + source);
        }

        $scope.addPlayer = function () {
            $location.path('/admin/player');
        }

        init();

    }]);