
app.controller("history-ctrl", function($scope, $http, $timeout,$window) {
	$scope.selectedDate = "";
	$scope.filteredItems = [];
	$scope.uniqueDates = [];
	$scope.items = [];
	$scope.form = {};
	$scope.showDetail = false;
    $scope.showSuggestions = false;
    $scope.suggestions = [];
   $scope.updateSuggestions = function() {
    // Replace the code below with your logic to get suggestions.
    // Example: Use usernames and order IDs as suggestions
    $scope.suggestions = $scope.items
        .filter(item => 
            String(item.id).toLowerCase().includes($scope.searchText.toLowerCase()) ||
            (item.account && String(item.account.username).toLowerCase().includes($scope.searchText.toLowerCase()))
        )
        .map(item => ({ id:item.id, username: item.account ? item.account.username : '' }));

    // Show/hide suggestions based on input length
    $scope.showSuggestions = $scope.searchText.length > 0;
};

     $scope.filterByOrderId = function(orderId) {
        // Replace this with your logic to filter items based on order ID
        $scope.filteredItems = $scope.items.filter(item => String(item.id).toLowerCase() === String(orderId).toLowerCase());
    };
    $scope.selectSuggestion = function(selectedSuggestion) {
        // Set the selected suggestion in the search input
        $scope.searchText = selectedSuggestion.username;
        // Hide suggestions
        $scope.showSuggestions = false;

        // Trigger the search based on the selected suggestion
        $scope.filterByOrderId(selectedSuggestion.id); // Replace this with your search function
    };
    // ...
    $scope.refreshPage = function(){
		$window.location.reload();
	}
	// Tính toán mảng uniqueDates từ items
	$scope.filterByDate = function(selectedDate) {
		if (selectedDate) {
			$scope.filteredItems = $scope.items.filter(function(item) {
				var itemDate = new Date(item.createDate).toLocaleDateString('en-GB');
				return itemDate === selectedDate;
			});
		} else {
			$scope.filteredItems = $scope.items;
		}
	};


	$scope.computeUniqueDatesAndInitDate = function() {
		flatpickr("#calendar", {
			dateFormat: "d-m-Y",
			defaultDate: $scope.selectedDate,
			onChange: function(selectedDate) {
				$timeout(function() {
					$scope.selectedDate = new Date(selectedDate).toLocaleDateString('en-GB');
					$scope.filterByDate($scope.selectedDate);

				});
			}
		});
	};

	$scope.getDetailData = function(item) {
		if (item.showDetail) {
			item.showDetail = false;
		}
		else {
			$http.get(`/rest/historys/details/${item.id}`)
				.then(function(resp) {
					item.detailData = resp.data;
					item.showDetail = true;
				}).catch(function(error) {
					console.error("error: ", error)
				})
		}
	}
	$scope.initialize = function() {
		$http.get("/rest/historys").then(resp => {
			$scope.items = resp.data;
			$scope.computeUniqueDatesAndInitDate();
			$scope.filterByDate($scope.selectedDate);
			$scope.form = {
				available: null,
			};
			
		});
		$scope.reset(); //để có hình mây lyc1 mới đầu
	};

	$scope.edit = function(item) {
		$scope.form = angular.copy(item);
		$(".nav-tabs a:eq(0)").tab("show");
	}

	$scope.reset = function() {
		$scope.form = {
		}
	}

	$scope.update = function() {
		var item = angular.copy($scope.form);
		$http.put(`/rest/historys/${item.id}`, item).then(resp => {
			var index = $scope.items.findIndex(p => p.id == item.id);
			if(index.status == 'Đã Giao'){
				index.available == true;
			}
			$scope.items[index] = item;
			alert("Cập nhật lịch sử đơn hàng thành công!");
			$scope.initialize();
		})
			.catch(error => {
				alert("Lỗi cập nhật lịch sử đơn hàng!");
				console.log("Error", error);
			});
	}

	$scope.delete = function(item) {
		if (confirm("Bạn muốn xóa lịch sử đơn hàng này?")) {
			// Check if 'status' property exists in the item object
			if (!item.hasOwnProperty('status')) {
				item.status = "Đã Hủy";
			} else {
				// Update order status to "Đã Hủy" and set available to false
				item.status = "Đã Hủy";
				item.available = false;
			}

			$http.put(`/rest/historys/${item.id}`, item).then(resp => {
				console.log("Update successful:", resp.data);
				alert("Xóa lịch sử đơn hàng thành công!");
				$scope.initialize();
			}).catch(error => {
				console.error("Update error:", error);
				alert("Lỗi xóa lịch sử đơn hàng!");
			});
		}
	}

	$scope.filteredItems = {
		remove(id) { // xóa sản phẩm khỏi giỏ hàng
			var index = this.items.findIndex(item => item.id == id);
			this.items.splice(index, 1);
			this.saveToLocalStorage();
		}
	}
	$scope.pager = {
		page: 0,
		size: 10,
		get items() {
			if (this.page < 0) {
				this.last();
			}
			if (this.page >= this.count) {
				this.first();
			}
			var start = this.page * this.size;
			return $scope.items.slice(start, start + this.size)
		},
		get count() {
			return Math.ceil(1.0 * $scope.items.length / this.size);
		},
		first() {
			this.page = 0;
		},
		last() {
			this.page = this.count - 1;
		},
		next() {
			this.page++;
		},
		prev() {
			this.page--;
		}
	}

	$scope.initialize();
});