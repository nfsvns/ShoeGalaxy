package com.poly.controller;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.dao.AccountDAO;
import com.poly.dao.CommentDAO;
import com.poly.dao.DiscountProductDAO;
import com.poly.dao.ImageDAO;
import com.poly.dao.OrderDetailDAO;
import com.poly.dao.ProductDAO;

import com.poly.dao.ReplyDAO;
import com.poly.dao.SizeDAO;
import com.poly.entity.Account;
import com.poly.entity.Comment;
import com.poly.entity.DiscountProduct;
import com.poly.entity.Product;
import com.poly.entity.Reply;
import com.poly.entity.Size;
import com.poly.service.SessionService;

@Controller
public class ProductController {

	@Autowired
	ProductDAO dao;
	@Autowired
	SizeDAO sizeDAO;
	@Autowired
	DiscountProductDAO dpDAO;
	@Autowired
	CommentDAO commentDAO;

	@Autowired
	AccountDAO accountDAO;

	@Autowired
	ReplyDAO replyDAO;

	@Autowired
	ImageDAO imageDAO;
	@Autowired
	OrderDetailDAO orderDetailDAO;

	@Autowired
	SessionService sessionService;

	@RequestMapping("/shop.html")
	public String list(Model model, @RequestParam("p") Optional<Integer> p) {
		Pageable pageable = PageRequest.of(p.orElse(0), 6);
		Page<Product> page = dao.findDelete(pageable);

		// Lấy danh sách DiscountProduct
		List<DiscountProduct> discountProducts = dpDAO.findAll();
		model.addAttribute("discountProducts", discountProducts);
		model.addAttribute("products", page);

		// Truy vấn danh sách hãng và số lượng sản phẩm tương ứng
		List<Object[]> results = dao.countProductsByCategory();
		model.addAttribute("results", results);

		return "shop";
	}

	@RequestMapping("/shop.html/search")
	public String searchAndPage(Model model, @RequestParam("keywords") Optional<String> kw,
			@RequestParam("p") Optional<Integer> p) {
		String keywords = kw.orElse(sessionService.getAttribute("keywords"));
		sessionService.setAttribute("keywords", keywords);

		Pageable pageable = PageRequest.of(p.orElse(0), 6);
		Page<Product> page = dao.findByKeywords("%" + keywords + "%", pageable);
		model.addAttribute("products", page);
		model.addAttribute("keywords", keywords);
		List<DiscountProduct> discountProducts = dpDAO.findAll();
		model.addAttribute("discountProducts", discountProducts);
		model.addAttribute("check", "1");
		// Truy vấn danh sách hãng và số lượng sản phẩm tương ứng
		List<Object[]> results = dao.countProductsByCategory();
		model.addAttribute("results", results);
		return "shop";
	}

	@RequestMapping("shop.html/findByPrice")
	public String findByPrice(Model model, @RequestParam("p") Optional<Integer> p,
			@RequestParam(value = "priceRange", required = false) String priceRange) {
		// Check if priceRange is null or does not have the expected format
		if (priceRange == null || !priceRange.matches("\\d+-\\d+")) {
			// Handle the case where priceRange is not valid, perhaps by setting default
			// values or redirecting to an error page.
			return "error"; // Replace "error" with the actual error view name
		}

		String[] priceValues = priceRange.split("-");
		if (priceValues.length != 2 || priceValues[0] == null || priceValues[1] == null) {
			// Handle the case where priceValues are not valid, perhaps by setting default
			// values or redirecting to an error page.
			return "error"; // Replace "error" with the actual error view name
		}

		try {
			double minPrice = Double.parseDouble(priceValues[0]);
			double maxPrice = Double.parseDouble(priceValues[1]);
			System.out.println(minPrice);
			System.out.println(maxPrice);

			// rest of your code...

			List<DiscountProduct> discountProducts = dpDAO.findAll();
			model.addAttribute("discountProducts", discountProducts);
			Pageable pageable = PageRequest.of(p.orElse(0), 6);
			Page<Product> page = dao.findByPriceBetween(minPrice, maxPrice, pageable);
			model.addAttribute("products", page);
			model.addAttribute("check", "2");
			model.addAttribute("priceRange", priceRange); // Add priceRange to the model for use in the view
			// Truy vấn danh sách hãng và số lượng sản phẩm tương ứng
			List<Object[]> results = dao.countProductsByCategory();
			model.addAttribute("results", results);

			return "shop";
		} catch (NumberFormatException e) {
			// Handle the case where parsing fails, perhaps by setting default values or
			// redirecting to an error page.
			return "error"; // Replace "error" with the actual error view name
		}
	}

