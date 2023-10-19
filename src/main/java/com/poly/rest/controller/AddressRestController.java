package com.poly.rest.controller;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poly.entity.Address;
import com.poly.entity.Category;
import com.poly.entity.Role;
import com.poly.service.AddressService;
import com.poly.service.CategoryService;


@CrossOrigin("*")
@RestController
@RequestMapping("/rest/address")
public class AddressRestController {
	@Autowired
	AddressService addressservice;
	@GetMapping
	public List<Address> getAll() {
		return addressservice.findAll();
	}
	
	  @GetMapping("/getAddressDetails")
	public ResponseEntity<Map<String, String>> getAddressDetails(@RequestParam("selectedAddress") Integer selectedAddress) {
	    Map<String, String> details = new HashMap<>();
	    
	    // Thực hiện logic để lấy thông tin từ cơ sở dữ liệu dựa trên selectedAddress
	    Address address = addressservice.findById(selectedAddress);
	    if (address != null) {
	        details.put("province", address.getProvince());
	       
	        details.put("district", address.getDistrict());
	        details.put("ward", address.getWard());
	        details.put("address", address.getAddress());
	        details.put("email", address.getEmail());
	        String fullname = address.getAccount().getFullname();
            details.put("fullname", fullname);
	    } else {
	        details.put("province", "");
	        details.put("district", "");
	        details.put("ward", "");
	        details.put("fullname", "");
	        details.put("address", "");
	        details.put("email", "");
	    }
	    
	    return new ResponseEntity<>(details, HttpStatus.OK);
	}
	
	
	
	
	
	}

