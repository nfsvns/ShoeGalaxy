package com.poly.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	
	

	public Account(String username, String password, String fullname, String email) {
		super();
		this.username = username;
		this.password = password;
		this.fullname = fullname;
		this.email = email;
	}

	public Account() {
		super();
	}

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
    List<ShoppingCart> shoppingCarts;

	@JsonIgnore
	@OneToMany(mappedBy = "account")
	private List<Comment> comments;
	
	@JsonIgnore
	@OneToMany(mappedBy = "account")
	private List<Reply> reply;
	
	

}