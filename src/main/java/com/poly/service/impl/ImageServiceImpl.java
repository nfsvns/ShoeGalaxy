package com.poly.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
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

	@Override
	public List<Image> findAll() {
		return dao.findAll();
	}

	@Override
	public Image create(JsonNode sizeData) {
		return null;
	}

	@Override
	public Image findById(Integer id) {
		return dao.findById(id).get();
	}

	@Override
	public Image update(Image image) {
		return dao.save(image);
	}

	@Override
	public void delete(Integer id) {
		dao.deleteById(id);
	}

	@Override
	public Image create(Image image) {
		return dao.save(image);
	}

	@Override
	public List<Image> findByProductId(Integer id) {
		return dao.findByProductId(id);
	}

}
