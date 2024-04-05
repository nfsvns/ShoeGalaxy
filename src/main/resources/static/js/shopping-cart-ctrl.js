const app = angular.module("app", [])
app.controller("cart-ctrl", function($scope, $http, $window) {
	$scope.cart = [];

	$scope.selectedSize = "";

	function displayModal() {
		var modal = document.getElementById('myModal');
		modal.classList.add('show');
		modal.style.display = 'block';
		setTimeout(function() {
			var modal = document.getElementById('myModal');
			modal.classList.remove('show');
			modal.style.display = 'none';
			/*	var modalBackdrop = document.querySelector('.modal-backdrop');*/
			/*	modalBackdrop.parentNode.removeChild(modalBackdrop);*/
		}, 700);
	}
	$scope.getSize = function(event) {
		var size = event.target.innerText;
		$scope.selectedSize = size.trim();



		var buttons = document.getElementsByClassName('btn');
		var size = event.target.innerText.trim();

		for (var i = 0; i < buttons.length; i++) {
			if (buttons[i].innerText.trim() === size) {
				buttons[i].classList.remove('btn-dark');
				buttons[i].classList.add('btn-danger'); // Chọn màu sắc mong muốn cho button được chọn
			} else {
				buttons[i].classList.remove('btn-danger'); // Chọn màu sắc mặc định cho các button không được chọn
				buttons[i].classList.add('btn-dark');
				buttons[i].classList.add('text-light');
			}
		}


	}

	$scope.totalCount = 0; // Khai báo biến totalCount trong $scope
	$scope.getOrderDetails = function() {
		var buttons = document.getElementsByClassName('btn-secondary');
		for (var i = 0; i < buttons.length; i++) {
			buttons[i].addEventListener('click', function(event) {
				var orderId = event.currentTarget.parentNode.querySelector('#orderId').value;
                
				$http.get(`/rest/products/repurchase/${orderId}`)
					.then(function(response) {
						var orderDetails = response.data.orderDetails;
						$scope.cart.items = [];
						if (orderDetails && orderDetails.length > 0) {
							for (var j = 0; j < orderDetails.length; j++) {
								(function(orderDetail) {
									var spanElement = document.getElementById('remoteUi');
									var spanText = spanElement !== null ? spanElement.innerText : null;
									console.log(spanText);
									var accountData = null;
									var productData = null;
									var size = event.currentTarget.getAttribute('data-size');
									$http.get(`/rest/accounts/${spanText}`).then(account => {
										accountData = account.data;
										$http.get(`/rest/products/${orderDetail.id}`).then(product => {
											productData = product.data;
											var data = {
												account: accountData,
												product: productData,
												image: orderDetail.image,
												name: orderDetail.name,
												size: size,
												price: orderDetail.price,
												qty: orderDetail.qty,
												total: orderDetail.price * orderDetail.qty,
												status: true,
											};
											$http({
												method: 'POST',
												url: '/rest/carts',
												data: data
											}).then(function(response) {
												console.log("Dữ liệu đã được lưu vào cơ sở dữ liệu", response.data);
											}).catch(function(error) {
												console.error("Lỗi khi lưu dữ liệu vào cơ sở dữ liệu: ", error);
											});
											$window.location.reload();
											window.location.href = 'http://localhost:8080/cart.html';
										});
									});
									console.log(orderDetail);
									$scope.cart.items.push(orderDetail);
								})(orderDetails[j]);
								
							}
						} else {
							console.log("No order details found");
						}


					})
					.catch(function(error) {
						console.error(error);
					});
				event.currentTarget.removeEventListener('click', this);
			});
		}
	};

var $cart = $scope.cart = {
    items: [],
    add(id) {
        if (!$scope.selectedSize) {
            alert('Vui lòng chọn size.');
            console.log($scope.selectedSize);
            return;
        }

        var spanElement = document.getElementById('remoteUi');
        var spanText = spanElement !== null ? spanElement.innerText : null;
        if (spanText == null) {
            try {
                alert("Vui lòng đăng nhập");
                window.location.href = "/login";
                return; // Ngừng thực thi hàm nếu không có người dùng đăng nhập
            } catch (error) {
                console.error('Thông báo lỗi: ' + error);
                // Xử lý lỗi ở đây, ví dụ: ghi lỗi vào một file log
                return;
            }
        }

        // Lấy thông tin sản phẩm từ server
        $http.get(`/rest/products/${id}`)
            .then(resp => {
                var product = resp.data;
console.log($scope.selectedSize)
                // Kiểm tra số lượng tồn kho của sản phẩm với kích thước đã chọn
                $http.get(`/rest/sizeManager/checkQuantity/${id}/${$scope.selectedSize}`)
                    .then(stockResp => {
                        var availableStock = stockResp.data;
                        
                        // Kiểm tra xem có đủ hàng trong kho không
                        if (availableStock >= product.qty) {
                            // Thêm sản phẩm vào giỏ hàng
                            var item = {
								id:id,
                                product: product,
                                size: $scope.selectedSize,
                                qty: product.qty, // Số lượng mặc định
                                total: product.qty * product.price // Tính tổng tiền
                            };
                           
                            // Thêm item vào giỏ hàng
                            this.items.push(item);
                            
                            // Hiển thị modal thông báo
                            displayModal();
                        } else {
                            alert('Số lượng vượt quá số lượng tồn kho.');
                        }
                    })
                    .catch(error => {
                        console.error("Lỗi khi kiểm tra số lượng tồn kho: ", error);
                    });
            })
            .catch(error => {
                console.error("Lỗi khi tải thông tin sản phẩm từ server: ", error);
            });
    },
    // Các phương thức khác của đối tượng $cart ở đây...
		price_product(item) {
			if (item.percentage == null || item.percentage === 0) {
				return item.price;
			} else {
				return item.price - (item.price * item.percentage / 100);
			}
		},
		amtt_of(item) {
			var itemPrice = this.price_product(item); // Gọi hàm price_product(item) từ cùng đối tượng
			return itemPrice * item.qty; // Tính giá tiền của sản phẩm với số lượng
		},
		amt_of(item) { // tính thành tiền của 1 sản phẩm
			return item.price * item.qty;
		},
		get count() {
			return this.items
				.map(item => item.qty)
				.reduce((total, qty) => total += qty, 0);
		},
		get amount() { // tổng thành tiền các mặt hàng trong giỏ
			return this.items
				.filter(item => item.status === true) // Lọc ra các mặt hàng có item.status = true
				.map(item => this.amtt_of(item)) // Tính tổng tiền cho các mặt hàng thỏa mãn điều kiện
				.reduce((total, amt) => total += amt, 0);
		},
		saveToDatabase(item) {
			var spanElement = document.getElementById('remoteUi');
			var spanText = spanElement !== null ? spanElement.innerText : null;
			console.log(spanText);
			var accountData = null;
			var productData = null;
			$http.get(`/rest/accounts/${spanText}`).then(account => {
				accountData = account.data;
				$http.get(`/rest/products/${item.id}`).then(product => {
					productData = product.data;
					var itemPrice = this.price_product(item);
					var total = this.amtt_of(item);
					var data = {
						// Các trường dữ liệu bạn muốn lưu trữ, tùy thuộc vào đối tượng 'item'
						// Ví dụ: username, product, price, quantity, ...
						account: accountData,
						product: productData,
						image: item.image,
						name: item.name,
						size: item.sizes,
						price: itemPrice,
						qty: item.qty,
						total: total,
						status: false,
						// Thêm các trường dữ liệu khác vào đây nếu cần thiết
					};
					$http({
						method: 'POST',
						url: '/rest/carts',
						data: data
					}).then(function(response) {
						console.log("Dữ liệu đã được lưu vào cơ sở dữ liệu", response.data);
					}).catch(function(error) {
						console.error("Lỗi khi lưu dữ liệu vào cơ sở dữ liệu: ", error);
					});
				});

			});
		},
		updateQtyDatabase(cartId, newQuantity) {
			$http.get(`/rest/carts/${cartId}`)
				.then(response => {
					var data = response.data;
					data.qty = newQuantity;
					data.total = data.qty * data.price;
					$http.put(`/rest/carts/${cartId}`, data)
						.then(response => {
							console.log("Dữ liệu đã được cập nhật trong cơ sở dữ liệu", response.data);
							// Reload cart data after successful update
							this.loadFromDatabase(); // Sử dụng arrow function để giữ nguyên ngữ cảnh
						})
						.catch(error => {
							console.error("Lỗi khi cập nhật dữ liệu trong cơ sở dữ liệu: ", error);
						});
				})
				.catch(error => {
					console.error("Lỗi khi tải dữ liệu từ cơ sở dữ liệu: ", error);
				});
		},
		loadFromDatabase() {
			if (this.items.length == 0) {
				var spanElement = document.getElementById('remoteUi');
				var spanText = spanElement !== null ? spanElement.innerText : null;
				$http.get(`/rest/carts/username/${spanText}`).then(response => {
					this.items = response.data;
				}).catch(error => {
					console.error("Lỗi khi tải dữ liệu từ cơ sở dữ liệu: ", error);
				});
			}
		},
		remove(item) { // xóa sản phẩm khỏi giỏ hàng
			$http.delete(`/rest/carts/${item.id}`)
				.then(response => {
					var index = this.items.findIndex(p => p.id == item.id);
					this.items.splice(index, 1); // Xóa phần tử khỏi mảng items
					this.loadFromDatabase(); // Tải lại bảng sau khi xóa thành công
				})
				.catch(error => {
					console.error('Lỗi khi xóa sản phẩm khỏi giỏ hàng: ', error);
				});
		},
		clearAll() { // Xóa sạch các mặt hàng trong giỏ
			var spanElement = document.getElementById('remoteUi');
			var spanText = spanElement !== null ? spanElement.innerText : null;
			if (!spanText) {
				console.error('Không thể xác định tên người dùng.');
				return;
			}
			$http.delete(`/rest/carts/delete/${spanText}`)
				.then(response => {
					if (response.status === 200) {
						this.items = [];
						this.loadFromDatabase();
						console.log('Đã xóa tất cả sản phẩm trong giỏ hàng thành công.');
					} else {
						console.error('Xảy ra lỗi khi xóa sản phẩm trong giỏ hàng.');
					}
				})
				.catch(error => {
					console.error('Lỗi khi xóa sản phẩm trong giỏ hàng: ', error);
				});
		},
		status_changed(item, isChecked) {
			var newStatus = isChecked ? true : false; // Đảm bảo rằng newStatus sẽ là true nếu checkbox được chọn và ngược lại
			$http.get(`/rest/carts/${item.id}`)
				.then(response => {
					var data = response.data;
					data.status = newStatus;
					$http.put(`/rest/carts/${item.id}`, data)
						.then(response => {
							console.log("Trạng thái đã được cập nhật trong cơ sở dữ liệu", response.data);
							this.loadFromDatabase();
						})
						.catch(error => {
							console.error("Lỗi khi cập nhật trạng thái trong cơ sở dữ liệu: ", error);
						});
				})
				.catch(error => {
					console.error("Lỗi khi tải dữ liệu từ cơ sở dữ liệu: ", error);
				});
		},
		clear() {
			var spanElement = document.getElementById('remoteUi');
			var spanText = spanElement !== null ? spanElement.innerText : null;
			if (!spanText) {
				console.error('Không thể xác định tên người dùng.');
				return;
			}
			$http.delete(`/rest/carts/delete/status/${spanText}`).then(response => {
				if (response.status === 200) {
					this.items = [];
					this.loadFromDatabase();
					console.log('Đã xóa tất cả sản phẩm có trạng thái là true trong giỏ hàng thành công.');
				} else {
					console.error('Xảy ra lỗi khi xóa sản phẩm trong giỏ hàng.');
				}
			}).catch(error => {
				console.error('Lỗi khi xóa sản phẩm trong giỏ hàng: ', error);
			});
		}
	}
	$cart.loadFromDatabase();

	// Đặt hàng
	$scope.order = {
		get account() {
			return { username: $auth.user.username }
		},
		createDate: new Date(),
		address: "",
		get orderDetails() {
			return $cart.items.map(item => {
				return {
					product: { id: item.id },
					price: item.price,
					quantity: item.qty
				}
			});
		},
		purchase() {
			var order = angular.copy(this);
			// Thực hiện đặt hàng
			$http.post("/rest/orders", order).then(resp => {
				alert("Đặt hàng thành công!");
				$cart.clear();
				location.href = "/order/detail/" + resp.data.id;
			}).catch(error => {
				alert("Đặt hàng lỗi!");
				console.log(error)
			})
		}
	}



})


const host = "https://provinces.open-api.vn/api/";
var callAPI = (api) => {
	return axios.get(api)
		.then((response) => {
			renderData(response.data, "province");
		});
}
callAPI('https://provinces.open-api.vn/api/?depth=1');
var callApiDistrict = (api) => {
	return axios.get(api)
		.then((response) => {
			renderData(response.data.districts, "district");
		});
}
var callApiWard = (api) => {
	return axios.get(api)
		.then((response) => {
			renderData(response.data.wards, "ward");
		});
}

var renderData = (array, select) => {
	let row = ' <option disable value="">chọn</option>';
	array.forEach(element => {
		row += `<option data-code="${element.code}" value="${element.name}">${element.name}</option>`
	});
	document.querySelector("#" + select).innerHTML = row;
}

$("#province").change(() => {
	let selectedCode = $("#province option:selected").data("code");
	callApiDistrict(host + "p/" + selectedCode + "?depth=2");
});
$("#district").change(() => {
	let selectedCode = $("#district option:selected").data("code");
	callApiWard(host + "d/" + selectedCode + "?depth=2");
});

