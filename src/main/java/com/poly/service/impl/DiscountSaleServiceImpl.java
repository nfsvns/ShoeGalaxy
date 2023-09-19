package com.poly.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
