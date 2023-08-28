package com.poly.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.dao.AccountDAO;
import com.poly.dao.OrderDAO;
import com.poly.dao.OrderDetailDAO;
import com.poly.dao.ProductDAO;
import com.poly.dao.ShoppingCartDAO;
import com.poly.entity.Account;
import com.poly.entity.MailInfo;
import com.poly.entity.Order;
import com.poly.entity.OrderDetail;
import com.poly.entity.Product;
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

	///// ORDER /////
	@RequestMapping("checkout.html")
	public String checkout(Model model, HttpServletRequest request) {
		model.addAttribute("cartItems", shoppingCartDAO.getAll());
		model.addAttribute("total", shoppingCartDAO.getAmount());
//		String username = request.getRemoteUser();
//		model.addAttribute("orders", orderDAO.findByUsername(username));
		return "checkout";
	}

	@PostMapping("checkout.html")
	public String checkout1(Model model, @RequestParam String address, @RequestParam String[] idProduct,
			@RequestParam String[] countProduct, @RequestParam String email, @RequestParam String fullname,
			@RequestParam double total, HttpServletRequest request) {
		//// GỬI MAIL ////
		System.out.println(email);
		MailInfo mail = new MailInfo();
		mail.setTo(email);
		mail.setSubject("Đơn hàng của bạn đã đặt thành công");

		// Tạo nội dung email
		StringBuilder bodyBuilder = new StringBuilder();
		bodyBuilder.append("Tổng hóa đơn của ").append(fullname).append(" là: $").append(total)
				.append(" tại địa chỉ: ").append(address).append("<br><br>");

		// Tạo bảng với CSS
		bodyBuilder.append("<table style=\"border-collapse: collapse;\">");
		bodyBuilder.append(
				"<tr><th style=\"border: 1px solid black; padding: 8px;\">Sản phẩm</th><th style=\"border: 1px solid black; padding: 8px;\">Số lượng</th><th style=\"border: 1px solid black; padding: 8px;\">Giá</th><th style=\"border: 1px solid black; padding: 8px;\">Tổng cộng</th></tr>");

		// Lấy thông tin chi tiết của từng sản phẩm trong giỏ hàng và thêm vào bảng
		for (int i = 0; i < idProduct.length; i++) {
			Product product = productDAO.findById(Integer.parseInt(idProduct[i])).get();
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
		request.getSession().removeAttribute("cart");
		//// ADD Order ////
		Order order = new Order();
		Timestamp now = new Timestamp(new Date().getTime());
		String username = request.getRemoteUser();
		Account user = accountDAO.findById(username).orElse(null);

		order.setCreateDate(now);
		order.setAddress(address);

//		if (user != null) {
//			order.setAccount(user);
//		}
		
		order.setAccount(user);
		order.setNguoinhan(fullname);
		order.setTongtien(total);
		Order newOrder = orderDAO.saveAndFlush(order);

		//// ADD OrderDetail ////
		for (int i = 0; i < idProduct.length; i++) {
			Product product = productDAO.findById(Integer.parseInt(idProduct[i])).get();
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setOrder(newOrder);
			orderDetail.setProduct(product);
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
