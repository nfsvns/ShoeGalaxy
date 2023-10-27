package com.poly.controller;
import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.poly.dao.AccountDAO;
import com.poly.entity.Account;
import com.poly.service.AccountService;
import com.poly.service.UploadService;
@Controller
public class PasswordController {
	@Autowired
	AccountDAO dao;
	@Autowired
	AccountService acdao;
	@Autowired
	UploadService uploadservice;
	@GetMapping("/ChangePassword")
    public String showChangePasswordForm() {
        return "ChangePassword"; // Trả về trang thay đổi mật khẩu
    }

	@PostMapping("/changepassword")
    public String changePassword(String oldPassword, String newPassword,String newPasswordAgain, RedirectAttributes redirectAttributes) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Account account = dao.findById(username).orElse(null);

        if (account != null && acdao.changePassword(account, oldPassword, newPassword,newPasswordAgain)) {
            redirectAttributes.addFlashAttribute("success", "Thay đổi mật khẩu thành công.");
            return "redirect:/ChangePassword";
        } else {
            redirectAttributes.addFlashAttribute("error", "Không thể thay đổi mật khẩu. Vui lòng kiểm tra lại.");
            return "redirect:/ChangePassword";
        }
    }
	
	 @GetMapping("/ChangeInfomation")
	 public String userProfileForm(Model model) {
	        String username = SecurityContextHolder.getContext().getAuthentication().getName();
	        Account account = dao.findById(username).orElse(null);
	        
	        if (account != null) {
	            model.addAttribute("account", account);
	            return "ChangeInfomation";
	        } else {
	            return "redirect:/login"; // Chuyển hướng đến trang đăng nhập nếu không tìm thấy tài khoản
	        }
	    }
	 
	 @PostMapping("/profile")
	 public String updateUserProfile(@ModelAttribute Account updatedAccount, @RequestParam("image") MultipartFile imageFile, RedirectAttributes redirectAttributes) {
	     String username = SecurityContextHolder.getContext().getAuthentication().getName();

	     // Kiểm tra xem người dùng đã chọn tệp hình ảnh mới hay không
	     if (!imageFile.isEmpty()) {
	         try {
	             String originalFilename = imageFile.getOriginalFilename();
	             String fileName = uploadservice.save(imageFile, "images").getPath();

	             // Lấy tên tệp từ đường dẫn tệp
	             File savedFile = new File(fileName);
	             String imagePath = savedFile.getName();

	             // Lưu tên tệp vào cơ sở dữ liệu
	             updatedAccount.setPhoto(imagePath);

	             // Nếu người dùng chọn tệp mới, chạy hàm updateProfile
	             boolean success = acdao.updateProfile(username, updatedAccount.getFullname(), updatedAccount.getEmail(), updatedAccount.getPhoto());

	             if (success) {
	                 redirectAttributes.addFlashAttribute("success", "Thông tin cá nhân đã được cập nhật thành công.");
	             } else {
	                 redirectAttributes.addFlashAttribute("error", "Không thể cập nhật thông tin cá nhân. Vui lòng kiểm tra lại.");
	             }
	         } catch (Exception e) {
	             // Xử lý lỗi
	             redirectAttributes.addFlashAttribute("error", "Không thể tải lên hình ảnh. Vui lòng kiểm tra lại.");
	         }
	     } else {
	         // Nếu người dùng không chọn tệp mới, chạy hàm updateProfileWithoutPhoto
	         boolean success = acdao.updateProfileWithoutPhoto(username, updatedAccount.getFullname(), updatedAccount.getEmail());

	         if (success) {
	             redirectAttributes.addFlashAttribute("success", "Thông tin cá nhân đã được cập nhật thành công.");
	         } else {
	             redirectAttributes.addFlashAttribute("error", "Không thể cập nhật thông tin cá nhân. Vui lòng kiểm tra lại.");
	         }
	     }

	     return "redirect:/ChangeInfomation";
	 }








	


	
	 
	    }
