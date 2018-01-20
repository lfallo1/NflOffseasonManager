(function () {

    angular.module('nflDraftApp').service('LoginService', ['PlayerService', '$rootScope', '$route', '$location', '$q', function (PlayerService, $rootScope, $route, $location, $q) {

        var service = {};

        service.setToken = function (res) {
            localStorage.setItem("authorization", JSON.stringify(res.data));
            $rootScope.authentication = true;
            service.setUser(res.data.user_details);
        };

        service.setUser = function (userDetails) {
            $rootScope.user = userDetails;
            $rootScope.$emit('begin_polling');
        };

        service.clearUser = function () {
            $rootScope.$emit('stop_polling');
            PlayerService.clear();
            $rootScope.user = undefined;
            $rootScope.authentication = false;
            localStorage.removeItem('authorization');
            if ($location.path() == '/') {
                $route.reload();
            } else {
                $location.path('/');
            }
        };

        service.rejectIfLoggedIn = function () {
            var deferred = $q.defer();
            if ($rootScope.user) {
                deferred.reject();
                $location.path('/');
            } else {
                deferred.resolve();
            }
            return deferred.promise;
        };

        service.hasRole = function (roles) {
            var deferred = $q.defer();

            //if any of the user's roles are found in the list of acceptable roles
            var hasRole = $rootScope.user && $rootScope.user.authorities && $rootScope.user.authorities.filter(function (r) {
                return roles.indexOf(r.authority) > -1;
            }).length > 0;

            if (!hasRole) {
                deferred.reject();
                $location.path('/');
            } else {
                deferred.resolve();
            }
            return deferred.promise;
        };

        return service;

    }]);

})();