	@RequestMapping("/findSizeProducts")
	public String getProductsBySize(@RequestParam(value = "size", required = false) String size, Model model,
			Pageable pageable) {

		Page<Product> page;

		if ("small".equals(size)) {
			page = dao.findProductsBySize36To38(PageRequest.of(pageable.getPageNumber(), 6));
		} else if ("medium".equals(size)) {
			page = dao.findProductsBySize40To42(PageRequest.of(pageable.getPageNumber(), 6));
		} else if ("large".equals(size)) {
			page = dao.findProductsBySize42To44(PageRequest.of(pageable.getPageNumber(), 6));
		} else {
			// Xử lý khi size là null hoặc không phù hợp
			page = new PageImpl<>(Collections.emptyList());
		}

		model.addAttribute("products", page);
		model.addAttribute("check", "6");
		model.addAttribute("size", size);

		List<DiscountProduct> discountProducts = dpDAO.findAll();
		model.addAttribute("discountProducts", discountProducts);

		// Truy vấn danh sách hãng và số lượng sản phẩm tương ứng
		List<Object[]> results = dao.countProductsByCategory();
		model.addAttribute("results", results);

		return "shop";
	}

	@RequestMapping("/shop.html/sort")
	public String sort(Model model, @RequestParam("desc") Optional<String> de, @RequestParam("p") Optional<Integer> p) {
		String desc = de.orElse(sessionService.getAttribute("desc"));

		sessionService.setAttribute("desc", desc);

		Sort sort = Sort.by(Sort.Direction.DESC, desc);
		Pageable pageable = PageRequest.of(p.orElse(0), 6, sort);
		Page<Product> page = dao.findAll(pageable);
		model.addAttribute("products", page);
		model.addAttribute("check", "4");
		// Truy vấn danh sách hãng và số lượng sản phẩm tương ứng
		List<Object[]> results = dao.countProductsByCategory();
		model.addAttribute("results", results);
		List<DiscountProduct> discountProducts = dpDAO.findAll();
		model.addAttribute("discountProducts", discountProducts);
		return "shop";
	}

	@RequestMapping("/shop.html/sort1")
	public String sort2(Model model, @RequestParam("asc") Optional<String> a, @RequestParam("p") Optional<Integer> p) {
		String asc = a.orElse(sessionService.getAttribute("asc"));
		sessionService.setAttribute("asc", asc);
		Sort sort = Sort.by(Sort.Direction.ASC, asc);
		Pageable pageable = PageRequest.of(p.orElse(0), 6, sort);
		Page<Product> page = dao.findAll(pageable);
		model.addAttribute("products", page);
		List<DiscountProduct> discountProducts = dpDAO.findAll();
		model.addAttribute("discountProducts", discountProducts);

		model.addAttribute("check", "5");
		// Truy vấn danh sách hãng và số lượng sản phẩm tương ứng
		List<Object[]> results = dao.countProductsByCategory();
		model.addAttribute("results", results);
		return "shop";
	}

	@RequestMapping("/shop.html/searchBrand")
	public String brand(Model model, @RequestParam("brand") Optional<String> br,
			@RequestParam("p") Optional<Integer> p) {
		String brand = br.orElse(sessionService.getAttribute("brand"));
		sessionService.setAttribute("brand", brand);

		Pageable pageable = PageRequest.of(p.orElse(0), 6);
		Page<Product> page = dao.findByBrand(brand, pageable);

		model.addAttribute("products", page);
		model.addAttribute("check", "3");
		List<DiscountProduct> discountProducts = dpDAO.findAll();
		model.addAttribute("discountProducts", discountProducts);
		// Truy vấn danh sách hãng và số lượng sản phẩm tương ứng
		List<Object[]> results = dao.countProductsByCategory();
		model.addAttribute("results", results);
		return "shop";
	}

	@RequestMapping("/shop-single.html/{productId}")
	public String getProduct(Model model, @PathVariable("productId") int productId) {
		Product list = dao.findById(productId).get();
		List<Size> listS = sizeDAO.findByIdProduct(productId);
		List<DiscountProduct> discountProducts = dpDAO.findByIdProduct(productId);
		List<Comment> comment = commentDAO.findByCommentId(productId);

		List<Reply> reply = replyDAO.findByCommentProductId(productId);

		model.addAttribute("reply", reply);
		model.addAttribute("prod", list);
		model.addAttribute("prodd", listS);
		model.addAttribute("discountProducts", discountProducts);
		model.addAttribute("comment", comment);

		// Chắc chắn rằng listS chứa thông tin về số lượng của size
		return "shop-single";
	}

