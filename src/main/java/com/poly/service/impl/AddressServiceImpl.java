package com.poly.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.dao.AddressDAO;
import com.poly.dao.CategoryDAO;
import com.poly.entity.Address;
import com.poly.service.AddressService;
@Service
public class AddressServiceImpl implements AddressService {
	@Autowired
	AddressDAO adddao;
	@Override
	public List<Address> findAll() {
		return adddao.findAll();
	}

	@Override
	public Address findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Address create(Address address) {
		return adddao.save(address);
	}

	@Override
	public Address update(Address address) {
		// TODO Auto-generated method stub
		return adddao.save(address);
	}

	@Override
	public void delete(Integer id) {
		adddao.deleteById(id);
		
	}

}
