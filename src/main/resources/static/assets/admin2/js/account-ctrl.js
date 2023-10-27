app.controller("account-ctrl", function($scope, $http) {
    $scope.items = [];
    $scope.form = {};

    $scope.initialize = function() {
        $http.get("/rest/accounts").then(resp => {
            $scope.items = resp.data;
            $scope.form = {
				
			};
             $scope.reset(); // To have a clean form initially
        });

       
    };
    
$scope.reset = function(){
		$scope.form = {
			available: true,
			photo: "cloud-upload.jpg"
		}
	}
	
	  $scope.imageChanged = function(files) {
    var data = new FormData();
    data.append('file', files[0]);
    $http.post('/rest/upload/images', data, {
        transformRequest: angular.identity,
        headers: { 'Content-Type': undefined }
    }).then(resp => {
        $scope.form.photo = resp.data.name;
    }).catch(error => {
        alert("Lỗi upload hình ảnh");
        console.log("Error", error);
    });
}

	$scope.edit = function(item){
		$scope.form = angular.copy(item);
		$(".nav-tabs a:eq(0)").tab("show");
	}
	
	$scope.update = function(){
		var item = angular.copy($scope.form);
		$http.put(`/rest/accounts/${item.username}`, item).then(resp => {
			var index = $scope.items.findIndex(p => p.id == item.id);
			$scope.items[index] = item;
			alert("Cập nhật tài khoản thành công!");
		})
		.catch(error => {
			alert("Lỗi cập nhật tài khoản!");
			console.log("Error", error);
		});
	}
	$scope.create = function(){
		var item = angular.copy($scope.form);
		$http.post(`/rest/accounts`, item).then(resp => {
			$scope.items.push(resp.data);
			$scope.reset();
			alert("Thêm mới tài khoản thành công!");
		}).catch(error => {
			alert("Lỗi thêm mới tài khoản!");
			console.log("Error", error);
		});
	}

	$scope.delete = function(item){
		if(confirm("Bạn muốn xóa người dùng này?")){
			$http.delete(`/rest/accounts/${item.username}`).then(resp => {
				var index = $scope.items.findIndex(p => p.id == item.id);
				$scope.items.splice(index, 1);
				$scope.reset();
				alert("Xóa người dùng thành công!");
			}).catch(error => {
				alert("Lỗi xóa người dùng!");
				console.log("Error", error);
			})
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
            return $scope.items.slice(start, start + this.size);
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
    };

    $scope.initialize();
});
