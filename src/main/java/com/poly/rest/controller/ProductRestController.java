package com.poly.rest.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.poly.entity.DiscountProduct;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poly.entity.Image;
import com.poly.entity.Product;
import com.poly.service.DiscountProductService;
import com.poly.service.ImageService;
import com.poly.service.ProductService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/products")
public class ProductRestController {
	@Autowired
	ProductService productService;
	@Autowired
	ImageService imageService;
	@Autowired
	DiscountProductService dcService;

	@GetMapping
	public List<Product> getAll() {
		return productService.findAll();
	}

	@GetMapping("{id}")
	public Product getOne(@PathVariable("id") Integer id) {
		return productService.findById(id);
	}
	

	@GetMapping("/{id}/images")
	public List<Image> getProductImages(@PathVariable("id") Integer id) {
		// Gọi service để lấy danh sách hình ảnh dựa trên ID sản phẩm
		return imageService.getImageByProductId(id);
	}

	@GetMapping("/{id}/price")
	public List<DiscountProduct> getProductPrice(@PathVariable("id") Integer id) {
		List<DiscountProduct> allDiscountProducts = dcService.findByIdProductDiscount(id);
		LocalDate currentDate = LocalDate.now();

		List<DiscountProduct> validDiscountProducts = allDiscountProducts.stream().filter(discountProduct -> {
			LocalDate discountStartDate = discountProduct.getStart_Date();
			LocalDate discountEndDate = discountProduct.getEnd_Date();

			boolean isStartToday = currentDate.isEqual(discountStartDate);
			boolean isEndToday = currentDate.isEqual(discountEndDate);

			return isStartToday || isEndToday
					|| (currentDate.isAfter(discountStartDate) && currentDate.isBefore(discountEndDate));
		}).collect(Collectors.toList());

		for (DiscountProduct validDiscountProduct : validDiscountProducts) {
			validDiscountProduct.setPercentage(validDiscountProduct.getPercentage());
		}
		return validDiscountProducts;
	}

	@PostMapping
	public Product post(@RequestBody Product product) {
		productService.create(product);
		return product;
	}

	
	@RequestMapping(value = "{id}", method = {RequestMethod.PUT, RequestMethod.DELETE})
	public Product putOrDelete(@PathVariable("id") Integer id, @RequestBody Product product, 
			HttpServletRequest request) {
		if (request.getMethod().equals(RequestMethod.DELETE.toString())) {
			return productService.deletu(product);
		} else if (request.getMethod().equals(RequestMethod.PUT.toString())) {
			return productService.update(product);
		}
		return product;

	}
	/*
	@DeleteMapping("{id}/real") 
	public void deleteProduct(@PathVariable("id") Integer id) {
	    productService.DeleteProductAndRelatedData(id);
	}
	*/
           	@GetMapping("/counts")
    	public ResponseEntity<Map<String, Integer>> getProductCounts() {
		Map<String, Integer> productCounts = new HashMap<>();
		productCounts.put("MLB", productService.countMlBProducts());
		productCounts.put("ADIDAS", productService.countADProducts());
		productCounts.put("NIKE", productService.countNKProducts());
		return ResponseEntity.ok(productCounts);
	}

	@GetMapping("/quantities")
	public ResponseEntity<List<Product>> getProductQuantities() {
		List<Object[]> productQuantities = productService.getProductQuantity();
		List<Product> productDTOs = new ArrayList<>();

		for (Object[] row : productQuantities) {
			Product productDTO = new Product();
			productDTO.setId((Integer) row[0]);
			productDTO.setName((String) row[1]);
			productDTO.setQuantity((Integer) row[2]);
			productDTOs.add(productDTO);
		}

		return ResponseEntity.ok(productDTOs);
	}

}
