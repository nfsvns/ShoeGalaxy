package com.poly.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.poly.dao.AccountDAO;
import com.poly.dao.AddressDAO;
import com.poly.entity.Account;
import com.poly.entity.Address;
import com.poly.entity.Role;
import com.poly.service.AccountService;
import com.poly.service.AddressService;


@Service
public class AddressServiceImpl implements AddressService{
	@Autowired
    private AddressDAO dao;
	public List<Address> findAll() {
		return dao.findAll();
	}
	@Override
	public List<Address> getAddressesByUsername(String username) {
        return dao.findByAccountUsername(username);
    }
	@Override
	 public String getProvinceByAddress(String address) {
        // Triển khai logic để lấy `province` từ cơ sở dữ liệu dựa vào `address`
        String province = dao.findProvinceByAddress(address);
        return province;
    }
	@Override
	public Address findById(Integer selectedAddress) {
        Optional<Address> addressOptional = dao.findById(selectedAddress);
        return addressOptional.orElse(null);
    }
	
	
	@Override
	 public Address findDefaultAddress() {
        // Thực hiện truy vấn cơ sở dữ liệu để tìm địa chỉ có thuộc tính activate = true
        return dao.findByActivate(true);
    }
	
}
