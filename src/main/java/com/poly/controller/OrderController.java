package com.poly.controller;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.dao.AccountDAO;
import com.poly.dao.DiscountCodeDAO;
import com.poly.dao.OrderDAO;
import com.poly.dao.OrderDetailDAO;
import com.poly.dao.ProductDAO;
import com.poly.dao.ShoppingCartDAO;
import com.poly.dao.SizeDAO;
import com.poly.entity.Account;
import com.poly.entity.DiscountCode;
import com.poly.entity.MailInfo;
import com.poly.entity.Order;
import com.poly.entity.OrderDetail;
import com.poly.entity.Product;
import com.poly.entity.Size;
import com.poly.service.MailerService;
import com.poly.service.SessionService;

@Controller
public class OrderController {
	@Autowired
	MailerService mailerService;
	@Autowired
	SessionService sessionService;
	@Autowired
	ShoppingCartDAO shoppingCartDAO;
	@Autowired
	OrderDAO orderDAO;
	@Autowired
	OrderDetailDAO orderDetailDAO;
	@Autowired
	ProductDAO productDAO;

	@Autowired
	AccountDAO accountDAO;
	@Autowired
	DiscountCodeDAO dcDAO;

	@Autowired
	SizeDAO sizeDAO;

	@RequestMapping("/check")
	public String checkout(Model model, @RequestParam(value = "totalAmount", required = false) String totalAmount) {
		model.addAttribute("cartItems", shoppingCartDAO.getAll());
		model.addAttribute("total", shoppingCartDAO.getAmount());
		return "checkout.html";
	}

	@RequestMapping("/searchCodee")
	public String searchDiscountCode(Model model, @RequestParam(value = "code", required = false) String code,
			@RequestParam(value = "totalAmount", required = false) String totalAmount) {
		List<DiscountCode> discountCodes = new ArrayList<>();

		if (code != null && !code.isEmpty()) {
			discountCodes = dcDAO.findBykeyword(code);

			// Kiểm tra ngày hết hạn
			LocalDate currentDate = LocalDate.now();
			discountCodes
					.removeIf(dc -> dc.getExpirationDate() != null && dc.getExpirationDate().isBefore(currentDate));

			if (!discountCodes.isEmpty()) {
				// Tìm thấy mã giảm giá, tính toán giá trị mới

				double cartAmount = Double.parseDouble(totalAmount); // Thay thế bằng giá trị thực tế từ HTML
				double discountAmount = discountCodes.get(0).getDiscountAmount(); // Thay thế bằng phần trăm thực tế

				// Tính toán giá trị mới
				double calculatedValue = cartAmount - (cartAmount * (discountAmount / 100.0));

				// Truyền giá trị mới vào view
				model.addAttribute("calculatedValue", calculatedValue);
				model.addAttribute("cartAmount", cartAmount);
				model.addAttribute("messages", "Áp dụng mã giảm giá thành công!");
			} else {
				// Không tìm thấy mã giảm giá hoặc đã hết hạn
				model.addAttribute("messages", "Mã giảm giá không hợp lệ hoặc đã hết hạn.");
			}
		}

		model.addAttribute("code", code);
		model.addAttribute("discountCodes", discountCodes);
		return "checkout.html";
	}

