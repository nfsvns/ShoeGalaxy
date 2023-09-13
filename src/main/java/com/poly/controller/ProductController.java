package com.poly.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.dao.CategoryDAO;
import com.poly.dao.ProductDAO;
import com.poly.dao.SizeDAO;
import com.poly.entity.Product;
import com.poly.entity.Size;
import com.poly.service.SessionService;

@Controller
public class ProductController {

	@Autowired
	ProductDAO dao;
	@Autowired
	SizeDAO sizeDAO;

	@Autowired
	SessionService sessionService;

	@RequestMapping("/shop.html")
	public String list(Model model, @RequestParam("p") Optional<Integer> p) {
		Pageable pageable = PageRequest.of(p.orElse(0), 6);
		Page<Product> page = dao.findAll(pageable);
		model.addAttribute("products", page);
		int count = dao.countMlBProducts();
		model.addAttribute("count", count);
		int countAD = dao.countADProducts();
		model.addAttribute("countAD", countAD);
		int countNK = dao.countNKProducts();
		model.addAttribute("countNK", countNK);
		return "shop";
	}

	@RequestMapping("/shop.html/search")
	public String searchAndPage(Model model, @RequestParam("keywords") Optional<String> kw,
			@RequestParam("p") Optional<Integer> p) {
		String keywords = kw.orElse(sessionService.getAttribute("keywords"));
		sessionService.setAttribute("keywords", keywords);

		Pageable pageable = PageRequest.of(p.orElse(0), 6);
		Page<Product> page = dao.findByKeywords("%" + keywords + "%", pageable);
		model.addAttribute("products", page);
		model.addAttribute("keywords", keywords);
		model.addAttribute("check", "1");
		return "shop";
	}

	@RequestMapping("shop.html/findByPrice")
	public String findByPrice(Model model, @RequestParam("min") Optional<Double> min,
			@RequestParam("max") Optional<Double> max, @RequestParam("p") Optional<Integer> p) {
		double minPrice = min.orElse(sessionService.getAttribute("minPrice"));
		double maxPrice = max.orElse(sessionService.getAttribute("maxPrice"));
		sessionService.setAttribute("minPrice", minPrice);
		sessionService.setAttribute("maxPrice", maxPrice);

		Pageable pageable = PageRequest.of(p.orElse(0), 6);
		Page<Product> item = dao.findByPriceBetween(minPrice, maxPrice, pageable);
		model.addAttribute("products", item);
		model.addAttribute("check", "2");
		return "shop";
	}

	@RequestMapping("/shop.html/searchBrand")
	public String brand(Model model, @RequestParam("brand") Optional<String> br,
			@RequestParam("p") Optional<Integer> p) {
		String brand = br.orElse(sessionService.getAttribute("brand"));
		sessionService.setAttribute("brand", brand);

		Pageable pageable = PageRequest.of(p.orElse(0), 6);
		Page<Product> page = dao.findByBrand(brand, pageable);
		model.addAttribute("products", page);
		model.addAttribute("check", "3");
		return "shop";
	}

	@RequestMapping("/shop.html/sort")
	public String sort(Model model, @RequestParam("desc") Optional<String> de, @RequestParam("p") Optional<Integer> p) {
		String desc = de.orElse(sessionService.getAttribute("desc"));

		sessionService.setAttribute("desc", desc);

		Sort sort = Sort.by(Sort.Direction.DESC, desc);
		Pageable pageable = PageRequest.of(p.orElse(0), 6, sort);
		Page<Product> page = dao.findAll(pageable);
		model.addAttribute("products", page);
		model.addAttribute("check", "4");
		return "shop";
	}

	@RequestMapping("/shop.html/sort1")
	public String sort2(Model model, @RequestParam("asc") Optional<String> a, @RequestParam("p") Optional<Integer> p) {
		String asc = a.orElse(sessionService.getAttribute("asc"));
		sessionService.setAttribute("asc", asc);
		Sort sort = Sort.by(Sort.Direction.ASC, asc);
		Pageable pageable = PageRequest.of(p.orElse(0), 6, sort);
		Page<Product> page = dao.findAll(pageable);
		model.addAttribute("products", page);
		model.addAttribute("check", "5");
		return "shop";
	}

	@RequestMapping("/shop-single.html/{productId}")
	public String getProduct(Model model, @PathVariable("productId") int productId) {
		Product list = dao.findById(productId).get();
		List<Size> listS = sizeDAO.findByIdProduct(productId);
		model.addAttribute("prod", list);
		model.addAttribute("prodd", listS);
		return "shop-single";
	}

}