const app = angular.module("app1", ["ngRoute"]);

app.config(function($routeProvider) {
	$routeProvider
		.when("/dashboard", {
			templateUrl: "/assets/admin2/pages/dashboard/index.html",
			controller: "dashboard-ctrl"
		})
		.when("/table", {
			templateUrl: "/assets/admin2/pages/tables.html",
			
		})
		.when("/billing", {
			templateUrl: "/assets/admin2/pages/billing.html",
			
		})
		.when("/history", {
			templateUrl: "/assets/admin2/pages/history.html",
			controller: "history-ctrl"
		})
		.otherwise({
			redirectTo: "/dashboard"

		});
});