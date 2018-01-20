angular.module('nflDraftApp').controller('AdminPlayerManagementCtrl', ['$q', '$http', '$scope', '$rootScope', "$location", "$routeParams", "toaster", "ApiService", "PlayerService", "ConfigurationService",
    function ($q, $http, $scope, $rootScope, $location, $routeParams, toaster, ApiService, PlayerService, ConfigurationService) {

        var init = function () {
            if ($routeParams.id) {
                ApiService.apiSendGet('api/players/' + $routeParams.id).then(function (player) {
                    $scope.player = player;

                    //for autocomplete inputs
                    $scope.initialCollege = $scope.player.college.name;
                    $scope.initialPosition = $scope.player.position.name;
                    $scope.initialYear = $scope.player.year.toString();
                }, function (err) {
                    $location.path('/admin/player');
                    $scope.loadNewPlayer();
                });
            } else {
                $scope.loadNewPlayer();
            }

            $scope.heightSlider = {
                options: {
                    id: 'heightSlider',
                    floor: 60,
                    ceil: 96,
                    translate: function (value, sliderId, label) {
                        if (sliderId == 'heightSlider') {
                            return Math.floor(Number(value) / 12) + "'" + Number(value) % 12;
                        }
                    }
                }
            }

        };

        $scope.handleSelectCollege = function (data) {
            if (data) {
                $scope.player.college = data.originalObject;
            }
        };

        $scope.handleSelectYear = function (data) {
            if (data) {
                $scope.player.year = data.originalObject;
            }
        };

        $scope.handleSelectPosition = function (data) {
            if (data) {
                $scope.player.position = data.originalObject;
            }
        };

        $scope.submit = function () {
            var payload = $scope.player;
            payload.year = isNaN($scope.player.year) ? $scope.player.year.name : $scope.player.year;
            payload.collegeText = payload.college.name;
            if ($scope.validate(payload)) {
                if (payload.id) {
                    ApiService.apiSendPut('api/players/' + $scope.player.id, payload).then(function (data) {
                        toaster.pop('success', '', 'Player updated!');
                        $location.path('/')
                    }, savePlayerErrorCallback);
                } else {
                    ApiService.apiSendPost('api/players', payload).then(function (data) {
                        toaster.pop('success', '', 'Player added!');
                        $location.path('/')
                    }, savePlayerErrorCallback);
                }
            } else {
                toaster.pop('error', '', 'There are errors on the form, please ensure all fields are populated.');
            }
        };

        var savePlayerErrorCallback = function (err) {
            if (err.data && err.data.message && err.data.message.toLowerCase().indexOf('duplicate') > -1) {
                toaster.pop('error', '', 'This player already exists');
                return;
            }
            toaster.pop('error', '', 'There was a problem saving this player!');
        }

        $scope.validate = function (player) {
            var validationResult = {valid: false, details: []};
            return player.name && player.height && player.weight && player.college.id &&
                player.position.id && player.year && $scope.player.positionRank > 0
        }

        $scope.loadNewPlayer = function () {
            $scope.player = {
                id: null,
                name: 'New Player',
                height: 72,
                weight: 200,
                college: $scope.collegeOptions[0],
                position: $scope.positionOptions[0],
                year: {name: new Date().getFullYear()},
                positionRank: 35
            };

            //for autocomplete inputs
            $scope.initialCollege = $scope.collegeOptions[0].name;
            $scope.initialPosition = $scope.positionOptions[0].name;
            $scope.initialYear = new Date().getFullYear().toString();
        }

        $scope.yearOptions = ConfigurationService.getYears();
        $scope.collegeOptions = ConfigurationService.getColleges();
        $scope.positionOptions = ConfigurationService.getPositions();

        init();

    }]);