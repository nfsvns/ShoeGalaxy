package com.poly.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.poly.dao.AccountDAO;

import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "Accounts")
public class Account implements Serializable {
	
	@Id
	String username;	
	String password;
	String fullname;
	String email;
	String photo;
	Integer id;
	Boolean activate;
	
	@JsonIgnore
	@OneToMany(mappedBy = "account")
	List<Order> orders;

	@JsonIgnore
	@OneToMany(mappedBy = "account")
	List<Address> addresses;

	@JsonIgnore
	@OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
	List<Authority> authorities;
	
	@JsonIgnore
	@OneToMany(mappedBy = "account")
	private List<Comment> comments;
	
	@JsonIgnore
	@OneToMany(mappedBy = "account")
	private List<Reply> reply;
	
	
}

