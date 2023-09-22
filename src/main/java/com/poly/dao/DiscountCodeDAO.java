package com.poly.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.entity.DiscountCode;

public interface DiscountCodeDAO extends JpaRepository<DiscountCode, Integer> {

	@Query("SELECT o FROM DiscountCode o Where o.code=?1")
	List<DiscountCode> findBykeyword(String code);

	DiscountCode findByCode(String code);

}
