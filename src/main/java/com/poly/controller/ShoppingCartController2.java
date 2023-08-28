package com.poly.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.dao.OrderDetailDAO;
import com.poly.dao.ProductDAO;
import com.poly.dao.ShoppingCartDAO;
import com.poly.entity.OrderDetail;
import com.poly.entity.Product;
//import com.poly.model.CartItem;
//import com.poly.model.Product;

import com.poly.service.SessionService;


import ch.qos.logback.core.joran.util.beans.BeanUtil;

@Controller

public class ShoppingCartController2 {
	// mới
	@Autowired
	SessionService service;
	@Autowired
	ProductDAO productDAO;
	@Autowired
	ShoppingCartDAO shoppingCartDAO;
	

	@RequestMapping("/cart.html")
	public String list(Model model) {
		model.addAttribute("cartItems", shoppingCartDAO.getAll());
		model.addAttribute("total", shoppingCartDAO.getAmount());
		service.setAttribute("cartQuantity", shoppingCartDAO.getCount());
		return "cart";
	}

	@GetMapping("/cart.html/add/{productId}")
	public String addCart(@PathVariable("productId") Integer productId) {
		Optional<Product> product = productDAO.findById(productId);
		Product item = new Product();
		BeanUtils.copyProperties(product.get(), item);
		item.setQuantity(1);
		shoppingCartDAO.add(item);
		service.setAttribute("cartQuantity", shoppingCartDAO.getCount());
		return "redirect:/shop.html";
	}

	@GetMapping("/cart.html/remove/{productId}")
	public String removeCart(@PathVariable("productId") Integer productId) {
		shoppingCartDAO.remove(productId);
		return "redirect:/cart.html";
	}

	@PostMapping("/cart.html/update")
	public String updateCart(@RequestParam("id") Integer id, @RequestParam("quantity") Integer quantity) {
		shoppingCartDAO.update(id, quantity);
		service.setAttribute("cartQuantity", shoppingCartDAO.getCount());
		return "redirect:/cart.html";
	}
	@GetMapping("/cart.html/clear")
	public String clearCart() {
		shoppingCartDAO.clear();
		return "redirect:/cart.html";
	}
	
	
	

}
