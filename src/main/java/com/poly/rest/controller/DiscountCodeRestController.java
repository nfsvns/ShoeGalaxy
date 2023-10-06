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
import com.poly.entity.Product;
import com.poly.service.DiscountCodeService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/discountCode")
public class DiscountCodeRestController {

	@Autowired 
	DiscountCodeService codeService; 
	@GetMapping
	public List<DiscountCode> getAll(){
		return codeService.findAll();
	}
	@GetMapping("{id}")
	public DiscountCode getOne(@PathVariable("id") Integer id) {
		
		return codeService.findById(id);
	}
	
	@PostMapping
	public DiscountCode post(@RequestBody DiscountCode discountCode) {
		codeService.create(discountCode);
		return discountCode;
	}
	@PutMapping("{id}")
	public DiscountCode put(@PathVariable("id") Integer id, @RequestBody DiscountCode dicountCode) {
		return codeService.update(dicountCode);
	}
	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") Integer id) {
		codeService.deleteDiscountCode(id);
		codeService.delete(id);
	}
	
	
}
