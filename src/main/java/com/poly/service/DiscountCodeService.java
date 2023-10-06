package com.poly.service;

import java.util.List;



import com.fasterxml.jackson.databind.JsonNode;
import com.poly.entity.DiscountCode;
import com.poly.entity.Product;


public interface DiscountCodeService {
	public List<DiscountCode> findAll();

	public DiscountCode create(JsonNode discountData);

	public DiscountCode findById(Integer id);
	public DiscountCode create(DiscountCode discountCode) ;

	public List<DiscountCode> findByCode(Integer id);

	public DiscountCode update(DiscountCode discountCode);

	public void delete(Integer id);

	public void deleteDiscountCode(Integer id);

}
