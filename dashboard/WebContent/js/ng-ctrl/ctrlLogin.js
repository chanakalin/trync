tryncapp.controller('ctrlLogin', function($scope,$rootScope,$location,$http){
	
	$scope.errMessageShow = false;
	$scope.errMessage = "";
	
	//focus to password field
	$scope.focusToPassword = function(){
		 angular.element('#password').focus();
	}
	
	//check login on page init
	$scope.checkLogged = function(){
		
	}
	
	
	//validate new login
	$scope.validateLogin = function(){
		$rootScope.showLoading = true;
		$http({
				method 	: "POST",
				url		: "rest/user/login",
				headers	: {
					   "Content-Type": "application/json; charset=utf-8"
				},
				responseType	: "json",
				data	: JSON.stringify({ email : $scope.email ,password : $scope.password })
				
		}).then(function successCallback(response) {
			$rootScope.loggedUserId = response.data.uid[0];
			$rootScope.loggedUserGroup = response.data.gid[0];
	    	$rootScope.loggedUserName = response.data.fullname[0];
	    	$location.path("/home");
		  }, function errorCallback(response) {
			  $scope.errMessage = (response.data.error[0]);
			  $scope.errMessageShow = true;
			  angular.element('#email').focus();
		  });
		$rootScope.showLoading = false;
	}
	
	/**$scope.validateLogin = function(){
		$.ajax({
		    type: "POST",
		    url: "rest/user/login",
		    data: JSON.stringify({ email : $scope.email ,password : $scope.password }),
		    contentType: "application/json; charset=utf-8",
		    dataType: "json",
		    success: function(data){
		    	$rootScope.loggedUserId = data.uid;
		    	$rootScope.loggedUserName = data.name;
		    	$cookieStore.put("loggedUserId",data.uid);
		    	$cookieStore.put("loggedUserName",data.name);
		    	$location.path("/home");
		    },
		    error: function (xhr, ajaxOptions, thrownError) {
		    	errData = JSON.parse(xhr.responseText);
		    	errMessageShow = true;
		    	errMessage = errData.error[0];
		    }
		});
		**/

});