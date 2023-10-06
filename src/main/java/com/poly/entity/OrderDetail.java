package com.poly.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "orderdetails")
public class OrderDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private double price;
	private Integer quantity;
	private Integer size;
	@ManyToOne @JoinColumn(name = "productId")
	private Product product;
	@ManyToOne @JoinColumn(name = "orderId")
	private Order order;
}
