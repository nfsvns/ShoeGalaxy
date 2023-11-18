package com.poly.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.poly.dao.AccountDAO;
import com.poly.entity.Account;
import com.poly.entity.Product;
import com.poly.service.AccountService;


@Service
public class AccountServiceImpl implements AccountService{
	@Autowired
	AccountDAO dao;
	
	public List<Account> findAll() {
		return dao.findAll();
	}

	public Account findById(String username) {
		return dao.findById(username).get();
	}

	public List<Account> getAdministrators() {
		return dao.getAdministrators();
	}
	@Override
	public boolean changePassword(Account account, String oldPassword, String newPassword,String newPasswordAgain) {
		if ((newPassword.equalsIgnoreCase(newPasswordAgain)) && (oldPassword.equals(account.getPassword()))) {
            account.setPassword(newPassword);
            dao.save(account);
            return true;
        }
        return false;
    }

	@Override
	public boolean updateProfile(String username, String newFullname, String newEmail, String photo) {
		Account account = dao.findById(username).orElse(null);

        if (account != null) {
            // Cập nhật thông tin cá nhân
            account.setFullname(newFullname);
            account.setEmail(newEmail);
            account.setPhoto(photo);
            dao.save(account);
            return true;
        }

        return false;
    }
	@Override
	public Account getCurrentAccount() {
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        String username = authentication.getName();
	        return dao.findByUsername(username);
	}

	@Override
	public Account update(Account account) {
		
		return dao.save(account);
	}

	@Override
	public Account create(Account account) {
		// TODO Auto-generated method stub
		return dao.save(account);
	}

	@Override
	public void delete(String username) {
		// TODO Auto-generated method stub
		dao.deleteById(username);
	}

	@Override
	public boolean updateProfileWithoutPhoto(String username, String newFullname, String newEmail) {
		Account account = dao.findById(username).orElse(null);

        if (account != null) {
            // Cập nhật thông tin cá nhân
            account.setFullname(newFullname);
            account.setEmail(newEmail);
            dao.save(account);
            return true;
        }

        return false;
    }


	
}
