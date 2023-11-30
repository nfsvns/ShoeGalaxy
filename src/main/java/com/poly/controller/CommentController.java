package com.poly.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
public class CommentController {
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

	@PostMapping("/shop.html/deleteComment/{commentId}")
	public String deleteComment(@PathVariable("commentId") Integer commentId,
			@RequestParam(value = "productId", required = false) Integer idProduct) {

		System.out.println(commentId);
		System.out.println(idProduct);
		commentDAO.deleteById(commentId);

		// Redirect back to the product page or any desired page
		return "redirect:/shop-single.html/" + idProduct; // Adjust the URL as needed
	}

	@PostMapping("/shop.html/deleteReply/{replyId}")
	public String deleteReply(@PathVariable("replyId") Integer replyId,
			@RequestParam(value = "productId", required = false) Integer productId) {
		replyDAO.deleteById(replyId);

		// Redirect back to the product page or any desired page
		return "redirect:/shop-single.html/" + productId; // Adjust the URL as needed
	}
	
	
	@PostMapping("/shop.html/updateComment")
	public String updateComment(@RequestParam("commentId") Integer commentId,
	                             @RequestParam("editedDescription") String editedDescription,
	                             @RequestParam("productId") Integer productId) {
	    Comment existingComment = commentDAO.findById(commentId).orElse(null);

	    if (existingComment != null) {
	        existingComment.setDescription(editedDescription);
	        commentDAO.save(existingComment);
	    } else {
	        throw new IllegalArgumentException("Comment with id " + commentId + " not found!");
	    }

	    // Redirect back to the product page or any desired page
	    return "redirect:/shop-single.html/" + productId; // Adjust the URL as needed
	}
	  @PostMapping("/shop.html/updateReply")
	    public String updateReply(@RequestParam("replyId") Integer replyId,
	                              @RequestParam("editedDescription") String editedDescription,
	                              @RequestParam("productId") Integer productId,
	                              Model model,
	                              HttpServletRequest request) {
	        Reply existingReply = replyDAO.findById(replyId).orElse(null);

	        if (existingReply != null) {
	            // Update reply details
	            existingReply.setDescription(editedDescription);

	            // Save the updated reply
	            replyDAO.save(existingReply);
	        } else {
	            throw new IllegalArgumentException("Reply with id " + replyId + " not found!");
	        }

	        // Redirect back to the product page or any desired page
	        return "redirect:/shop-single.html/" + productId; // Adjust the URL as needed
	    }


	@PostMapping("/shop.html/addComments")
	public String addComment(@RequestParam(value = "description", required = false) String description,
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
		return "redirect:/shop-single.html/" + idProduct;
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
		return "redirect:/shop-single.html/" + idProduct;
	}

}
