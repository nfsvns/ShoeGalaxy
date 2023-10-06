package com.poly.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.context.annotation.SessionScope;

import com.poly.entity.Product;
import com.poly.entity.Report;

@SessionScope
public interface ProductDAO extends JpaRepository<Product, Integer> {

	Page<Product> findByPriceBetween(Double minPrice, Double maxPrice, Pageable pageable);

	@Query("SELECT o FROM Product o WHERE o.category.name LIKE ?1 and o.available = True ")
	Page<Product> findByBrand(String brand, Pageable pageable);

	// tim kiem
	@Query("SELECT o FROM Product o WHERE o.name LIKE ?1 and o.available = True")
	Page<Product> findByKeywords(String keywords, Pageable pageable);

	@Query("SELECT new Report(o.category.name, sum(o.price), count(o) )" + " FROM Product o"
			+ " GROUP BY o.category.name" + " ORDER BY sum(o.price) DESC")
	List<Report> getInventoryByCategory();
	
	@Query("SELECT p FROM Product p WHERE p.category.id=?1 and p.available = True")
	List<Product> findByCategoryId(String cid);

	@Query("SELECT p FROM Product p ORDER BY p.name ASC ")
	List<Product> sortProductAS();

	@Query("SELECT p FROM Product p ORDER BY p.name Desc")
	List<Product> sortProductDesc();

	@Query("SELECT p FROM Product p ORDER BY p.price ASC")
	List<Product> sortPriceLowToHight();

	@Query("SELECT p FROM Product p ORDER BY p.price DESC")
	List<Product> sortPriceHightToLow();

	@Query("SELECT COUNT(p) FROM Product p WHERE p.category.id = 'M' and p.available = True")
	Integer countMlBProducts();
	
	@Query("SELECT COUNT(p) FROM Product p WHERE p.category.id = 'AD' and p.available = True")
	Integer countADProducts();
	
	@Query("SELECT COUNT(p) FROM Product p WHERE p.category.id = 'NK' and p.available = True")
	Integer countNKProducts();
	
	
	@Query("SELECT p FROM Product p WHERE p.available = True")
	Page<Product> findDelete(Pageable pageable);
}
