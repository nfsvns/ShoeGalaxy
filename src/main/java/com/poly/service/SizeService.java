package com.poly.service;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

import com.poly.entity.Size;

public interface SizeService {
	public List<Size> findAll();

	public Size create(JsonNode sizeData);

	public Size findById(Integer id);

	public Size create(Size size);

	public List<Size> findByCode(Integer id);

	public Size update(Size discountCode);

	public void delete(Integer id);

	public void deleteDiscountCode(Integer id);
}
