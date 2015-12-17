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
        });

    $httpProvider.defaults.withCredentials = true;
    $locationProvider.html5Mode(true).hashPrefix('!');
});


app.run(function ($location, AuthService) {
    AuthService.check(function (data) {
        if (data.error) $location.url('/auth');
        else if ($location.path() == '/auth') $location.url('/user')
    });
});
app.factory('User', function ($resource) {
    return $resource('_data/user.json');
});
app.service('AuthService', function ($http) {
    var self = this;
    var auth_server_url = 'http://localhost:8055';

    self.login = function (data, callback) {
        console.log(data);
        //callback({error: false});
        $http.post(auth_server_url + '/login/login.json', data).then(function (res) {
            callback(res.data);
        });
    };

   self.loginVK = function (data, callback) {
        $http.post(auth_server_url + '/login/login.vk', data).then(function (res) {
            callback(res.data);
        });
    };

    self.logout = function (callback) {
        //callback({error: true});
        $http.get(auth_server_url + '/login/logout.json').then(function (res) {
            callback(res.data);
        });
    };

    self.restore = function (data, callback) {
        console.log(data);
        $http.post(auth_server_url + '/login/restore.json', data).then(function (res) {
            callback(res.data);
        });
    };

    self.check = function (callback) {
        //callback({error: true});
        $http.get(auth_server_url + '/login/check').then(function (res) {
            callback(res.data);
        });
    };

    self.register = function (data, callback) {
        console.log(data);
        //callback({error: false});
        $http.post(auth_server_url + '/login/register.json', data).then(function (res) {
            callback(res.data);
        });
    };
});
app.controller('AuthController', function ($scope, AuthService) {
    $scope.vm = this;

    $scope.vm.user = {};

    $scope.vm.login = function () {
        AuthService.login($scope.vm.user, function (data) {
            if (!data.error) location.reload();
            else jQuery('#error.modal').modal('show');
        });
    };

    $scope.vm.loginVK = function () {
        VK.init({apiId: 5178375});
        VK.Auth.login(function (res) {
            //console.log(res);
            if (res.status) {
                AuthService.loginVK(res.session.user, function (data) {
                    if (!data.error) location.reload();
                    else jQuery('#error.modal').modal('show');
                });
            } else jQuery('#error.modal').modal('show');
        });
    };

    $scope.vm.register = function () {
        AuthService.register($scope.vm.user, function (data) {
            if (!data.error) location.reload();
            else jQuery('#error.modal').modal('show');
        });
    };

    $scope.vm.restore = function () {
        AuthService.restore($scope.vm.user, function (data) {
            if (!data.error) jQuery('#restore.modal').modal('show');
            else jQuery('#error.modal').modal('show');
        });
    };
});
app.controller('UserController', function ($scope, User, AuthService) {
    $scope.vm = this;

    $scope.vm.user = User.get();

    $scope.vm.logout = function () {
        AuthService.logout(function (data) {
            if (!data.error) location.reload();
            else jQuery('#error.modal').modal('show');
        });
    };
});