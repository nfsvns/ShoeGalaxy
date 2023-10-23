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
	private Integer quantity;
	private Boolean available;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	@JsonIgnore
	@OneToMany(mappedBy = "product")
	private List<OrderDetail> orderDetails;
	
	

	@JsonIgnore
	@OneToMany(mappedBy = "product")
	private List<Size> sizes;

	@JsonIgnore
	@OneToMany(mappedBy = "product")
	private List<Image> images;

	@JsonIgnore
	@OneToMany(mappedBy = "product")
	private List<DiscountProduct> discountProduct;
	
	@JsonIgnore
	@OneToMany(mappedBy = "product")
	private List<ShoppingCart> shoppingCarts;
	
	@Override
	public String toString() {
	    return "Product [id=" + id + ", name=" + name + ", price=" + price + ", quantity=" + quantity + ", available=" + available
	            + ", category=" + category
	            + ", orderDetails size=" + (orderDetails != null ? orderDetails.size() : "null")
	            + ", sizes size=" + (sizes != null ? sizes.size() : "null")
	            + ", images size=" + (images != null ? images.size() : "null") + "]";
	}
}
