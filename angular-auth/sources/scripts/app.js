var app = angular.module('app', ['ngResource', 'ngRoute']);

app.config(function ($httpProvider, $routeProvider, $locationProvider) {
    $routeProvider.otherwise({redirectTo: '/'})
        .when('/user', {
            controller: 'UserController',
            templateUrl: 'tpl/user.html'
        })
        .when('/auth', {
            controller: 'AuthController',
            templateUrl: 'tpl/auth.html'
        })
        .when('/restore', {
            controller: 'RestoreController',
            templateUrl: 'tpl/restore.html'
        });

    $httpProvider.defaults.withCredentials = true;
    $locationProvider.html5Mode(true).hashPrefix('!');
});


app.run(function ($location, AuthService) {
    AuthService.check(function (data) {
        if (data.error) {
            if($location.path() == '/restore'){
                $location.url('/restore');
            } else {
                $location.url('/auth');
            }
        }
        else{
            if ($location.path() == '/auth')
                $location.url('/user')
        }
    });
});