package com.poly.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.poly.entity.Image;
import com.poly.entity.Size;

public interface ImageService {

	public List<Image> getImageByProductId(Integer productId);
	
	public List<Image> findAll();

	public Image create(JsonNode sizeData);
	
	public Image create(Image image);

	public Image findById(Integer id);

	public Image update(Image image);

	public void delete(Integer id);
	
	public List<Image> findByProductId(Integer id);
	
}
