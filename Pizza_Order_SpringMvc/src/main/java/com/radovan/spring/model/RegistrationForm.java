package com.radovan.spring.model;

import java.io.Serializable;

import com.radovan.spring.dto.CustomerDto;
import com.radovan.spring.dto.ShippingAddressDto;
import com.radovan.spring.dto.UserDto;

public class RegistrationForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private UserDto user;

	private ShippingAddressDto shippingAddress;

	private CustomerDto customer;

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	public ShippingAddressDto getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(ShippingAddressDto shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public CustomerDto getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerDto customer) {
		this.customer = customer;
	}

}
