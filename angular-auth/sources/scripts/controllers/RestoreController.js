app.controller('RestoreController', function ($scope, User, AuthService, $location) {
    $scope.vm = this;

    $scope.vm.restorePass = {};

    $scope.vm.changePassword = function () {
        //console.log($scope.vm.restorePass);
        AuthService.changePassword($scope.vm.restorePass, function (data) {
            if (!data.error) $location.url('/auth');
            else jQuery('#error.modal').modal('show');
        });
    };
});