(function () {

    angular.module('nflDraftApp').service('ConfigurationService', ['ApiService', '$q', '$rootScope', function (ApiService, $q, $rootScope) {

        var service = {};

        var years = [];
        var positions = [];
        var positionCategories = [];
        var positionSidesOfBall = [];
        var colleges = [];
        var conferences = [];
        var nflTeams = [];
        var tags = [];

        service.initConfig = function () {
            var deferred = $q.defer();
            if ($rootScope.configured) {
                deferred.resolve();
            } else {
                ApiService.apiSendGetNoAuth('api/config').then(function (data) {
                    years = data.years.map(function (d, idx) {
                        return {
                            id: idx,
                            name: d
                        }
                    });
                    positions = data.positions;
                    positionCategories = data.positionCategories;
                    positionSidesOfBall = data.positionSidesOfBall;
                    colleges = data.colleges;
                    conferences = data.conferences;
                    tags = data.tags;
                    nflTeams = data.nflTeams.map(function (t, i) {
                        return {
                            id: i,
                            name: t.team
                        }
                    });
                    $rootScope.configured = true;
                    deferred.resolve();
                });
            }
            return deferred.promise;
        };

        service.getYears = function () {
            return years;
        };

        service.getPositions = function () {
            return positions;
        };

        service.getPositionCategories = function () {
            return positionCategories;
        };

        service.getPositionSidesOfBall = function () {
            return positionSidesOfBall;
        };

        service.getColleges = function () {
            return colleges;
        };

        service.getConferences = function () {
            return conferences;
        };

        service.getTags = function () {
            return tags;
        };

        service.getNflTeams = function () {
            return nflTeams;
        };

        service.smartButtonSettings = {
            displayProp: 'name',
            externalIdProp: '',
            smartButtonMaxItems: 3,
            smartButtonTextConverter: function (itemText, originalItem) {
                return itemText;
            },
            enableSearch: true,
            searchField: 'name'
        };

        return service;

    }]);

})();
