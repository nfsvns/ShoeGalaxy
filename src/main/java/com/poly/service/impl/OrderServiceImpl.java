package com.poly.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.poly.dao.OrderDAO;
import com.poly.dao.OrderDetailDAO;
import com.poly.entity.Order;
import com.poly.entity.Product;
import com.poly.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	OrderDAO dao;
	@Autowired
	OrderDetailDAO detaildao;
	@Override
	public List<Order> findAll() {
		return dao.findAll();
	}

	@Override
	public Order create(JsonNode orderData) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order findById(Long id) {
		return dao.findById(id).get();
	}

	@Override
	public List<Order> findByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void delete(Long id) {
		dao.deleteById(id);
	}

	@Override
	public Order update(Order order) {
		return dao.save(order);
	}

	@Override
	public void deleteOrderDetailByOrderId(Long id) {
		// TODO Auto-generated method stub
		detaildao.deleteById(id);
	}

}
