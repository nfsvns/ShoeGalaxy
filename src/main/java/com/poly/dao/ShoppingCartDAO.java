package com.poly.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.entity.ShoppingCart;

public interface ShoppingCartDAO extends JpaRepository<ShoppingCart, Integer> {

	@Query("SELECT s FROM ShoppingCart s WHERE s.account.username LIKE ?1")
	List<ShoppingCart> findShoppingCartsByUsername(String username);

	@Query("SELECT s FROM ShoppingCart s WHERE s.product.id like ?1 and s.account.id like ?2 and s.size like ?3")
	ShoppingCart findShoppingCartByProductIdAndUsernameAndSize(Integer id, String username, String size);

	@Modifying
	@Query("DELETE FROM ShoppingCart s WHERE s.account.username = :username")
	void deleteAllByUsername(@Param("username") String username);
	
	@Modifying
	@Query("SELECT s FROM ShoppingCart s WHERE s.account.username like ?1 and s.status = true")
	List<ShoppingCart> findByUsernameWithStatus(@Param("username") String username);
	
}