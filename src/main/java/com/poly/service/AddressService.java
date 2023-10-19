package com.poly.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.dao.AddressDAO;
import com.poly.entity.Account;
import com.poly.entity.Address;
import com.poly.entity.Role;

public interface AddressService {
	 
	public List<Address> getAddressesByUsername(String username);
	public String getProvinceByAddress(String address);
	public List<Address> findAll() ;
	public Address findById(Integer selectedAddress);
	public Address findDefaultAddress();
}
