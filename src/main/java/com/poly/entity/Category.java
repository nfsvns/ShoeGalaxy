package com.poly.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "Categories")
public class Category implements Serializable{
	@Id
	@NotEmpty
	@Length(min = 0, max = 4)
	private String id;
	@NotEmpty
	private String name;
	
	@JsonIgnore
	@OneToMany(mappedBy = "category")
	private List<Product> products;
	@Override
	public String toString() {
	    return "Category [id=" + id + ", name=" + name + ", products size=" + (products != null ? products.size() : "null") + "]";
	}
}
