package com.poly.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "discount_product")
public class DiscountProduct {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name = "productId")
	private Product product;
	private String name;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate start_Date;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate end_Date;
	private double percentage;
	private boolean active;

}
