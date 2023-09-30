package com.poly.service;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.poly.entity.DiscountCode;
import com.poly.entity.Discount_Sale;

public interface DiscountSaleService {
	public List<Discount_Sale> findAll();

	public Discount_Sale create(JsonNode Discount_Sale);

	public Discount_Sale findById(Integer id);

	public Discount_Sale create(Discount_Sale discount_Sale);

	public List<Discount_Sale> findByCode(Integer id);

	public Discount_Sale update(Discount_Sale discount_Sale);

	public void delete(Integer id);

	public void deleteDiscountCode(Integer id);
}
