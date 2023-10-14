package com.poly.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.poly.dao.SizeDAO;
import com.poly.entity.Size;
import com.poly.service.SizeService;

@Service
public class SizeServiceImpl  implements SizeService{
	@Autowired
	SizeDAO sizeDAO;
	
	@Override
	public List<Size> findAll() {
		// TODO Auto-generated method stub
		return sizeDAO.findAll();
	}

	@Override
	public Size create(JsonNode sizeData) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Size findById(Integer id) {
		// TODO Auto-generated method stub
		return sizeDAO.findById(id).get();
	}

	@Override
	public Size create(Size size) {
		// TODO Auto-generated method stub
		return sizeDAO.save(size);
	}

	@Override
	public List<Size> findByCode(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Size update(Size size) {
		// TODO Auto-generated method stub
		return sizeDAO.save(size);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		sizeDAO.deleteById(id);
	}

	@Override
	public void deleteDiscountCode(Integer id) {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public Integer findQuantityByProductIdAndSize(Integer id, Integer size) {
		return sizeDAO.findQuantityByProductIdAndSize(id, size);
		
	}

}
