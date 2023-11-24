package com.poly.dao;

import java.util.List;
import java.util.Optional;

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
	 
	 @Query("SELECT a FROM Account a ORDER BY a.id DESC")
	    List<Account> findAllDESC();
	 @Query("SELECT MAX(a.id) FROM Account a")
	    Integer getMaxAccountId();
	 
	  Optional<Account> findById(Integer id);

	 
	 @Query("SELECT a FROM Account a WHERE a.id = ?1")
	   Account getAccountById(Integer id);
	 
	 
	 public boolean existsByusername(String username);
	 boolean existsByIdAndUsername(Integer id, String username);
	 public boolean existsById(Integer id);
	   
	
	 
	@Procedure
    void DeleteAccountAndRelatedData(String username);
}
