package com.poly.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poly.entity.Category;
import com.poly.entity.Product;

public interface CategoryService {

	public List<Category> findAll();

	public Category findById(String id);

	public Category create(Category category);

	public Category update(Category category);

	public void delete(String id);

	public Category delete(Category category);

	

}
