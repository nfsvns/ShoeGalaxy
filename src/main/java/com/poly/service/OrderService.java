package com.poly.service;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.fasterxml.jackson.databind.JsonNode;
import com.poly.entity.Order;
import com.poly.entity.OrderDetail;
import com.poly.entity.Product;


public interface OrderService {
	public List<Order> findAll() ;
	public Order create(JsonNode orderData) ;
	
	public Order findById(Long id) ;
	
	public List<Order> findByUsername(String username) ;
	
	public Order update(Order order) ;

	public void delete(Long id) ;
	
	public void deleteOrderDetailByOrderId(Long id);

	public List<Order> getCurrentUserOrders();

	public List<OrderDetail> getDetailDataById(Long id);

}
