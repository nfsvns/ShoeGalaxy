package com.poly.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.entity.Size;

public interface SizeDAO extends JpaRepository<Size, Integer> {

	@Query("Select o From Size o where o.product.id = ?1")
	List<Size> findByIdProduct(Integer keywords);

	@Query("SELECT s.quantity FROM Size s WHERE s.product.id = ?1 AND s.sizes = ?2")
	Integer findQuantityByProductIdAndSize(Integer productId, Integer size);

	@Modifying
	@Transactional
	@Query("UPDATE Size s SET s.quantity = :quantity WHERE s.product.id = :productId AND s.sizes = :sizeId")
	void updateQuantityByProductIdAndSize(Integer productId, Integer sizeId, Integer quantity);

}
