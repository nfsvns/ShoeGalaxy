package com.poly.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.entity.DiscountCode;
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
	@GetMapping("{id}")
	public Discount_Sale getOne(@PathVariable("id") Integer id) {
		
		return discountSaleService.findById(id);
	}
	
	@PostMapping
	public Discount_Sale post(@RequestBody Discount_Sale discount_Sale) {
		discountSaleService.create(discount_Sale);
		return discount_Sale;
	}
	@PutMapping("{id}")
	public Discount_Sale put(@PathVariable("id") Integer id, @RequestBody Discount_Sale discount_Sale) {
		return discountSaleService.update(discount_Sale);
	}
	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") Integer id) {
		discountSaleService.deleteDiscountCode(id);
		discountSaleService.delete(id);
	}
}
