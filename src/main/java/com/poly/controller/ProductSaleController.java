package com.poly.controller;

import java.time.LocalDate;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import com.poly.dao.DiscountSaleDAO;
import com.poly.dao.ProductDAO;
import com.poly.entity.DiscountCode;

import com.poly.entity.Discount_Sale;

@Controller
public class ProductSaleController {
	@Autowired
	DiscountSaleDAO dsDAO;

	@Autowired
	ProductDAO prDAO;

	@RequestMapping("/discountsales")
	public String fillAll(Model model) {
		model.addAttribute("discountSales", dsDAO.findAll());
		Discount_Sale item = new Discount_Sale();
		model.addAttribute("item", item);

		// Thêm danh sách sản phẩm vào mô hình
		model.addAttribute("products", prDAO.findAll());

		return "discount_Sale";
	}

	@RequestMapping("/discountsales/add")
	public String addDiscountSale(@ModelAttribute("discount_sales") Discount_Sale discount_Sale, Model model) {
		LocalDate startDate = discount_Sale.getStartDate();
		LocalDate endDate = discount_Sale.getEndDate();

		if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
			// Ngày bắt đầu lớn hơn ngày kết thúc, hiển thị thông báo lỗi
			model.addAttribute("error", "Ngày bắt đầu phải nhỏ hơn hoặc bằng ngày kết thúc.");
			return "redirect:/discountsales"; // hoặc trả về trang lỗi cụ thể khác
		}

		dsDAO.save(discount_Sale);
		return "redirect:/discountsales";
	}

	@RequestMapping("/discountsales/update")
	public String updateDiscountSale(@ModelAttribute("discount_sales") @Valid Discount_Sale item, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			return "discount_Sale";
		}

		// Xử lý cập nhật dữ liệu vào cơ sở dữ liệu
		try {
			dsDAO.save(item);
		} catch (Exception e) {
			// Xử lý lỗi nếu cần thiết
			e.printStackTrace();
			model.addAttribute("error", "Lỗi cập nhật dữ liệu");
			return "discount_Sale";
		}

		return "redirect:/discountsales"; // Chuyển hướng đến trang danh sách sau khi cập nhật thành công
	}

	@RequestMapping("/discountsales/clear")
	public String reset(Model model) {
		Discount_Sale entity = new Discount_Sale();
		fillAll(model);
		return "discount_Sale";
	}

	@GetMapping("/discountsales/edit/{id}")
	public String showEditForm(@PathVariable("id") int id, Model model) {
		Discount_Sale item = dsDAO.findById(id).get();
		model.addAttribute("item", item);
		model.addAttribute("discountSales", dsDAO.findAll());


		return "discount_Sale";
	}

	@GetMapping("/discountsales/delete/{id}")
	public String deleteDiscountSale(@PathVariable("id") int id) {
		dsDAO.deleteById(id);
		return "redirect:/discountsales";
	}
}
