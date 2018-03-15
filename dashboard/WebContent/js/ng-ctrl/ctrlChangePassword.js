tryncapp.controller('ctrlChangePassword', function($scope,$rootScope,$location,$cookieStore,$http){
	$scope.errMessageShow = false;
	$scope.errMessage = "";
	$scope.successMessage = "";
	
	//validate both are same
	$scope.validatePassword = function(){
		if($scope.password1 != $scope.password2){
			$scope.errMessageShow = true;
			$scope.errMessage = "Password and re-typed password are not matched";
		}else{
			$scope.errMessageShow = false;
			$scope.errMessage = "";
		}
	}
	
	//update
	$scope.updatePassword = function(){
		$rootScope.showLoading = true;
		$http({
				method 	: "PUT",
				url		: "rest/user/updatepassword",
				headers	: {
					   "Content-Type": "application/json; charset=utf-8"
				},
				responseType	: "json",
				data	: JSON.stringify({ password : $scope.password1 })
				
		}).then(function successCallback(response) {
			  $scope.successMessage="Successfully updated password";
			  $scope.password1="";
			  $scope.password2="";
			  angular.element('#password1').focus();
		  }, function errorCallback(response) {
			  $scope.errMessage = (response.data.error[0]);
			  $scope.errMessageShow = true;
		  });
		$rootScope.showLoading = false;
	}
	
});