package com.poly.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.dao.AccountDAO;
import com.poly.dao.AuthorityDAO;
import com.poly.entity.Account;
import com.poly.entity.Authority;
import com.poly.service.AuthorityService;



@Service
public class AuthorityServiceImpl implements AuthorityService{
	@Autowired
	AuthorityDAO dao;
	@Autowired
	AccountDAO accountDAO;
	@Autowired
	AuthorityService authorityService;

	public List<Authority> findAll() {
		return dao.findAll();
	}

	public Authority create(Authority auth) {
		return dao.save(auth);
	}

	public void delete(Integer id) {
		dao.deleteById(id);
	}

	public List<Authority> findAuthoritiesOfAdministrators() {
		List<Account> accounts = accountDAO.getAdministrators();
		return dao.authoritiesOf(accounts);
	}

	@Override
	public List<Authority> findAuthoritiesByUsername(String username) {
	    return dao.findAuthoritiesByUsername(username);
	
	}
	@Override
	public boolean revokeAuthoritiesByUsername(String username) {
        List<Authority> authorities = dao.findAuthoritiesByUsername(username);
        for (Authority authority : authorities) {
            dao.delete(authority);
        }
		return true;
    }

	@Override
	public Authority findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Authority> findUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}
}
