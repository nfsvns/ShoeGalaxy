package com.poly.rest.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.entity.Image;
import com.poly.entity.Product;
import com.poly.service.ImageService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/images")
public class ImageRestController {

	@Autowired
	ImageService imageService;
	
	
	@GetMapping
	public List<Image> getAll() {
		return imageService.findAll();
	}

	@GetMapping("{id}")
	public Image getOne(@PathVariable("id") Integer id) {
		return imageService.findById(id);
	}
	
	@GetMapping("/products/{id}")
	public List<Image> getByProductId(@PathVariable("id") Integer id){
		return imageService.findByProductId(id);
	}

	@PostMapping
	public Image post(@RequestBody Image image) {
		imageService.create(image);
		return image;
	}

	@PutMapping("{id}")
	public Image put(@PathVariable("id") Integer id, @RequestBody Image image) {
		return imageService.update(image);
	}

	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") Integer id) {
		imageService.delete(id);
	}
	
}
