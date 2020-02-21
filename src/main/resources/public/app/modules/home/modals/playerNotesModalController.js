angular.module('nflDraftApp')
    .controller('PlayerNotesModalCtrl', ["$rootScope", "$scope", "$uibModal", "$uibModalInstance", "$timeout", '$http', "player", 'PlayerService', 'ConfigurationService', function ($rootScope, $scope, $uibModal, $uibModalInstance, $timeout, $http, player, PlayerService, ConfigurationService) {

        $scope.init = function () {

            $scope.projectedRoundOptions = PlayerService.getProjectedRoundOptions();
            $scope.likenessOptions = PlayerService.getLikenessOptions();
            $scope.tags = ConfigurationService.getTags();
            $scope.videos = [];
            $scope.youtubePlayerOptions = {
                autoplay : 1
            };
            $scope.activeVideo = null;

            $scope.player = player;
            $scope.playerDetails = player.details.name;
            if (player.details.position.name && player.details.collegeText) {
                $scope.playerDetails += ' (' + player.details.position.name + ' - ' + player.details.collegeText + ')';
            }

            if (!$scope.player.notes.id) {
                $scope.player.notes = {
                    player: {id: $scope.player.id},
                    username: $rootScope.user.username,
                    summary: '',
                    strengths: '',
                    weaknesses: '',
                    likeness: 3,
                    projectedRound: 2,
                    overallGrade: 75,
                    tags: []
                }
            }
            $scope.sliderVisible = true;
            refreshSlider();
            searchHighlights();
        };

        var refreshSlider = function () {
            $timeout(function () {
                $scope.$broadcast('rzSliderForceRender');
            });
        }

        $scope.isValidGrade = function (overallGrade) {
            return PlayerService.isValidGrade(overallGrade);
        };

        $scope.getColorByPlayerGrade = function (overallGrade) {
            if ($scope.isValidGrade(overallGrade)) {
                var hue = Math.max(overallGrade * (1.25) - (100 - overallGrade) * .67, 0);
                return 'hsl(' + hue + ', 58%, 50%)';
            }
            return 'hsl(0, 58%, 50%)';
        };

        //if the grade is invalid, the display value will be 100 for sake of the progress bar
        $scope.sanitizedGradeValue = function (overallGrade) {
            return $scope.isValidGrade(overallGrade) ? overallGrade : 100;
        };

        $scope.submit = function () {
            if ($scope.isValidGrade($scope.player.notes.overallGrade)) {
                $uibModalInstance.close($scope.player);
            }
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss();
        };

        $scope.delete = function () {
            $uibModalInstance.close({
                action: 'DELETE_NOTE'
            });
        };

        $scope.share = function () {
            $uibModal.open({
                templateUrl: 'app/modules/home/modals/sharePlayerModal.html',
                controller: 'SharePlayerModalCtrl',
                size: 'lg',
                resolve: {
                    player: function () {
                        return $scope.player;
                    }
                }
            }).result.then(function (result) {
                //TODO
            });
        };

        //toggle a tag for the note
        $scope.toggleTag = function (tag) {
            if ($scope.hasTag(tag)) {
                var idx = $scope.player.notes.tags.map(function (t) {
                    return t.id
                }).indexOf(tag.id);
                $scope.player.notes.tags.splice(idx, 1);
            } else {
                $scope.player.notes.tags.push(tag);
            }
        };

        //return whether or not the player note has the passed in tag
        $scope.hasTag = function (tag) {
            return $scope.player.notes.tags.map(function (t) {
                return t.id
            }).indexOf(tag.id) > -1;
        };

        var searchHighlights = function(){
            $http.get('api/youtube/search?q=' + encodeURI($scope.player.details.name + ' vs')).then(function(res){
               $scope.videos = res.data;
            }, function(err){
                console.log(err);
            });
        };

        $scope.selectVideo = function(video){
          $scope.activeVideo = video;
        };

        $scope.videoUrl = function(){
            if($scope.activeVideo){
                return 'https://www.youtube.com/embed/' + $scope.activeVideo.id;
            }
            return '';
        }

        $scope.init();

    }]);
