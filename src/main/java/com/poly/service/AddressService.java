package com.poly.service;

import java.util.List;

import com.poly.entity.Address;

public interface AddressService {
	public List<Address> findAll() ;

	public Address findById(Integer id) ;

	public Address create(Address address) ;

	public Address update(Address address) ;

	public void delete(Integer id) ;
}
