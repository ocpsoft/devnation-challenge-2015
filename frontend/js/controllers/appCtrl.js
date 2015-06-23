angular.module("devnation").controller("appCtrl", function ($scope, $http, config) {
	$scope.upc = {};
	$scope.message = "";
	$scope.checkUPC = function (upc) { 
		var url = config.baseUrl + '/rest/frontend/lookupUpc?upc='+upc;
		$http.get(url).success(function(data, status, headers, config) {
    		// this callback will be called asynchronously
    		// when the response is available
			console.log("OK: "+data);
			$scope.upc = data;
		}).error(function(data, status, headers, config) {
    		// called asynchronously if an error occurs
    		// or server returns response with an error status.
    		console.log("FAIL: "+status);
    		$scope.message = data;
		});
	}

	$scope.submitUPCQuantity = function (auth, upc, quantity) { 
		var url = config.baseUrl + '/rest/frontend/sendMedicine?auth='+auth+'&upc='+upc+'&quantity='+quantity;
		$http.get(url).success(function(data, status, headers, config) {
    		// this callback will be called asynchronously
    		// when the response is available
			console.log("OK: "+data);
		}).error(function(data, status, headers, config) {
    		// called asynchronously if an error occurs
    		// or server returns response with an error status.
    		console.log("FAIL: "+status);
    		$scope.message = data;
		});
	}

});