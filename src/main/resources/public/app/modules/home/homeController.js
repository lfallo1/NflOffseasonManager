angular.module("nflDraftApp")
    .controller("HomeCtrl", ["$rootScope", "$scope", "PlayerService", "ConfigurationService", "ApiService", "$uibModal", "PlayersApiConstants", "toaster", "ngClipboard", 'SharedPlayerPollingService', function ($rootScope, $scope, PlayerService, ConfigurationService, ApiService, $uibModal, PlayersApiConstants, toaster, ngClipboard, SharedPlayerPollingService) {

        $rootScope.$on('new_shared_players', function () {
            $scope.sharedPlayers = SharedPlayerPollingService.getSharedPlayers();
        });

        $scope.loadPlayers = function () {
            $scope.loading = true;
            var players = PlayerService.getPlayers();
            if (players.length == 0) {
                if ($rootScope.user) {
                    ApiService.apiSendGet(PlayersApiConstants.PLAYERS_FIND_ALL).then(function (data) {
                        $scope.loading = false;
                        PlayerService.setPlayers(data);
                        $scope.players = PlayerService.sortAndFilter($scope.filterParams, $scope.sortParam, $scope.favorite && $rootScope.user, $scope.availableOnly);
                        $scope.players.forEach(setExpanded)
                    }, function (err) {
                        $scope.loading = false;
                        console.log(err);
                    });
                } else {
                    ApiService.apiSendGetNoAuth(PlayersApiConstants.PLAYERS_FIND_ALL).then(function (data) {
                        $scope.loading = false;
                        PlayerService.setPlayers(data);
                        $scope.players = PlayerService.sortAndFilter($scope.filterParams, $scope.sortParam, $scope.favorite && $rootScope.user, $scope.availableOnly);
                        $scope.players.forEach(setExpanded)
                    }, function (err) {
                        $scope.loading = false;
                        console.log(err);
                    });
                }
            } else {
                $scope.loading = false;
                $scope.players = PlayerService.sortAndFilter($scope.filterParams, $scope.sortParam, $scope.favorite && $rootScope.user, $scope.availableOnly);
                $scope.players.forEach(setExpanded)
            }
        };

        $scope.sort = function (sortParam) {
            $scope.sortParam.value = sortParam;
            $scope.sortParam.direction = $scope.sortParam.direction * -1;
            $scope.loadPlayers();
        };

        $scope.prevPage = function () {
            $scope.players = PlayerService.gotoPage(PlayerService.pagination.currentPage - 1);
            $scope.players.forEach(setExpanded)
        };

        $scope.nextPage = function () {
            $scope.players = PlayerService.gotoPage(PlayerService.pagination.currentPage + 1);
            $scope.players.forEach(setExpanded)
        };

        $scope.gotoPage = function (pg) {
            $scope.players = PlayerService.gotoPage(pg);
            $scope.players.forEach(setExpanded)
        };

        var setExpanded = function (player) {
            player.expanded = $scope.expandAll;
        };

        $scope.getCurrentPageNumber = function () {
            return PlayerService.pagination.currentPage;
        };

        //helper to return number of pages for a dropdown
        $scope.getPageNumbers = function () {
            var dummy = [];
            for (var i = 0; i < PlayerService.pagination.totalPages; i++) {
                dummy.push(i + 1);
            }
            return dummy;
        };

        $scope.toggleExpandAll = function () {
            $scope.expandAll = !$scope.expandAll;
            for (var i = 0; i < $scope.players.length; i++) {
                $scope.players[i].expanded = $scope.expandAll;
            }
        };

        $scope.deleteNote = function (player) {

            $uibModal.open({
                templateUrl: 'app/modules/home/modals/confirmModal.html',
                controller: 'ConfirmModalCtrl',
                size: 'sm',
                resolve: {
                    data: function () {
                        return {
                            title: 'Confirm Note Deletion',
                            message: 'Are you sure you want delete note for ' + player.name + '?'
                        }
                    }
                }
            }).result.then(function () {
                ApiService.apiSendPost('api/notes/delete', player.notes).then(function () {
                    console.log('deleted');
                    player.notes = {};
                    $scope.showMessage('danger', '', 'Player notes removed');
                });
            });

        };

        $scope.openNotesModal = function (player) {

            var input = {
                id: player.id,
                details: {
                    height: player.height,
                    weight: player.weight,
                    fortyYardDash: player.fortyYardDash,
                    position: player.position,
                    name: player.name,
                    collegeText: player.collegeText
                },
                notes: {
                    id: player.notes.id,
                    summary: player.notes.summary,
                    strengths: player.notes.strengths,
                    weaknesses: player.notes.weaknesses,
                    likeness: player.notes.likeness,
                    projectedRound: player.notes.projectedRound,
                    overallGrade: player.notes.overallGrade,
                    username: player.notes.username,
                    player: player.notes.player,
                    tags: player.notes.tags
                }
            }

            //open notes modal
            var modalInstance = $uibModal.open({
                templateUrl: 'app/modules/home/modals/playerNotesModal.html',
                controller: 'PlayerNotesModalCtrl',
                windowClass: 'player-notes-modal-window',
                backdrop: 'static',
                // size: 'lg',
                resolve: {
                    player: function () {
                        return input;
                    }
                }
            });

            //wait for the modal instance to resolve
            modalInstance.result.then(function (result) {
                if (result.action && result.action === 'DELETE_NOTE') {
                    $scope.deleteNote(player);
                } else {
                    player.notes = result.notes;

                    //save / update notes for the player
                    ApiService.apiSendPost('api/notes', player.notes).then(function (data) {

                        if (!player.notes.id) {
                            player.notes.id = data;
                        }
                        $scope.showMessage('success', '', 'Notes saved for ' + player.name);
                        console.log('success');

                    }, function (err) {
                        $scope.showMessage('error', '', 'Something tragic happened & we couldn\'t save :-(');
                        console.log(err);
                    });
                }
            });

        };

        $scope.getClassByPlayerGrade = function (prefix, grade) {
            return PlayerService.getClassByPlayerGrade(prefix, grade);
        };

//      	$scope.copyToClipboard = function(player){
//      		var text = player.name;
//      		if($rootScope.user && player.notes.notes){
//      			text += '- ' + player.notes.notes;
//      		}
//      		ngClipboard.toClipboard(text);
//      		toaster.pop('success', '', 'Player copied to clipboard');
//      	}

        $scope.showMessage = function (type, message, title) {
            toaster.pop(type, message, title);
        };

        //gonna just hard code this. once next years combine starts, i'll change the year.
        $scope.isCurrentYearSelected = function () {
//      		var currentYear = new Date().getFullYear();
            return $scope.filterParams.years.filter(function (y) {
                return y.name <= $scope._currentYear;
            }).length > 0;
        };

        $scope.colspanSize = function () {
            var draftedByColSpan = $scope.isCurrentYearSelected() ? 1 : 0;
            return draftedByColSpan + $scope.user ? 11 : 8;
        }

        var logoPlaceholder = 'http://i.nflcdn.com/static/site/7.5/img/teams/{TEAM}/{TEAM}_logo-80x90.gif';
        $scope.getNflTeamLogo = function (player) {
            return (player && player.team && player.team.team) ? logoPlaceholder.replace(/{TEAM}/g, player.team.team) : '';
        };

        $scope.getProjectedRoundText = function (notes) {
            if (notes && notes.id) {
                return PlayerService.getProjectedRoundOptions().filter(function (d) {
                    return d.round == notes.projectedRound;
                })[0].text + ' rd';
            }
            return '';
        };

        $scope.getLikenessGlyphicon = function (notes) {
            if (!notes || !notes.id) {
                return '';
            }
            var base = 'glyphicon glyphicon-';
            if (notes.likeness > 3) {
                return base += 'thumbs-up text-success';
            } else if (notes.likeness < 3) {
                return base += 'thumbs-down text-danger';
            }
            return base += 'hand-left text-warning';
        };

        //given a number b/w 0 and 100, get the color value
        $scope.calculateBgColor = function (overallGrade) {
            return 'hsl(' + overallGrade * (1.25) + ', 58%, 50%)';
        };

        //reload the shared players
        $scope.reloadSharedPlayers = function (option) {
            $scope.selectedDuration = option.val; //set this so the html page has a flag to determine active class
            var duration = $scope.selectedDuration * 1000 * 60 * 60 * 24;
            var date = new Date().getTime() - duration;
            SharedPlayerPollingService.reloadSharedPlayers(date);
        };

        $scope.editDraftInformation = function (player) {
            //open notes modal
            var nextPick = PlayerService.nextPick(player.year);
            $uibModal.open({
                templateUrl: 'app/modules/home/modals/editDraftInfoModal.html',
                controller: 'EditDraftInfoModalCtrl',
                size: 'sm',
                resolve: {
                    data: function () {
                        return {
                            player: {
                                id: player.id,
                                name: player.name,
                                team: {team: player.team.team || $scope.nflTeamOptions[0].name},
                                pick: nextPick.pick,
                                round: nextPick.round,
                                year: player.year
                            },
                            overall: nextPick.overall
                        };
                    }
                }
            }).result.then(function (result) {

                ApiService.apiSendPost(`api/players/draft/${result.id}`, {
                    id: player.id,
                    team: result.team,
                    pick: result.pick,
                    round: result.round,
                    year: player.year
                }).then(function (data) {

                    $scope.showMessage('success', '', 'Draft info saved for ' + player.name);

                    //do a clean refresh to reload players with adjusted draft info
                    PlayerService.setPlayers([]);
                    $scope.players = [];
                    $scope.loadPlayers();

                }, function (err) {
                    $scope.showMessage('error', '', 'Something tragic happened & we couldn\'t save :-(');
                    console.log(err);
                });

            });
        };

        // var startDraftListener = function(){
        //     if($rootScope.user) {
        //         $stomp.connect(window.location.origin + window.location.pathname + 'shared?access_token=' + JSON.parse(localStorage.getItem('authorization')).access_token)
        //             .then(function (frame) {
        //
        //                 //subscribe to the channel to which import updates will be sent
        //                 $scope.draftSubscription = $stomp.subscribe('/topic/draft', function (payload) {
        //                     PlayerService.refreshDraft(payload);
        //                 });
        //             })
        //     }
        // }

        var init = function () {
            $scope.selectedDurationOptions = [{val: 30, text: '30'}, {val: 90, text: '90'}, {val: 1000, text: 'All'}];
            $scope.selectedDuration = $scope.selectedDurationOptions[1].val;
            $scope.sharedPlayers = [];
            $scope.favorite = false;
            $scope.availableOnly = false;

            $scope.yearOptions = [];
            $scope.collegeOptions = [];
            $scope.conferenceOptions = [];
            $scope.positionOptions = [];
            $scope.positionCategoryOptions = [];
            $scope.positionSideOfBallOptions = [];
            $scope.nflTeamOptions = [];

            var appDate = new Date();
            $scope._currentYear = appDate.getMonth() > 5 ? appDate.getFullYear() + 1 : appDate.getFullYear();

            $scope.filterParams = {
                years: [{id: $scope._currentYear - 2017, name: $scope._currentYear}],
                positions: [],
                positionCategories: [],
                positionSidesOfBall: [{"id": 1, "name": "offense"}, {"id": 2, "name": "defense"}],
                conferences: [],
                colleges: [],
                nflTeams: [],
                name: ""
            };

            $scope.smartButtonSettings = ConfigurationService.smartButtonSettings;

            $scope.onMultiSelectEvents = {
                onItemSelect: function () {
                    $scope.loadPlayers();
                },
                onItemDeselect: function () {
                    $scope.loadPlayers();
                }
            };

            $scope.sortParam = {
                value: 'positionRank',
                direction: 1
            }

            $rootScope.currentPage = "Home";
            $scope.loadPlayers();

            $scope.yearOptions = ConfigurationService.getYears();
            $scope.collegeOptions = ConfigurationService.getColleges();
            $scope.conferenceOptions = ConfigurationService.getConferences();
            $scope.positionOptions = ConfigurationService.getPositions();
            $scope.positionCategoryOptions = ConfigurationService.getPositionCategories();
            $scope.positionSideOfBallOptions = ConfigurationService.getPositionSidesOfBall();
            $scope.nflTeamOptions = ConfigurationService.getNflTeams();

            //flag determining whether to show the combine results row
            $scope.expandAll = false;
        };

        init();


    }]);
