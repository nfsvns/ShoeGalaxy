package com.poly.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.entity.Order;
import com.poly.service.OrderService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/historys")
public class OrderRestController {
	
	@Autowired
	OrderService orderService;
	
	@GetMapping
	public List<Order> getAll() {
		return orderService.findAll();
	}
	@GetMapping("{id}")
	public Order getOne(@PathVariable("id") Long id) {
		return orderService.findById(id);
	}
	
	@PutMapping("{id}")
	public Order put(@PathVariable("id") Long id, @RequestBody Order order) {
		return orderService.update(order);
	}
	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") Long id) {
		orderService.deleteOrderDetailByOrderId(id);
		orderService.delete(id);
	}

}
