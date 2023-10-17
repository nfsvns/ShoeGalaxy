package com.poly.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Admin2Controller {
	@RequestMapping("/admin2")
	public String index2() {
		return "redirect:/assets/admin2/index.html";
	}
}
