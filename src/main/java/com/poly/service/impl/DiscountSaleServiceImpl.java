package com.poly.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.poly.dao.DiscountSaleDAO;
import com.poly.entity.Discount_Sale;
import com.poly.service.DiscountSaleService;

@Service
public class DiscountSaleServiceImpl implements DiscountSaleService {
	@Autowired
	DiscountSaleDAO discountDAO;

	@Override
	public List<Discount_Sale> findAll() {
		// TODO Auto-generated method stub
		return discountDAO.findAll();
	}

	@Override
	public Discount_Sale create(JsonNode Discount_Sale) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Discount_Sale findById(Integer id) {
		// TODO Auto-generated method stub
		return discountDAO.findById(id).get();
	}

	@Override
	public Discount_Sale create(Discount_Sale discount_Sale) {
		// TODO Auto-generated method stub
		return discountDAO.save(discount_Sale);
	}

	@Override
	public List<Discount_Sale> findByCode(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Discount_Sale update(Discount_Sale discount_Sale) {
		// TODO Auto-generated method stub
		return discountDAO.save(discount_Sale);
	}

	@Override
	public void delete(Integer id) {
		discountDAO.deleteById(id);

	}

	@Override
	public void deleteDiscountCode(Integer id) {
		// TODO Auto-generated method stub

	}

}
