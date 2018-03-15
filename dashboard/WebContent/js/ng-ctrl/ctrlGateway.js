tryncapp.controller('ctrlGateway', function($scope, $rootScope, $location,
		$http) {

	$scope.errMessageShow = false;
	$scope.errMessage = "";
	$scope.gateways = [];// gateway array

	// fetch gateway list
	$scope.loadGateways = function() {
		$rootScope.showLoading = true;
		$http({
			method : "GET",
			url : "rest/gateway",
			headers : {
				"Content-Type" : "application/json; charset=utf-8"
			},
			responseType : "json"
		}).then(function successCallback(response) {
			$scope.gateways = [];
			for (var i = 0; i < response.data.gateways[0].length; i++) {
				$scope.gateways.push(response.data.gateways[0][i]);
			}
		}, function errorCallback(response) {
			$scope.errMessage = (response.data.error[0]);
			$scope.errMessageShow = true;
		});
		$rootScope.showLoading = false;
	}

});