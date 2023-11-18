package com.poly.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;

import com.poly.entity.Account;

public interface AccountDAO extends JpaRepository<Account, String> {
	@Query("SELECT DISTINCT ar.account  FROM Authority ar WHERE ar.role.id IN ('DIRE', 'STAF','CUST')")
	List<Account> getAdministrators();

	Account findByUsername(String username);
	
	Account findByUsernameIgnoreCase(String username);

	@Query("SELECT a FROM Account a WHERE a.email LIKE ?1")
	Account getAccountByEmail(String email);
	
	 List<Account> findByEmail(String email);
	 
	@Procedure
    void DeleteAccountAndRelatedData(String username);
}