	@PostMapping("/shop.html/addComments")
	public String addComment(@RequestParam("description") String description,
			@RequestParam(value = "productId", required = false) Integer idProduct, Model model,
			HttpServletRequest request) {
		Comment commentt = new Comment();
		// add Comment
		// getTime();
		Timestamp now = new Timestamp(new Date().getTime());
		// getUserName();
		String username = request.getRemoteUser();
		Account user = accountDAO.findById(username).orElse(null);
		// setDescription
		commentt.setDescription(description); // Set the comment text
		// setAccountUsername
		commentt.setAccount(user);
		// setDate
		commentt.setCreate_Date(now);

		// check product
		if (idProduct != null) {
			Product product = dao.findById(idProduct).orElse(null);
			if (product != null) {
				// if productId != null
				commentt.setProduct(product);
			} else {
				throw new IllegalArgumentException("Product with id " + idProduct + " not found!");
			}
		} else {
			// Handle when productId is null
			throw new IllegalArgumentException("Product id is null!");
		}

		// add comments
		commentDAO.save(commentt);

		// Fetch data again after saving the new comment
		Product list = dao.findById(idProduct).orElse(null);
		List<Size> listS = sizeDAO.findByIdProduct(idProduct);
		List<DiscountProduct> discountProducts = dpDAO.findByIdProduct(idProduct);
		List<Comment> comment = commentDAO.findByCommentId(idProduct);

		model.addAttribute("prod", list);
		model.addAttribute("prodd", listS);
		model.addAttribute("discountProducts", discountProducts);
		model.addAttribute("comment", comment);

		List<Reply> reply = replyDAO.findByCommentProductId(idProduct);

		model.addAttribute("reply", reply);
		// Add a success message to the model
		model.addAttribute("message", "Comment added successfully!");
		// Truy vấn danh sách hãng và số lượng sản phẩm tương ứng
		List<Object[]> results = dao.countProductsByCategory();
		model.addAttribute("results", results);
		// Return the name of your success template
		return "shop-single.html";
	}

	@PostMapping("/shop.html/replyComments")
	public String replyComment(@RequestParam("descriptionReply") String description,
			@RequestParam(value = "productIdReply", required = false) Integer idProduct,
			@RequestParam(value = "parentId", required = false) Integer idComment, Model model,
			HttpServletRequest request) {
		Reply commentt = new Reply();
		Timestamp now = new Timestamp(new Date().getTime());
		String username = request.getRemoteUser();
		Account user = accountDAO.findById(username).orElse(null);

		commentt.setDescription(description); // Set the comment text
		commentt.setAccount(user);

		commentt.setCreate_date(now);

		if (idProduct != null) {
			Product product = dao.findById(idProduct).orElse(null);
			if (product != null) {
				commentt.setProduct(product);
			} else {
				throw new IllegalArgumentException("Product with id " + idProduct + " not found!");
			}
		} else {
			// Handle when productId is null
			throw new IllegalArgumentException("Product id is null!");
		}

		if (idComment != null) {
			Comment comment = commentDAO.findById(idComment).orElse(null);
			if (comment != null) {
				commentt.setComment(comment);
			} else {
				throw new IllegalArgumentException("Product with id " + idProduct + " not found!");
			}
		} else {
			// Handle when productId is null
			throw new IllegalArgumentException("Product id is null!");
		}

		replyDAO.save(commentt);

		// Fetch data again after saving the new comment
		Product list = dao.findById(idProduct).orElse(null);
		List<Size> listS = sizeDAO.findByIdProduct(idProduct);
		List<DiscountProduct> discountProducts = dpDAO.findByIdProduct(idProduct);
		List<Comment> comment = commentDAO.findByCommentId(idProduct);

		model.addAttribute("prod", list);
		model.addAttribute("prodd", listS);
		model.addAttribute("discountProducts", discountProducts);
		model.addAttribute("comment", comment);
		List<Reply> reply = replyDAO.findByCommentProductId(idProduct);

		model.addAttribute("reply", reply);
		// Add a success message to the model
		model.addAttribute("message", "Comment added successfully!");
		// Truy vấn danh sách hãng và số lượng sản phẩm tương ứng
		List<Object[]> results = dao.countProductsByCategory();
		model.addAttribute("results", results);
		// Return the name of your success template
		return "shop-single.html";
	}
	@GetMapping("/newArrivals")
	public String index(Model model, @RequestParam(name = "page", defaultValue = "0") int page) {
	    int pageSize = 8; // or any other desired page size
	    int totalProducts = 24; // or any other desired total number of products

	    Page<Product> newProductPage = dao.findNewProducts(PageRequest.of(page, pageSize));
	    List<Product> newProduct = newProductPage.getContent();

	    List<Object[]> orderDetails = orderDetailDAO.findByAllTopProductOrderDetail();
	    model.addAttribute("orderDetails", orderDetails);
	    model.addAttribute("newProduct", newProduct);
	    model.addAttribute("images", imageDAO.findAll());
	    model.addAttribute("pro", dao.topProduct());
	    model.addAttribute("products", dao.findAll());
	    model.addAttribute("discountProducts", dpDAO.findAll());

	    // Truy vấn danh sách hãng và số lượng sản phẩm tương ứng
	    List<Object[]> results = dao.countProductsByCategory();
	    model.addAttribute("results", results);

	    model.addAttribute("currentPage", page);
	    model.addAttribute("totalPages", (int) Math.ceil((double) totalProducts / pageSize));

	    return "newArrivals.html";
	}



}
