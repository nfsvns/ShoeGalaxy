package com.poly.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "Orders")
public class Order implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "create_date", nullable = false)
	private Timestamp createDate;

	private String address;
	private String nguoinhan;
	private Double tongtien;
	private Boolean available;
	private String city;

	@ManyToOne
	@JoinColumn(name = "username")
	private Account account;
	
	@ManyToOne
	@JoinColumn(name = "discountID")
	private DiscountCode discountCode;
	
	@JsonIgnore
	@OneToMany(mappedBy = "order")
	List<OrderDetail> orderDetails;
}
