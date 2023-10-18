package com.poly.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.poly.entity.Address;
import com.poly.service.AddressService;



public class AddressController {
/*	@Autowired
	AddressService addressService;
@PostMapping("/address")
public String getAddressList(Model model) {
	
	String username = SecurityContextHolder.getContext().getAuthentication().getName();
    
    // Lấy danh sách địa chỉ của người dùng
    List<Address> userAddresses = addressService.getAddressesByUsername(username);
    
    // Đặt danh sách địa chỉ vào model để truyền đến giao diện người dùng
    model.addAttribute("userAddresses", userAddresses);
    
  
	return "redirect:/check";
}
*/
}
