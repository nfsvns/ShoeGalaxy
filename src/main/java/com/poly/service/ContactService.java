package com.poly.service;

import java.util.List;

import com.poly.entity.Contact;

public interface ContactService {
	List<Contact> findAllContacts();

	Contact findContactById(Integer id);

	Contact saveContact(Contact contact);



	void deleteContact(Integer id);

	Contact updateContact(Contact contact);
}