	@PostMapping("checkout.html")
	public String checkout1(Model model, @RequestParam String address, @RequestParam String[] productId,
			@RequestParam String[] sizeId, @RequestParam String[] countProduct, @RequestParam String email,
			@RequestParam String fullname, @RequestParam double total, HttpServletRequest request,

			@RequestParam(value = "productId", required = false) List<Integer> productID,
			@RequestParam(value = "sizeId", required = false) List<Integer> size,
			@RequestParam(value = "countProduct", required = false) List<Integer> count) {
		boolean allProductsEnough = true; // Biến để theo dõi xem tất cả sản phẩm có đủ số lượng không

		// Tạo một danh sách để lưu trạng thái kiểm tra số lượng của từng sản phẩm
		List<Boolean> productStatus = new ArrayList<>();

		for (int i = 0; i < productID.size(); i++) {
			Integer id = productID.get(i);
			Integer idSize = size.get(i);
			Integer countedQuantity = count.get(i);

			// Tìm số lượng (quantity) theo productId và sizeId
			Integer quantity = sizeDAO.findQuantityByProductIdAndSize(id, idSize);
			System.out.println(quantity);

			if (quantity != null) {
				// Kiểm tra xem số lượng có đủ để trừ không
				if (quantity >= countedQuantity) {
					// Sản phẩm này đủ số lượng
					productStatus.add(true);
				} else {
					// Sản phẩm này không đủ số lượng
					productStatus.add(false);
					allProductsEnough = false; // Đặt biến này thành false nếu ít nhất một sản phẩm không đủ
				}
			} else {
				// Xử lý nếu không tìm thấy thông tin sản phẩm (ví dụ: throw một Exception hoặc
				// xử lý lỗi khác)
				productStatus.add(false);
				allProductsEnough = false; // Đặt biến này thành false nếu có lỗi xảy ra
			}
		}

		if (allProductsEnough) {
			// Nếu tất cả sản phẩm có đủ số lượng, thực hiện cập nhật cho tất cả sản phẩm
			for (int i = 0; i < productID.size(); i++) {
				Integer id = productID.get(i);
				Integer idSize = size.get(i);
				Integer countedQuantity = count.get(i);

				// Trừ số lượng
				Integer quantity = sizeDAO.findQuantityByProductIdAndSize(id, idSize);
				Integer remainingQuantity = quantity - countedQuantity;

				// Cập nhật số lượng mới vào bảng Size
				sizeDAO.updateQuantityByProductIdAndSize(id, idSize, remainingQuantity);
			}
			/* return "thankyou"; */ // Chuyển hướng đến trang thành công hoặc trang bạn muốn
		} else {
			// Nếu ít nhất một sản phẩm không đủ số lượng, hiển thị thông báo hoặc xử lý lỗi
			model.addAttribute("messages",
					"Số lượng đơn giày của bạn muốn mua lớn hơn số lượng tồn kho cho ít nhất một sản phẩm!");
		}

		//// GỬI MAIL ////
		System.out.println(email);
		MailInfo mail = new MailInfo();
		mail.setTo(email);
		mail.setSubject("Đơn hàng của bạn đã đặt thành công");

		// Tạo nội dung email
		StringBuilder bodyBuilder = new StringBuilder();
		bodyBuilder.append("Tổng hóa đơn của ").append(fullname).append(" là: $").append(total).append(" tại địa chỉ: ")
				.append(address).append("<br><br>");

		// Tạo bảng với CSS
		bodyBuilder.append("<table style=\"border-collapse: collapse;\">");
		bodyBuilder.append(
				"<tr><th style=\"border: 1px solid black; padding: 8px;\">Sản phẩm</th><th style=\"border: 1px solid black; padding: 8px;\">Số lượng</th><th style=\"border: 1px solid black; padding: 8px;\">Giá</th><th style=\"border: 1px solid black; padding: 8px;\">Tổng cộng</th></tr>");

		// Lấy thông tin chi tiết của từng sản phẩm trong giỏ hàng và thêm vào bảng
		for (int i = 0; i < productId.length; i++) {
			Product product = productDAO.findById(Integer.parseInt(productId[i])).get();
			int quantity = Integer.parseInt(countProduct[i]);

			bodyBuilder.append("<tr>");
			bodyBuilder.append("<td style=\"border: 1px solid black; padding: 8px; text-align: center;\">")
					.append(product.getName()).append("</td>");
			bodyBuilder.append("<td style=\"border: 1px solid black; padding: 8px; text-align: center;\">")
					.append(quantity).append("</td>");
			bodyBuilder.append("<td style=\"border: 1px solid black; padding: 8px; text-align: center;\">").append("$")
					.append(product.getPrice()).append("</td>");
			bodyBuilder.append("<td style=\"border: 1px solid black; padding: 8px; text-align: center;\">").append("$")
					.append(product.getPrice() * quantity).append("</td>");
			bodyBuilder.append("</tr>");
		}

		bodyBuilder.append("</table>");
		mail.setBody(bodyBuilder.toString());

		mailerService.queue(mail);

		//// ADD Order ////
		Order order = new Order();
		Timestamp now = new Timestamp(new Date().getTime());
		String username = request.getRemoteUser();
		Account user = accountDAO.findById(username).orElse(null);
		order.setCreateDate(now);
		order.setAddress(address);
		order.setAccount(user);
		order.setNguoinhan(fullname);
		order.setTongtien(total);
		Order newOrder = orderDAO.saveAndFlush(order);

		//// ADD OrderDetail ////
		for (int i = 0; i < productId.length; i++) {
			Product product = productDAO.findById(Integer.parseInt(productId[i])).get();
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setOrder(newOrder);
			orderDetail.setProduct(product);
			orderDetail.setSize(Integer.parseInt(sizeId[i]));
			orderDetail.setPrice(product.getPrice());
			orderDetail.setQuantity(Integer.parseInt(countProduct[i]));
			orderDetailDAO.save(orderDetail);
		}
		request.getSession().removeAttribute("cart");

		return "redirect:/thankyou.html";

	}

	///// THANKYOU /////
	@RequestMapping("thankyou.html")
	public String thankyou() {
		sessionService.setAttribute("cartQuantity", shoppingCartDAO.getCount());
		return "thankyou";
	}
}
