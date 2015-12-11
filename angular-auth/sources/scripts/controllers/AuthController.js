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
            console.log(res);
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