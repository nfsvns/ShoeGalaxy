package com.poly.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.entity.Category;
import com.poly.entity.DiscountCode;
import com.poly.service.CategoryService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/categories")
public class CategoryRestController {
	@Autowired
	CategoryService Category;

	@GetMapping
	public List<Category> findAll() {
		return Category.findAll();

	}

	@GetMapping("{id}")
	public Category getOne(@PathVariable("id") String id) {

		return Category.findById(id);
	}

	@PostMapping
	public Category post(@RequestBody Category category) {
		Category.create(category);
		return category;
	}

	@PutMapping("{id}")
	public Category put(@PathVariable("id") String id, @RequestBody Category category) {
		return Category.update(category);
	}

	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") String id) {
		// Kiểm tra trước khi xóa
		Category category = Category.findById(id);
		if (category != null) {
			// Kiểm tra xem danh mục có chứa sản phẩm không
			if (category.getProducts() != null && !category.getProducts().isEmpty()) {
				
			} else {
				// Nếu không có sản phẩm, thực hiện xóa
				Category.delete(id);
			}
		}
	}
}
