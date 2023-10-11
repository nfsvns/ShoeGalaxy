package com.poly.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "Products")
public class Product implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private Double price;
	@JoinColumn(name = "size")
	private Integer quantity;
	private Boolean available;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	@ManyToOne
	@JoinColumn(name = "sale_id")
	private Discount_Sale discount_sale; 
	
	@JsonIgnore
	@OneToMany(mappedBy = "product")
	private List<OrderDetail> orderDetails;

	@JsonIgnore
	@OneToMany(mappedBy = "product")
	private List<Size> sizes;
	
	@JsonIgnore
	@OneToMany(mappedBy = "product")
	private List<Image> images;
	@Override
	public String toString() {
	    return "Product [id=" + id + ", name=" + name + ", price=" + price + ", quantity=" + quantity + ", available=" + available
	            + ", category=" + category + ", discount_sale=" + discount_sale 
	            + ", orderDetails size=" + (orderDetails != null ? orderDetails.size() : "null")
	            + ", sizes size=" + (sizes != null ? sizes.size() : "null")
	            + ", images size=" + (images != null ? images.size() : "null") + "]";
	}
}
