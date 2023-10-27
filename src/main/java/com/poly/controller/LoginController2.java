package com.poly.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.dao.AccountDAO;
import com.poly.dao.AuthorityDAO;
import com.poly.dao.RoleDAO;
import com.poly.entity.Account;
import com.poly.entity.Authority;
import com.poly.entity.Role;
import com.poly.service.UserService;

@Controller
public class LoginController2 {

	@Autowired
	AccountDAO accountDAO;
	@Autowired
	UserService userService;
	@Autowired
	AuthorityDAO authorityDAO;
	@Autowired 
	RoleDAO roleDAO;

	@RequestMapping("/login")
	public String loginForm(Model model) {
		return "/login";
	}

	@RequestMapping("/login/success")
	public String loginSuccess(Model model) {
		model.addAttribute("message", "Đăng nhập thành công!");
		return "forward:/login";
	}
	
	@RequestMapping("/oauth2/login/success")
	public String success(OAuth2AuthenticationToken oauthh2) {
		userService.loginFromOAuth2(oauthh2);
		return "forward:/login/success";
	}

	@RequestMapping("/login/error")
	public String error(Model model) {
		model.addAttribute("message", "Sai thông tin đăng nhập!");
		return "forward:/login";
	}

	@RequestMapping("/logout/success")
	public String logoff(Model model) {
		model.addAttribute("message", "Đăng xuất thành công!");

		return "forward:/login";
	}

	@RequestMapping("/access/denied")
	public String denied(Model model) {
		model.addAttribute("message", "Bạn không có quyền truy xuất!");
		return "/login";
	}

	@RequestMapping("/register.html")
	public String register() {
		return "register";
	}

	@RequestMapping("/register/success")
	public String register1(Model model, @RequestParam String username, @RequestParam String password,
			@RequestParam String fullname, @RequestParam String email) {
		if (!accountDAO.findById(username).isEmpty()) {
			model.addAttribute("error", "Vui lòng đặt tên username khác!");
		} else {
			Account user = new Account();
			user.setUsername(username);
			user.setPassword(password);
			user.setFullname(fullname);
			user.setEmail(email);
			accountDAO.save(user);
			Authority authority = new Authority();
			authority.setAccount(user);
			Role role = roleDAO.findById("CUST").get();
			authority.setRole(role);
			authorityDAO.save(authority);
			model.addAttribute("message", "Đăng kí thành công");
		}
		return "register";
	}

}
