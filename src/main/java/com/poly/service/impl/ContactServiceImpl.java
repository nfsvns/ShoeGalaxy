package com.poly.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.dao.ContactDAO;
import com.poly.entity.Contact;
import com.poly.service.ContactService;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactDAO contactDAO;

    @Override
    public List<Contact> findAllContacts() {
        return contactDAO.findAll();
    }

    @Override
    public Contact findContactById(Integer id) {
        return contactDAO.findById(id).orElse(null);
    }

    @Override
    public Contact saveContact(Contact contact) {
        return contactDAO.save(contact);
    }

    @Override
    public Contact updateContact(Contact contact) {
        return contactDAO.save(contact);
    }

    @Override
    public void deleteContact(Integer id) {
        contactDAO.deleteById(id);
    }
}
