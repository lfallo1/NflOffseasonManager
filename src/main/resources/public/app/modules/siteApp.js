var siteModule = angular.module('siteModule', ['ui.router']);

siteModule.config(function ($stateProvider, $urlRouterProvider, $controllerProvider) {
	
	$controllerProvider.register('StatusCtrl', function ($scope, $timeout) {
		var self = this;
        self.vals = [{value: 'Once upon a time', style:'success'},
        	{value: 'Dogs and cats dont feed the turtle', style:'primary'},
        	{value: 'Dont touch the potato!', style:'warning'}];

	    self.statusClick = function (val) {
	    	self.msg = val;
	        $timeout(function(){
	        	self.msg = undefined;
	        },5000);
	    };
	});
	
    $stateProvider
        .state('site.home', { //child of site view, has two named views
        	url : '/home',        	
        	views : {
        		'' : {
        			templateUrl : 'app/modules/home/home.html',
                	controller: 'HomeCtrl'
        		},
                
                'filmtable@site.home' : {
        			templateUrl : 'app/shared/filmStatusTable/filmStatusTable.html',
                	controller: 'FilmStatusTableController'        			
        		}
        	}
        })
        .state('site.login', {
            url : '/login',
        	templateUrl : 'app/modules/login/login.html',
        	controller: 'LoginCtrl'
        })
        .state('site.test', {
        	url : '/test',
        	template : '<h3>Just a test</h3>'
        })
});
    