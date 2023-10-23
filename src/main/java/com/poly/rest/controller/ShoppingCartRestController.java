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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poly.entity.Product;
import com.poly.entity.ShoppingCart;
import com.poly.service.ShoppingCartService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/carts")
public class ShoppingCartRestController {

	@Autowired
	private ShoppingCartService shoppingCartService;

	@GetMapping
	public List<ShoppingCart> findAll() {
		return shoppingCartService.findAll();
	}

	@GetMapping("{id}")
	public ShoppingCart findById(@PathVariable("id") int id) {
		return shoppingCartService.findById(id);
	}

	@GetMapping("/username/{username}")
	public List<ShoppingCart> findByUsername(@PathVariable("username") String username) {
		return shoppingCartService.findByUsername(username);
	}

	@GetMapping("/product/{username}/{id}/{size}")
	public ShoppingCart findByProductIdAndUsernameAndSize(@PathVariable("username") String username,
			@PathVariable("id") Integer id, @PathVariable("size") Integer size) {
		return shoppingCartService.findByProductIdAndUsernameAndSize(id, username, size);
	}

	@PostMapping
	public ShoppingCart save(@RequestBody ShoppingCart shoppingCart) {
		shoppingCartService.save(shoppingCart);
		return shoppingCart;
	}

	@PutMapping("{id}")
	public void put(@PathVariable("id") Integer id, @RequestBody ShoppingCart shoppingCart) {
		shoppingCartService.update(shoppingCart);
	}

	@DeleteMapping("{id}")
	public String deleteById(@PathVariable("id") Integer id) {
		shoppingCartService.deleteById(id);
		return "Deleted shopping cart with id: " + id;
	}

	@DeleteMapping("/all/{username}")
	public void deleteAll(@PathVariable("username") String username) {
		shoppingCartService.deleteAllByUsername(username);
	}
	
}