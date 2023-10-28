app.controller("product-ctrl", function($scope, $http) {
	$scope.items = [];
	$scope.form = {};
	$scope.productCounts = {};

	$scope.initialize = function() {
		$http.get("/rest/categories").then(resp => {
			$scope.categories = resp.data;
		})
		$http.get("/rest/discountSale").then(resp => {
			$scope.discountSale = resp.data;
		})

		$http.get("/rest/products").then(resp => {
			$scope.items = resp.data;
		});
		$scope.reset(); //để có hình mây lyc1 mới đầu
	}
	$scope.edit = function(item) {
		$scope.form = angular.copy(item);
		$(".nav-tabs a:eq(0)").tab("show");
	}

	$scope.imageChanged = function(files) {
		var data = new FormData();
		data.append('file', files[0]);
		$http.post('/rest/upload/images', data, {
			transformRequest: angular.identity,
			headers: { 'Content-Type': undefined }
		}).then(resp => {
			$scope.form.image = resp.data.name;
		}).catch(error => {
			alert("Lỗi upload hình ảnh");
			console.log("Error", error);
		})
	}
	
	
	$scope.reset = function(){
		$scope.form = {
			available: true,
			image: "cloud-upload.jpg"
		}
	}
	$scope.create = function() {
		var item = angular.copy($scope.form);
		$http.post(`/rest/products`, item).then(resp => {
			/*			resp.data.createDate = new Date(resp.data.createDate)*/
			$scope.items.push(resp.data);
			$scope.reset();

			alert("Thêm mới sản phẩm thành công!");

		}).catch(error => {
			alert("Lỗi thêm mới sản phẩm!");
			console.log("Error", error);
		});
	}

	$scope.update = function() {
		var item = angular.copy($scope.form);
		$http.put(`/rest/products/${item.id}`, item).then(resp => {
			var index = $scope.items.findIndex(p => p.id == item.id);
			$scope.items[index] = item;
			alert("Cập nhật sản phẩm thành công!");
		})
			.catch(error => {
				alert("Lỗi cập nhật sản phẩm!");
				console.log("Error", error);
			});
	}

	$scope.delete = function(item){	
		var item = angular.copy(item);
		item.available = false;
		$http.put(`/rest/products/${item.id}`, item).then(resp => {
			var index = $scope.items.findIndex(p => p.id == item.id);
			$scope.items.splice(index, 1);
			$scope.reset();
			$scope.items[index] = item;
			alert("Xóa sản phẩm thành công!");
		})
			.catch(error => {
				alert("Lỗi xóa sản phẩm!");
				console.log("Error", error);
			});
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

	/*$scope.getProductCounts = function() {
		$http.get('http://localhost:8080/rest/products/counts').then(function(response) {
			$scope.productCounts = response.data;
			renderChart();
		}, function(error) {
			console.error('Error fetching product counts', error);
		});
	};
	$scope.getProductCounts();*/

	/*function renderChart() {
		var labels = Object.keys($scope.productCounts);
		var counts = Object.values($scope.productCounts);

		var data = {
			labels: labels,
			datasets: [{
				data: counts,
				backgroundColor: [
					'#FF6384',
					'#36A2EB',
					'#FFCE56'
				]
			}]
		};

		var ctx = document.getElementById('myChart').getContext('2d');
		var myChart = new Chart(ctx, {
			type: 'pie',
			data: data
		});
	}*/

/*	$http.get("/rest/products/quantities")
		.then(function(response) {
			console.log(response.data); // Log the response data to check for any issues

			var data = response.data;
			var labelsBar = data.map(function(product) {
				return product.name;
			});
			var valuesBar = data.map(function(product) {
				return product.quantity;
			});
			var backgroundColorsBar = [
				'#FF6384',
				'#36A2EB',
				'#FFCE56',
				'#33FF99',
				'#FF99FF',
				'#9966FF',
				'#FF9933',
				'#99CCFF',
				'#FF6699',
				'#66FF99',
				'#FFCC99',
				'#66CCFF',
				'#FF99CC',
				'#99FFCC',
				'#FF6666'
			]; // Colors can be customized

			var ctxBar = document.getElementById('myChartBar').getContext('2d');
			var myChartBar = new Chart(ctxBar, {
				type: 'bar', // Change the type to 'bar' for quantities
				data: {
					labels: labelsBar,
					datasets: [{
						data: valuesBar,
						backgroundColor: backgroundColorsBar
					}]
				}
			});
		});
*/
	$http.get("http://localhost:8080/rest/products/counts")
		.then(function(response) {
			var data = response.data;
			var labelsPie = Object.keys(data);
			var valuesPie = Object.values(data);
			var backgroundColorsPie = ['#FF6384', '#36A2EB', '#FFCE56']; // Colors can be customized

			var ctxPie = document.getElementById('myChartPie').getContext('2d');
			var myChartPie = new Chart(ctxPie, {
				type: 'pie', // Change the type to 'pie' for counts
				data: {
					labels: labelsPie,
					datasets: [{
						data: valuesPie,
						backgroundColor: backgroundColorsPie
					}]
				}
			});
		});








	/*	var data = {
			labels: ["Nike", "Adidas", "Puma"],
			datasets: [{
				data: [30, 20, 15, 10],
				backgroundColor: [
					"#FF6384",
					"#36A2EB",
					"#FFCE56"
		
				]
			}]
		};
		
		var ctx = document.getElementById('myCharttt').getContext('2d');
		var myChart = new Chart(ctx, {
			type: 'pie',
			data: data
		});
	*/
	$scope.initialize();





});