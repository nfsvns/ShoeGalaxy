package com.poly.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poly.dao.AccountDAO;
import com.poly.dao.OrderDAO;
import com.poly.dao.OrderDetailDAO;
import com.poly.dao.ShoppingCartDAO;
import com.poly.entity.Account;
import com.poly.entity.Order;
import com.poly.entity.OrderDetail;
import com.poly.service.AccountService;
import com.poly.service.OrderService;
import javax.servlet.http.HttpServletRequest;

@Controller
public class OrderStatusController {
	@Autowired
	AccountService accountService;
	@Autowired
	AccountDAO accdao;
	@Autowired
	OrderService orderService;
	@Autowired
	OrderDAO dao;
	@Autowired
	ShoppingCartDAO shoppingCartDAO;

	@GetMapping("/TrangThai")
	public String viewOrderStatus(Model model, HttpServletRequest request) {
//		model.addAttribute("cartItems", shoppingCartDAO.getAll());
//		model.addAttribute("total", shoppingCartDAO.getAmount());
		return "TrangThai";
	}
	@GetMapping("/shipped")
	public String shipped(Model model, HttpServletRequest request) {
		String remoteUser = request.getRemoteUser();
		if (remoteUser != null) {
			List<Object[]> shippedOrders = orderService.getShippedOrdersForCurrentAccount(remoteUser);

			model.addAttribute("shippedOrders", shippedOrders);
		}
		return "orderstatus/shipped";
	}
	@GetMapping("/unshipped")
	public String unshipped(Model model, HttpServletRequest request) {
		String remoteUser = request.getRemoteUser();
		if (remoteUser != null) {
			List<Object[]> unshippedOrders = orderService.getUnshippedOrdersForCurrentAccount(remoteUser);

			model.addAttribute("unshippedOrders", unshippedOrders);
		}
		return "orderstatus/unshipped";
	}
}
