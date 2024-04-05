package com.poly.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.dao.ProductDAO;
import com.poly.entity.Product;
import com.poly.service.ProductService;

@Controller
public class ProductController2 {
	@Autowired
	ProductService productService;
	@Autowired
	ProductDAO dao;

	@RequestMapping("/shop/list")
	public String list(Model model, @RequestParam("cid") Optional<String> cid, @RequestParam("p") Optional<Integer> p) {
		if (cid.orElse("").isEmpty()) {
		
			List<Product> list = productService.findAll();
			Pageable pageable = PageRequest.of(p.orElse(0), 6);
			Page<Product> page = dao.findAll(pageable);
			model.addAttribute("items", page);
//			model.addAttribute("items", list);
		} else {
			List<Product> list = productService.findByCategoryId(cid.get());
			model.addAttribute("items", list);
		}
		return "shop";
	}
	
	

	@RequestMapping("/shop/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id) {
		
		Product item = productService.findById(id);
		model.addAttribute("item", item);
		return "shop-single";
	}

	@RequestMapping("/shop/detail/sortASC")
	public String sortNameASC(Model model) {
		List<Product> list = productService.sortProductASC();
		model.addAttribute("items", list);
		return "shop";
	}

	@RequestMapping("/shop/detail/sortDesc")
	public String sortNameDesc(Model model) {
		List<Product> list = productService.sortProductDesc();
		model.addAttribute("items", list);
		return "shop";
	}

	@RequestMapping("/shop/detail/sortPriceLtoH")
	public String sortPriceAsc(Model model) {
		List<Product> list = productService.sortPriceLowToHight();
		model.addAttribute("items", list);
		return "shop";
	}
	@RequestMapping("/shop/detail/sortPriceHtoL")
	public String sortPriceDesc(Model model ) {
		List<Product> list = productService.sortPriceHightToLow();
		model.addAttribute("items", list);
		return  "shop";
	}

}
