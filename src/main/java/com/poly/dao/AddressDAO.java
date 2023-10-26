package com.poly.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.entity.Account;
import com.poly.entity.Address;

public interface AddressDAO extends JpaRepository<Address, Integer> {
	@Query("SELECT a FROM Address a WHERE a.account.username LIKE ?1")
	List<Address> getAddressesByUsername(String username);
	
	Optional<Address> findByAddressDetail(String addressDetail);

}
