//package com.poly.controller;
//
//import java.io.File;
//import java.util.Date;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.poly.dao.CategoryDAO;
//import com.poly.dao.ProductDAO;
//import com.poly.entity.Account;
//import com.poly.entity.Category;
//import com.poly.entity.Product;
//import com.poly.service.SessionService;
//import com.poly.service.UploadService;
//
//@Controller
//public class ProductAdminController2 {
//	
//	
//	@Autowired
//	ProductDAO proDAO;
//	
//	@Autowired
//	CategoryDAO cateDAO;
//	@Autowired
//	SessionService sessionService;
//	@Autowired
//	UploadService uploadService;
//	
//	@RequestMapping("/productAdmin")
//	public String index(Model model, Product item ) {
//		Account user = sessionService.getAttribute("user");
//		if(!user.getAdmin()) {			
//			String error="Khong du quyen truy cap ";
//			return "redirect:/login.html?error="+error;
//		}
//		model.addAttribute("item", item);
//		model.addAttribute("categories", cateDAO.findAll());
//		model.addAttribute("productItems", proDAO.findAll());
//		return "productAdmin2";
//	}
//	@PostMapping("/productAdmin/add")
//	public String addProduct(@ModelAttribute("item") Product product, @RequestParam("selectedCategory") String selectedId,
//	                         @RequestParam("imageFile") MultipartFile imageFile) {
//	    // Lưu ảnh và lấy đường dẫn lưu trữ
//		File file = uploadService.save(imageFile, "/images");
//	    String imagePath = file.getName();
//	    Category categories = null;
//	    List<Category> listCategory = cateDAO.findAll();
//		for (Category category : listCategory) {
//			if (category.getId().equals(selectedId) ) {
//				categories = category;
//				break;
//			}
//		}
//	    product.setImage(imagePath);
//	    product.setCategory(categories);
//	    // Lưu sản phẩm
//	    proDAO.save(product);
//	    return "redirect:/productAdmin";
//	}
//	
//	@RequestMapping("/productAdmin/delete/{id}")
//	public String deleteProduct(@PathVariable("id") Integer  id) {
//		proDAO.deleteById(id);	
//		return "redirect:/productAdmin";
//	}
//	
//	
//	
//
//}
