package com.poly.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.entity.Discount_Sale;

import com.poly.service.DiscountSaleService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/discountSale")
public class DiscountSaleRestController {
	@Autowired
	DiscountSaleService discountSaleService;

	@GetMapping
	public List<Discount_Sale> getAll() {
		return discountSaleService.findAll();
	}
}
