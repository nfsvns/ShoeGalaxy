package com.poly.rest.controller;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.entity.Order;
import com.poly.entity.OrderDetail;
import com.poly.service.OrderService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/historys")
public class OrderRestController {
	
	@Autowired
	OrderService orderService;
	
	@GetMapping
	public List<Order> getAll() {
		List<Order> orders = orderService.findAll();
		orders.sort(Comparator.comparing(Order::getId).reversed());
		return orders;
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
	@GetMapping("/details/{id}")
	public ResponseEntity<List<OrderDetail>> getHistoryDetail(@PathVariable Long id) {
		List<OrderDetail> detailData = orderService.getDetailDataById(id);
	    if(detailData != null) {
	    	return new ResponseEntity<>(detailData, HttpStatus.OK);
	    }else {
	    	return new ResponseEntity<>(detailData, HttpStatus.NOT_FOUND);
	    }
	}

}
