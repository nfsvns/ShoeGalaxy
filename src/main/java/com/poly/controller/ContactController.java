package com.poly.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.poly.dao.ContactDAO;
import com.poly.entity.Contact;

@Controller
public class ContactController {

	@Autowired
	private ContactDAO contactDAO;

	  @PostMapping("/addContact")
	    public String processContactForm(@ModelAttribute("contact") Contact contact, Model model) {
	        // Set ngày hiện tại
	        contact.setCreateDate(LocalDate.now());

	        try {
	            // Lưu thông tin liên hệ vào cơ sở dữ liệu bằng ContactDAO
	            contactDAO.save(contact);
	            model.addAttribute("messages", "Contact information sent successfully!");
	         // Thêm thuộc tính "success" để kiểm tra điều kiện trong Thymeleaf
	        } catch (Exception e) {
	            // Xử lý lỗi nếu có
	            model.addAttribute("messages", "Error sending contact information. Please try again later.");
	        
	        }

	        // Chuyển hướng hoặc hiển thị trang cảm ơn
	        return "/contact.html"; // Điều hướng đến trang cảm ơn
	    }
}
