

const app = angular.module("app", [])
app.controller("cart-ctrl", function($scope, $http) {
	// quản lý giỏ hàng
	/*$scope.prodd = [
		{ size: '36' },
		{ size: '37' },
		{ size: '39' },
		{ size: '40' },
		{ size: '41' },
		{ size: '42' },
		// ... Thêm dữ liệu size giày vào đây
	];*/
	// Ban đầu không có size nào được chọn
	$scope.cart = [];
	$scope.selectedSize = '';






	function displayModal() {
		var modal = document.getElementById('myModal');
		modal.classList.add('show');
		modal.style.display = 'block';
		setTimeout(function() {
			var modal = document.getElementById('myModal');
			modal.classList.remove('show');
			modal.style.display = 'none';
			var modalBackdrop = document.querySelector('.modal-backdrop');
			modalBackdrop.parentNode.removeChild(modalBackdrop);
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
                            var orderDetail = orderDetails[j];
                            $scope.cart.items.push(orderDetail);
                        }
                    } else {
                        console.log("No order details found");
                    }
                    $scope.cart.saveToLocalStorage();
                    window.location.href = 'http://localhost:8080/cart.html';
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
				alert('Vui lòng chọn size. ');
				return;
			}

			// Check if the item with the same ID and size is already in the cart
			var item = this.items.find(item => item.id == id && item.sizes == $scope.selectedSize);
			if (item) {
				item.qty += $scope.quantity; // Increment the quantity if the item already exists in the cart
				this.saveToLocalStorage();
				displayModal();
			} else {
				// If the item doesn't exist in the cart, fetch product details
				$http.get(`/rest/products/${id}`).then(resp => {
					// Fetch product images 
					$http.get(`/rest/products/${id}/images`).then(imageResp => {
						if (imageResp.data.length > 0) {
							resp.data.image = imageResp.data[0].image;
						} else {
							// Set a default image URL if no images are available
							resp.data.image = 'url_to_default_image.jpg';
						}

						$http.get(`/rest/products/${id}/price`).then(totalAmount => {
							if (totalAmount.data.length > 0) {
								resp.data.percentage = totalAmount.data[0].percentage;

							} else {
								resp.data.percetage = 0;
							}


							resp.data.qty = $scope.quantity;
							resp.data.sizes = $scope.selectedSize;
							// Check stock quantity before adding to cart
							$http.get(`/rest/sizeManager/checkQuantity/${id}/${$scope.selectedSize}`).then(stockResp => {
								var availableStock = stockResp.data;

								if (availableStock >= resp.data.qty) {
									this.items.push(resp.data);

									resp.data.qty = $scope.quantity;
									resp.data.sizes = $scope.selectedSize;
									this.saveToLocalStorage();
									displayModal();
								} else {
									alert('Số lượng vượt quá số lượng tồn kho.');
									return; // Stop execution if the stock quantity is insufficient
								}
							});
							// Set the quantity and selected size based on user input


						});
					});
				});
			}
		},
		remove(id) { // xóa sản phẩm khỏi giỏ hàng
			var index = this.items.findIndex(item => item.id == id);
			this.items.splice(index, 1);
			this.saveToLocalStorage();
		},
		clear() { // Xóa sạch các mặt hàng trong giỏ
			this.items = []
			this.saveToLocalStorage();
		},
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
		get count() { // tính tổng số lượng các mặt hàng trong giỏ
			return this.items
				.map(item => item.qty)
				.reduce((total, qty) => total += qty, 0);
		},
		get amount() { // tổng thành tiền các mặt hàng trong giỏ
			return this.items
				.map(item => this.amtt_of(item))
				.reduce((total, amt) => total += amt, 0);
		},
		saveToLocalStorage() { // lưu giỏ hàng vào local storage
			var json = JSON.stringify(angular.copy(this.items));
			localStorage.setItem("cart", json);
		},
		loadFromLocalStorage() { // đọc giỏ hàng từ local storage
			var json = localStorage.getItem("cart");
			this.items = json ? JSON.parse(json) : [];
		}
	}

	$cart.loadFromLocalStorage();

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
				$window.location("../assets/admin2/pages/history.html").reload();
			}).catch(error => {
				alert("Đặt hàng lỗi!")
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
	document.querySelector("#" + select).innerHTML = row
}

$("#province").change(() => {
	let selectedCode = $("#province option:selected").data("code");
	callApiDistrict(host + "p/" + selectedCode + "?depth=2");
	// printResult();
});
$("#district").change(() => {
	let selectedCode = $("#district option:selected").data("code");
	callApiWard(host + "d/" + selectedCode + "?depth=2");
	// printResult();
});
$("#ward").change(() => {
	// printResult();
})
