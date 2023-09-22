package com.poly.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.dao.DiscountCodeDAO;
import com.poly.entity.Category;
import com.poly.entity.DiscountCode;
import com.poly.entity.Product;

@Controller
public class ProductDiscountsController {
	@Autowired
	DiscountCodeDAO dcDAO;

	@GetMapping("/discount")
	public String homeIndex(Model model, @RequestParam("p") Optional<Integer> p) {
		DiscountCode item = new DiscountCode();
		model.addAttribute("item", item);
		Pageable pageable = PageRequest.of(p.orElse(0), 6);
		Page<DiscountCode> page = dcDAO.findAll(pageable);
		model.addAttribute("discount", page);
		return "discount";
	}

	@RequestMapping("/discount/edit-discount/{id}")
	public String edit(Model model, @PathVariable("id") Integer id, @RequestParam("p") Optional<Integer> p) {
		DiscountCode item = dcDAO.findById(id).orElse(new DiscountCode()); // Handle the case when item is not found

		// Truyền giá trị expirationDate vào mô hình
		model.addAttribute("item", item);

		List<DiscountCode> items = dcDAO.findAll();
		model.addAttribute("discount", items);

		Pageable pageable = PageRequest.of(p.orElse(0), 6);
		Page<DiscountCode> page = dcDAO.findAll(pageable);
		model.addAttribute("discount", page);

		return "discount";
	}

	@RequestMapping("/discount/delete-discount/{id}")
	public String delete(@PathVariable("id") Integer id) {
		dcDAO.deleteById(id);
		return "redirect:/discount";
	}

	@PostMapping("/discount/update-discount")
	public String updateDiscount(@ModelAttribute("item") @Valid DiscountCode item, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "edit-discount";
		}

		// Xử lý cập nhật dữ liệu vào cơ sở dữ liệu
		try {
			dcDAO.save(item);
		} catch (Exception e) {
			// Xử lý lỗi nếu cần thiết
			e.printStackTrace();
			model.addAttribute("error", "Lỗi cập nhật dữ liệu");
			return "edit-discount";
		}

		return "redirect:/discount"; // Chuyển hướng đến trang danh sách sau khi cập nhật thành công
	}

	@RequestMapping("/discount/create-discount")
	public String saveDiscount(Model model, @RequestParam String code, @RequestParam Double discountAmount,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate expirationDate) {
		// Check if the discount code already exists
		DiscountCode existingDiscountCode = dcDAO.findByCode(code);

		if (existingDiscountCode != null) {
			// If the discount code already exists, show an error message
			model.addAttribute("errorMessage", "Mã giảm giá đã tồn tại.");
		
		} else {
			// If the discount code doesn't exist, create a new one
			DiscountCode discountCode = new DiscountCode();
			discountCode.setCode(code);
			discountCode.setDiscountAmount(discountAmount);
			discountCode.setExpirationDate(expirationDate);
			dcDAO.save(discountCode);
			model.addAttribute("successMessage", "Mã giảm giá đã được tạo thành công.");
			
		}
		return "redirect:/discount";
	}

	@RequestMapping("/searchCode")
	public String searchDiscountCode(Model model, @RequestParam(value = "code", required = false) String code) {
		List<DiscountCode> discountCodes = new ArrayList();

		if (code != null && !code.isEmpty()) {
			discountCodes = dcDAO.findBykeyword(code);
		}
		model.addAttribute("code", code);
		model.addAttribute("discountCodes", discountCodes);
		return "searchCode.html";
	}

}
