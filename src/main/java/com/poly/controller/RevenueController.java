//package com.poly.controller;
//
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import com.poly.dao.OrderDAO;
//import com.poly.entity.Account;
//import com.poly.entity.Order;
//import com.poly.entity.RevenueItem;
//import com.poly.service.SessionService;
//
//
//@Controller
//
//public class RevenueController {
//	@Autowired
//	OrderDAO orderDAO;
//	@Autowired
//	SessionService sessionService;
//	
//	@RequestMapping(value = "/revenue", method = RequestMethod.GET)
//	public String showRevenueByYear(@RequestParam(value = "year", required = false) Integer year, Model model) {
//		Account user = sessionService.getAttribute("user");
//		if(!user.getAdmin()) {			
//			String error="Khong du quyen truy cap ";
//			return "redirect:/login.html?error="+error;
//		}
//		if (year == null) {
//	        // Nếu không có tham số 'year' được truyền, gán giá trị mặc định cho năm hiện tại
//	        year = Calendar.getInstance().get(Calendar.YEAR);
//	    }
//
//	    List<Object[]> revenueData = orderDAO.findByDoanhThuNam(year);
//
//	    List<RevenueItem> revenueItems = new ArrayList<>();
//	    for (Object[] row : revenueData) {
//	        int month = (int) row[0];
//	        double totalRevenue = (double) row[1];
//	        RevenueItem revenueItem = new RevenueItem(month, totalRevenue);
//	        revenueItems.add(revenueItem);
//	    }
//
//	    model.addAttribute("selectedYear", year);
//	    model.addAttribute("revenueItems", revenueItems);
//	    model.addAttribute("years", orderDAO.findByYear());
//
//	    return "revenue";
//	}
//
//	
//
//}
