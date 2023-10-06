package com.poly.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.poly.entity.Image;

public interface ImageService {

	public List<Image> getImageByProductId(Integer productId);
	
}
