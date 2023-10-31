package com.poly.rest.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.poly.entity.Category;
import com.poly.entity.DiscountCode;
import com.poly.entity.Product;
import com.poly.service.CategoryService;


@CrossOrigin("*")
@RestController
@RequestMapping("/rest/categories")
public class CategoryRestController {
	@Autowired
	CategoryService categoryService;

	@GetMapping
	public List<Category> findAll() {
		return categoryService.findAll();

	}

	@GetMapping("{id}")
	public Category getOne(@PathVariable("id") String id) {

		return categoryService.findById(id);
	}

	@PostMapping
	public Category post(@RequestBody Category category) {
		categoryService.create(category);
		return category;
	}

	/*
	 * @PutMapping("{id}") public Category put(@PathVariable("id") String
	 * id, @RequestBody Category category) { return
	 * categoryService.update(category); }
	 */
	/*
	 * @DeleteMapping("{id}") public void delete(@PathVariable("id") String id) { //
	 * Kiểm tra trước khi xóa Category categoryId= categoryService.findById(id); if
	 * (categoryId != null) { // Kiểm tra xem danh mục có chứa sản phẩm không if
	 * (categoryId.getProducts() != null && !categoryId.getProducts().isEmpty()) {
	 * 
	 * } else { // Nếu không có sản phẩm, thực hiện xóa categoryService.delete(id);
	 * } } }
	 */

	@RequestMapping(value = "{id}", method = { RequestMethod.PUT, RequestMethod.DELETE })
	public Category putOrDelete(@PathVariable("id") String id, @RequestBody Category category,
			HttpServletRequest request) {
		if (request.getMethod().equals(RequestMethod.DELETE.toString())) {
			return categoryService.delete(category);
		} else if (request.getMethod().equals(RequestMethod.PUT.toString())) {
			return categoryService.update(category);
		}
		return category;

	}
}
