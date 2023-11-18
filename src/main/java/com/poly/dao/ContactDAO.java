package com.poly.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.entity.Contact;

public interface ContactDAO  extends JpaRepository<Contact, Integer>{

}
