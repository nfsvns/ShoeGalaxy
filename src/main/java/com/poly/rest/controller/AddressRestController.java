package com.poly.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.service.AddressService;
import com.poly.entity.Address;
import com.poly.entity.Category;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/address")
public class AddressRestController {
	@Autowired
	AddressService addressService;

	@GetMapping
	public List<Address> findAll() {
		return addressService.findAll();
	}

	@GetMapping("{id}")
	public Address getOne(@PathVariable("id") Integer id) {

		return addressService.findById(id);
	}
	@PostMapping
	public Address post(@RequestBody Address address) {
		addressService.create(address);
		return address;
	}
	@PutMapping("{id}")
	public Address put(@PathVariable("id") Integer id, @RequestBody Address address) {
		return addressService.update(address);
	}
	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") Integer id) {
		// Kiểm tra trước khi xóa
		addressService.delete(id);
	}
}
