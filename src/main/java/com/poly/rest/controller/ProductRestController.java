package com.poly.rest.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.poly.entity.Product;
import com.poly.service.ProductService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/products")
public class ProductRestController {
	@Autowired
	ProductService productService;
	
	@GetMapping
	public List<Product> getAll() {
		return productService.findAll();
	}
	@GetMapping("{id}")
	public Product getOne(@PathVariable("id") Integer id) {
		return productService.findById(id);
	}
	@PostMapping
	public Product post(@RequestBody Product product) {
		productService.create(product);
		return product;
	}
	@RequestMapping(value = "{id}", method = {RequestMethod.PUT, RequestMethod.DELETE})
	public Product putOrDelete(@PathVariable("id") Integer id, @RequestBody Product product, 
			HttpServletRequest request) {
	    if (request.getMethod().equals(RequestMethod.DELETE.toString())) {
	    	return productService.deletu(product);
	}
	    else if (request.getMethod().equals(RequestMethod.PUT.toString())) {
	    	return productService.update(product);
	}
		return product;
	    
	}
}
