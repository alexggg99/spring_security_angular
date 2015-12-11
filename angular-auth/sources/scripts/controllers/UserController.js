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