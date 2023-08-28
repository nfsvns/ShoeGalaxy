package com.poly.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.poly.entity.Size;

public interface SizeDAO extends JpaRepository<Size, Integer> {

	@Query("Select o From Size o where o.product.id = ?1")
	List<Size> findByIdProduct(Integer keywords);
}
