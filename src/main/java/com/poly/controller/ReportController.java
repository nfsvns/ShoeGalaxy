//package com.poly.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import com.poly.dao.ProductDAO;
//import com.poly.entity.Account;
//import com.poly.entity.Report;
//import com.poly.service.SessionService;
//
//
//@Controller
//public class ReportController {
//	@Autowired
//	ProductDAO productDAO;
//	@Autowired
//	SessionService sessionService;
//	
//	@RequestMapping("/report")
//	public String report(Model model) {
//		Account user = sessionService.getAttribute("user");
//		if(!user.getAdmin()) {			
//			String error="Khong du quyen truy cap ";
//			return "redirect:/login.html?error="+error;
//		}
//		List<Report> items = productDAO.getInventoryByCategory();
//		model.addAttribute("items", items);
//		return "report";
//	}
//}
