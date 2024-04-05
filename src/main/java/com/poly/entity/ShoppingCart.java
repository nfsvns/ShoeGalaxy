package com.poly.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "shoppingcarts")
public class ShoppingCart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "Username")
	private Account account;
	
	@ManyToOne
	@JoinColumn(name = "productId")
	private Product product;

	private String image;
	private String name;
	private String size;
	private float price;
	private int qty;
	private float total;
	private	Boolean status;

}
