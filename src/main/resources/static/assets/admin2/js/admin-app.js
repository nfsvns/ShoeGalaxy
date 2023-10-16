app = angular.module("app1", ["ngRoute"]);

/*app.run(function($http, $rootScope){
	$http.get(`/rest/security/authentication`).then(resp => {
		if(resp.data){
			$auth = $rootScope.$auth = resp.data;
			$http.defaults.headers.common["Authorization"] = $auth.token;
		}
	});
})*/

app.config(function($routeProvider) {
	$routeProvider
		.when("/product", {
			templateUrl: "/assets/admin/product/index.html",
			controller: "product-ctrl"
		})

		.otherwise({
			templateUrl: "/assets/admin2/pages/dashboard/index.html",

		});
});