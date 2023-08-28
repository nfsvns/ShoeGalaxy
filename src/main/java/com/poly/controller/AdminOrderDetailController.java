//package com.poly.controller;
//
//import java.util.Date;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import com.poly.dao.OrderDAO;
//import com.poly.dao.OrderDetailDAO;
//import com.poly.entity.Account;
//import com.poly.entity.Order;
//import com.poly.service.SessionService;
//
//@Controller
//public class AdminOrderDetailController {
//	@Autowired
//	OrderDetailDAO orderDetailDAO;
//	@Autowired
//	SessionService sessionService;
//
//	@RequestMapping("/orderDetail")
//	public String index(Model model) {
//		Account user = sessionService.getAttribute("user");
//		if(!user.getAdmin()) {			
//			String error="Khong du quyen truy cap ";
//			return "redirect:/login.html?error="+error;
//		}
//		model.addAttribute("orderDetailItems", orderDetailDAO.findAll());
//		return "orderDetailAdmin";
//
//	}
//}
