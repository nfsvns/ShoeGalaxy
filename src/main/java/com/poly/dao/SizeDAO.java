
package com.poly.dao;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.entity.Size;

public interface SizeDAO extends JpaRepository<Size, Integer> {

	@Query("Select o From Size o where o.product.id = ?1")
	List<Size> findByIdProduct(Integer keywords);

	@Query("SELECT SUM(s.quantity) FROM Size s WHERE s.product.id = ?1 AND s.sizes = ?2")
	Integer findTotalQuantityByProductIdAndSize(Integer productId, String size);
    
	// Correct signature: idSize is not wrapped in Optional
	@Modifying
	@Transactional
	@Query("UPDATE Size s SET s.quantity = :quantity WHERE s.product.id = :productId AND s.sizes = :idSize")
	void updateQuantityByProductIdAndSize(Integer productId, String idSize, Integer quantity);


	@Query("SELECT s.quantity FROM Size s WHERE s.product.id = ?1 AND s.sizes = ?2")
	Integer findQuantityByProductIdAndSize(Integer id, String idSize);
    
	@Query(value = "SELECT size FROM Sizes WHERE product_id = ?1", nativeQuery = true)
	List<String> findSizeByIdProduct(@Param("productId") int productId);

}
