app.factory('User', function ($resource) {
    return $resource('_data/user.json');
});