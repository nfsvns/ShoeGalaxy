package com.poly.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.entity.Image;

public interface ImageDAO extends JpaRepository<Image, Integer> {
	
	@Query("SELECT i FROM Image i WHERE i.product.id = :productId")
	List<Image> findByProductId(Integer productId);
	
}
