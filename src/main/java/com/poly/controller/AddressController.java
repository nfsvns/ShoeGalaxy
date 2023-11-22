package com.poly.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.dao.AccountDAO;
import com.poly.dao.AddressDAO;
import com.poly.entity.Account;
import com.poly.entity.Address;

@Controller
public class AddressController {
	@Autowired
	AccountDAO accountDAO;
	@Autowired
	AddressDAO addressDAO;

	@RequestMapping("/addressAdmin")
	public String index(Model model, Address add) {
		Address item = new Address();

		model.addAttribute("item", item);
		model.addAttribute("addressItems", addressDAO.findAll());
		return "addressAdmin";
	}

	@PostMapping("/addAddress")
	public String addAddress(Model model, @RequestParam String address,
			@RequestParam(value = "provinceLabel", required = false) String provinceLabel,
			@RequestParam(value = "districtLabel", required = false) String districtLabel,
			@RequestParam(value = "wardLabel", required = false) String wardLabel, HttpServletRequest request) {
		if (provinceLabel == null || provinceLabel.isEmpty()
				|| districtLabel == null || districtLabel.isEmpty()
				|| wardLabel == null || wardLabel.isEmpty()
				|| address == null || address.isEmpty()) {
			model.addAttribute("messages", "Vui lòng điền đầy đủ thông tin để thêm địa chỉ");
			return "forward:/check";
		} else {
			String username = request.getRemoteUser();
			Account user = accountDAO.findById(username).orElse(null);
			
			boolean addressExists = addressDAO.existsByAccountAndAddressDetail(user, address + ", " + wardLabel + ", " + districtLabel + ", " + provinceLabel);
	        if (addressExists) {
	            model.addAttribute("messages", "Địa chỉ đã tồn tại. Vui lòng chọn địa chỉ khác.");
	            return "forward:/check";
	        }
			
			Address ad = new Address();
			ad.setAccount(user);
			ad.setAddressDetail(address + ", " + wardLabel + ", " + districtLabel + ", " + provinceLabel);
			ad.setCity(provinceLabel);
			ad.setDistrict(districtLabel);
			ad.setWard(wardLabel);
			ad.setStreet(address);
			addressDAO.save(ad);
			return "redirect:/check";
		}

	}

	@PostMapping("/deleteAddress")
	public String deleteAddress(Model model, @RequestParam(value = "address2", required = false) Integer address2,
			HttpServletRequest request) {
		if (address2 == null) {
			model.addAttribute("messages", "Vui lòng chọn địa chỉ để xóa ");
			return "forward:/check";
		}
		addressDAO.deleteById(address2);
		return "redirect:/check";
	}

	
	@PostMapping("/updateAddress")
	public String updateAddress(Model model, @RequestParam(value = "address2", required = false) Integer address2,
			@RequestParam String address, @RequestParam(value = "provinceLabel", required = false) String provinceLabel,
			@RequestParam(value = "districtLabel", required = false) String districtLabel,
			@RequestParam(value = "wardLabel", required = false) String wardLabel, HttpServletRequest request) {
		if (address2 == null) {
			model.addAttribute("messages", "Vui lòng chọn địa chỉ để update ");
			return "forward:/check";
		}
		Optional<Address> optionalAddress = addressDAO.findById(address2);
		if (optionalAddress.isPresent()) {
			Address existingAddress = optionalAddress.get();
			existingAddress.setCity(provinceLabel);
			existingAddress.setDistrict(districtLabel);
			existingAddress.setWard(wardLabel);
			existingAddress.setStreet(address);
			existingAddress.setAddressDetail(address + ", " + wardLabel + ", " + districtLabel + ", " + provinceLabel);
			addressDAO.save(existingAddress);
			return "redirect:/check";
		} else {
			model.addAttribute("messages", "Vui lòng chọn địa chỉ để update ");
			return "forward:/check";
		}
	}
	

	@PostMapping("/newAction")
	public String newAction(Model model, @RequestParam String address,
			@RequestParam(value = "provinceLabel", required = false) String provinceLabel,
			@RequestParam(value = "districtLabel", required = false) String districtLabel,
			@RequestParam(value = "wardLabel", required = false) String wardLabel, HttpServletRequest request) {
		if (provinceLabel == null || provinceLabel.isEmpty() 
				|| districtLabel == null || districtLabel.isEmpty()
				|| wardLabel == null || wardLabel.isEmpty() 
				|| address == null || address.isEmpty()) {
			model.addAttribute("messages", "Vui lòng điền đầy đủ thông tin để thêm địa chỉ");
			return "forward:/ChangeInfomation";
		} else {
			String username = request.getRemoteUser();
			Account user = accountDAO.findById(username).orElse(null);

			Address ad = new Address();
			ad.setAccount(user);
			ad.setAddressDetail(address + ", " + wardLabel + ", " + districtLabel + ", " + provinceLabel);
			ad.setCity(provinceLabel);
			ad.setDistrict(districtLabel);
			ad.setWard(wardLabel);
			ad.setStreet(address);
			addressDAO.save(ad);
			return "redirect:/ChangeInfomation";
		}

	}

	
}
