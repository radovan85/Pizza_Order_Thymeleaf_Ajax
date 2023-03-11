package com.radovan.spring.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class OrderDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer customerOrderId;
	
	private Float orderPrice;

	private Integer cartId;

	private Integer customerId;

	private Timestamp createdAt;

	private List<Integer> orderedItemsIds;

	private Integer addressId;
	
	private String createdAtStr;

	public Integer getCustomerOrderId() {
		return customerOrderId;
	}

	public void setCustomerOrderId(Integer customerOrderId) {
		this.customerOrderId = customerOrderId;
	}

	public Float getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(Float orderPrice) {
		this.orderPrice = orderPrice;
	}

	public Integer getCartId() {
		return cartId;
	}

	public void setCartId(Integer cartId) {
		this.cartId = cartId;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public List<Integer> getOrderedItemsIds() {
		return orderedItemsIds;
	}

	public void setOrderedItemsIds(List<Integer> orderedItemsIds) {
		this.orderedItemsIds = orderedItemsIds;
	}

	public Integer getAddressId() {
		return addressId;
	}

	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}

	public String getCreatedAtStr() {
		return createdAtStr;
	}

	public void setCreatedAtStr(String createdAtStr) {
		this.createdAtStr = createdAtStr;
	}

	
	

}
