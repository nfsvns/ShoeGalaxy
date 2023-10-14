package com.poly.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.entity.DiscountProduct;
import com.poly.entity.Image;
import com.poly.entity.Product;
import com.poly.entity.Size;

public interface DiscountProductDAO extends JpaRepository<DiscountProduct, Integer> {
	List<DiscountProduct> findByProduct(Product product);

	@Query("SELECT p FROM DiscountProduct p where p.product.id = ?1")
	List<DiscountProduct> findByIdProduct(Integer keywords);
	
	
	@Query("SELECT i FROM DiscountProduct i WHERE i.product.id = :productId")
	List<DiscountProduct>  findByIdProductDiscount(Integer productId);
}
