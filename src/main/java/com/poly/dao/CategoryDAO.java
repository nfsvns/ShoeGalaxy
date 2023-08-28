package com.poly.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.entity.Category;
import com.poly.entity.Product;

public interface CategoryDAO extends JpaRepository<Category, String> {
	@Query("SELECT SUM(p.quantity) FROM Category c JOIN c.products p WHERE c.name = ?1")
	Long countProductsByCategory(String categoryName);
}
