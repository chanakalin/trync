tryncapp.controller('ctrlIndex', function($scope,$rootScope,$location,$http){
	
	//logout
	$scope.logout = function(){
		$rootScope.showLoading = true;
		$http({
				method 	: "GET",
				url		: "rest/user/logout",
				headers	: {
					   "Content-Type": "application/json; charset=utf-8"
				},
				responseType	: "json"				
		}).then(function successCallback(response) {
				$rootScope.loggedUserId = 0;
				$rootScope.loggedUserName = "";
				$rootScope.showLoading = false;    	
		    	$location.path("/logout");
		  });
	}
	
});