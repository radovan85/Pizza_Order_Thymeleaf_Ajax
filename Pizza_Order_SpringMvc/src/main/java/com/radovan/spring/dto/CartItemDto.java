package com.radovan.spring.dto;

import java.io.Serializable;

public class CartItemDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer cartItemId;

	private Integer quantity;

	private Float price;

	private Integer pizzaSizeId;

	private Integer cartId;

	public Integer getCartItemId() {
		return cartItemId;
	}

	public void setCartItemId(Integer cartItemId) {
		this.cartItemId = cartItemId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Integer getCartId() {
		return cartId;
	}

	public void setCartId(Integer cartId) {
		this.cartId = cartId;
	}

	public Integer getPizzaSizeId() {
		return pizzaSizeId;
	}

	public void setPizzaSizeId(Integer pizzaSizeId) {
		this.pizzaSizeId = pizzaSizeId;
	}

}
