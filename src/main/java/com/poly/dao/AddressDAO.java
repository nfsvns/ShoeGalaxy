package com.poly.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.entity.Address;
import com.poly.entity.Order;
import com.poly.entity.OrderDetail;



public interface AddressDAO extends JpaRepository<Address, Integer> {
	 List<Address> findByAccountUsername(String username);
	 String findProvinceByAddress(String address);
	
}
