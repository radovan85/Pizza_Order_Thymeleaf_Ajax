package com.radovan.spring.dto;

import java.io.Serializable;

public class OrderItemDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer orderItemId;

	private Integer quantity;

	private Float price;

	private String pizza;

	private String pizzaSize;

	private Float pizzaPrice;

	private Integer orderId;

	public Integer getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(Integer orderItemId) {
		this.orderItemId = orderItemId;
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

	public String getPizza() {
		return pizza;
	}

	public void setPizza(String pizza) {
		this.pizza = pizza;
	}

	public String getPizzaSize() {
		return pizzaSize;
	}

	public void setPizzaSize(String pizzaSize) {
		this.pizzaSize = pizzaSize;
	}

	public Float getPizzaPrice() {
		return pizzaPrice;
	}

	public void setPizzaPrice(Float pizzaPrice) {
		this.pizzaPrice = pizzaPrice;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

}
