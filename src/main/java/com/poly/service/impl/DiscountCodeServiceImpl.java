
package com.poly.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.poly.dao.DiscountCodeDAO;
import com.poly.entity.DiscountCode;
import com.poly.entity.Product;
import com.poly.service.DiscountCodeService;

@Service
public class DiscountCodeServiceImpl implements DiscountCodeService {
	
	@Autowired
	DiscountCodeDAO dao ;

	@Override
	public List<DiscountCode> findAll() {
		// TODO Auto-generated method stub
		return dao.findAll();
	}

	@Override
	public DiscountCode create(JsonNode discountData) {
		// TODO Auto-generated m
		return null;
	}

	@Override
	public DiscountCode findById(Integer id ) {
		// TODO Auto-generated method stub
		return dao.findById(id).get();
	}

	@Override
	public List<DiscountCode> findByCode(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DiscountCode update(DiscountCode discountCode) {
		// TODO Auto-generated method stub
		
		return dao.save(discountCode);
	}

	@Override
	public void delete(Integer id) {
		dao.deleteById(id);
		
	}

	@Override
	public void deleteDiscountCode(Integer id) {
		dao.deleteById(id);
		
	}

	@Override
	public DiscountCode create(DiscountCode discountCode) {
		// TODO Auto-generated method stub
		return dao.save(discountCode);
	} 
	
	
}
