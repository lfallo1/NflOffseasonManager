angular.module('nflDraftApp')
    .controller('EditDraftInfoModalCtrl', ["$rootScope", "$scope", "$uibModalInstance", "toaster", "data", "ConfigurationService", function ($rootScope, $scope, $uibModalInstance, toaster, data, ConfigurationService) {

        $scope.players = [];
        $scope.teams = [];
        $scope.rounds = [
            {id: 1, name: "Round 1"},
            {id: 2, name: "Round 2"},
            {id: 3, name: "Round 3"},
            {id: 4, name: "Round 4"},
            {id: 5, name: "Round 5"},
            {id: 6, name: "Round 6"},
            {id: 7, name: "Round 7"}
        ];

        $scope.init = function(){
            $scope.player = data.player;
            $scope.overall = data.overall;
            $scope.teams = ConfigurationService.getNflTeams();
            console.log('pause');
        };

        $scope.savePlayer = function(){
            if($scope.player.round <= 7 && $scope.player.round >= 1 && $scope.player.pick >= 1 && $scope.player.pick <= 50 && $scope.player.team.team){
                confirm();
                return;
            }
            toaster.pop('error', '', 'Please select a valid round, pick, and team');
        }

        var confirm = function(){
            $uibModalInstance.close($scope.player);
        };

        $scope.cancel = function(){
            $uibModalInstance.dismiss();
        };

        $scope.init();

    }]);