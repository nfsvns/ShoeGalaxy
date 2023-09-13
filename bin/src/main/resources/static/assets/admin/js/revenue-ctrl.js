app.controller("revenue-ctrl", function($scope, $http, $location) {
	
	$scope.selectedYear = null;
	$scope.initialize = function() {	
		$http.get("/rest/revenue").then(resp => {
			$scope.years = resp.data;
			$scope.selectedYear = 2023; // Đặt năm 2023 làm năm mặc định
			$scope.getRevenueByYear(); // Gọi hàm để tải dữ liệu doanh thu ngay khi trang tải lên
		}).catch(error => {
	    	$location.path("/unauthorized");
	    })
	}
	$scope.getRevenueByYear = function() {
		// Perform an API call to get revenue data for the selected year
		// Replace 'YOUR_API_URL' with the actual API endpoint
		$http.get("/rest/revenue/" + $scope.selectedYear)
			.then(function(response) {
				$scope.revenueItems = response.data;
			})
			.catch(function(error) {
				console.log('Error fetching revenue data:', error);
			});
	};
	$scope.initialize();
});
