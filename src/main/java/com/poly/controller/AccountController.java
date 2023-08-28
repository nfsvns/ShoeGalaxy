//package com.poly.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import com.poly.dao.AccountDAO;
//
//import com.poly.entity.Account;
//import com.poly.service.SessionService;
//
//@Controller
//public class AccountController {
//	@Autowired
//	AccountDAO dao;
//	@Autowired
//	SessionService sessionService;
//
//	@RequestMapping("/account.html")
//	public String index(Model model) {
//		Account user = sessionService.getAttribute("user");
//		if(!user.getAdmin()) {			
//			String error="Khong du quyen truy cap ";
//			return "redirect:/login.html?error="+error;
//		}
//		Account item = new Account();
//		model.addAttribute("item", item);
//		List<Account> items = dao.findAll();
//		model.addAttribute("accounts", items);
//		return "account";
//	}
//
//	@RequestMapping("/account.html/create")
//	public String create(Account item) {
//		dao.save(item);
//		return "redirect:/account.html";
//	}
//
//	@RequestMapping("/account.html/update")
//	public String update(Account item) {
//		dao.save(item);
//		return "redirect:/account.html/edit/" + item.getUsername();
//	}
//
//	@RequestMapping("/account.html/delete/{username}")
//	public String delete(@PathVariable("username") String username) {
//		dao.deleteById(username);
//		return "redirect:/account.html";
//	}
//
//	@RequestMapping("/account.html/edit/{username}")
//	public String edit(Model model, @PathVariable("username") String username) {
//		Account item = dao.findById(username).get();
//		model.addAttribute("item", item);
//		List<Account> items = dao.findAll();
//		model.addAttribute("accounts", items);
//		return "account";
//	}
//
//}