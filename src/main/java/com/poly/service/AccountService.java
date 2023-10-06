package com.poly.service;

import java.util.List;

import com.poly.entity.Account;


public interface AccountService {
	public List<Account> findAll() ;
	public Account findById(String username) ;
	public List<Account> getAdministrators() ;
	 public boolean changePassword(Account account, String oldPassword, String newPassword,String newPasswordAgain);
	 public boolean updateProfile(String username, String newFullname, String newEmail, String photo);
	 public Account getCurrentAccount();
}
