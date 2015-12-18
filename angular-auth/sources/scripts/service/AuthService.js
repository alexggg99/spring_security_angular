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

    self.changePassword = function (data, callback) {
        console.log(data);
        $http.post(auth_server_url + '/login/changePassword.json', data).then(function (res) {
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