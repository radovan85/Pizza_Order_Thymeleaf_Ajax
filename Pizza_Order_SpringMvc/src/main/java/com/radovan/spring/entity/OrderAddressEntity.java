package com.radovan.spring.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "order_addresses")
public class OrderAddressEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer orderAddressId;

	private String address;
	private String city;
	@Column(name = "zip_code")
	private String zipcode;

	@OneToOne(mappedBy = "address",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	private OrderEntity order;

	public Integer getOrderAddressId() {
		return orderAddressId;
	}

	public void setOrderAddressId(Integer orderAddressId) {
		this.orderAddressId = orderAddressId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	

	public OrderEntity getOrder() {
		return order;
	}

	public void setOrder(OrderEntity order) {
		this.order = order;
	}
	
	
	
}