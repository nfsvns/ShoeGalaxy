package com.poly.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "Address")
public class Address  implements Serializable{
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column(name = "address_id")
	    private Integer id;
	 
	    @ManyToOne
	    @JoinColumn(name = "username")
	    private Account account; 
	    
	    private String province;
	    
	    @Column(name = "district")
	    private String district;
	    
	    @Column(name = "ward")
	    private String ward;	   
	    private String address;
	    private String email;
	    private Boolean activate;
	}
