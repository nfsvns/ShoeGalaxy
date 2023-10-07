package com.poly.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.poly.dao.AccountDAO;
import com.poly.dao.OrderDAO;
import com.poly.entity.Account;
import com.poly.entity.Order;
import com.poly.entity.OrderDetail;
import com.poly.service.AccountService;
import com.poly.service.OrderService;
import com.poly.service.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Controller
public class OrderStatusController {
	@Autowired
    AccountService accountService;
    @Autowired
    AccountDAO accdao;
    @Autowired
     OrderService orderService;
   
		/*
		  @GetMapping("/TrangThai")
		   public String viewOrderStatus(Model model) {
		  Account currentAccount = accountService.getCurrentAccount(); 
		  if(currentAccount != null){ 
		   List<Order> userOrderabc = orderService.getCurrentUserOrders();
		    model.addAttribute("userOrders",userOrderabc); } return "TrangThai"; }
		  
		 */
    @GetMapping("/TrangThai")
    public String viewOrderStatus(Model model) {
        Account currentAccount = accountService.getCurrentAccount();
        if (currentAccount != null) {
            List<Order> userOrders = orderService.getCurrentUserOrders();

            // Tạo danh sách sản phẩm không trùng nhau và số lượng tương ứng
            Map<String, Integer> productCounts = new HashMap<>();
            List<OrderDetail> uniqueOrderDetails = new ArrayList<>();

            for (Order order : userOrders) {
                List<OrderDetail> orderDetails = order.getOrderDetails();
                for (OrderDetail orderDetail : orderDetails) {
                    String productName = orderDetail.getProduct() != null ? orderDetail.getProduct().getName() : "Sản phẩm không tồn tại";

                    // Kiểm tra xem sản phẩm đã xuất hiện chưa
                    if (!productCounts.containsKey(productName)) {
                        productCounts.put(productName, 1);
                        uniqueOrderDetails.add(orderDetail);
                    } else {
                        // Sản phẩm đã xuất hiện, tăng số lượng lên
                        int count = productCounts.get(productName);
                        productCounts.put(productName, count + 1);
                    }
                }
            }

            model.addAttribute("uniqueOrderDetails", uniqueOrderDetails);
            model.addAttribute("productCounts", productCounts);
            model.addAttribute("userOrders", userOrders);
        }
        return "TrangThai";
    }
    }
            
  
        
        
    
    
	

