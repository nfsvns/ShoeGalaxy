package com.poly.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.dao.ImageDAO;
import com.poly.entity.Image;
import com.poly.service.ImageService;

@Service
public class ImageServiceImpl implements ImageService{
	
	private ImageDAO dao;
	
	@Autowired
    public ImageServiceImpl(ImageDAO dao) {
        this.dao = dao;
    }
	
	@Override
	public List<Image> getImageByProductId(Integer productId) {
		return dao.findByProductId(productId);
	}

}
