package com.poly.rest.controller;

import java.time.LocalDate;
import java.util.List;

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

import com.poly.entity.Contact;
import com.poly.service.ContactService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/contact")
public class ContactRestcController {

	@Autowired
	ContactService ctService; 
	@GetMapping
	public List<Contact> getAll(){
		return ctService.findAllContacts();
	}
	@GetMapping("{id}")
	public Contact getOne( @PathVariable("id") Integer id) {
		return ctService.findContactById(id);
	}
	@PostMapping
	public Contact post (@RequestBody Contact contact) {
		  contact.setCreateDate(LocalDate.now());
		ctService.saveContact(contact);
		return contact;
	}
	@PutMapping("{id}")
	public Contact put (@PathVariable("id") Integer id, @RequestBody Contact contact) {
		return ctService.updateContact(contact);
	}
	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") Integer id) {
		/* codeService.deleteDiscountCode(id); */
		ctService.deleteContact(id);
	}
	
}
