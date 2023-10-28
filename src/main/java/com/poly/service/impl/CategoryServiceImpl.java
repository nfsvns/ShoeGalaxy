package com.poly.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.dao.CategoryDAO;
import com.poly.entity.Category;
import com.poly.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	CategoryDAO dao;

	@Override
	public List<Category> findAll() {
		// TODO Auto-generated method stub
		return dao.findAll();
	}

	@Override
	public Category findById(String id) {
		// TODO Auto-generated method stub
		return dao.findById(id).get();
	}

	@Override
	public Category create(Category category) {
		// TODO Auto-generated method stub
		return dao.save(category);
	}

	@Override
	public Category update(Category category) {
		// TODO Auto-generated method stub
		return dao.save(category);
	}

	@Override
	public void delete(String id) {
		dao.deleteById(id);

	}

}
