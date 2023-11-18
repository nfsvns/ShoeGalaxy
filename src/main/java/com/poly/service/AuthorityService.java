package com.poly.service;

import java.util.List;

import com.poly.entity.Authority;
import com.poly.entity.Order;


public interface AuthorityService {
	public List<Authority> findAll() ;
	public Authority findById(Integer id) ;
	public Authority create(Authority auth) ;

	public void delete(Integer id) ;

	public List<Authority> findAuthoritiesOfAdministrators() ;
	public List<Authority> findUsername(String username);

}
