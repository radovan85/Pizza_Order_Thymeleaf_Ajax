package com.radovan.spring.dto;

import java.io.Serializable;

public class PizzaSizeDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer pizzaSizeId;

	private String name;

	private Float price;

	private Integer pizzaId;

	public Integer getPizzaSizeId() {
		return pizzaSizeId;
	}

	public void setPizzaSizeId(Integer pizzaSizeId) {
		this.pizzaSizeId = pizzaSizeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Integer getPizzaId() {
		return pizzaId;
	}

	public void setPizzaId(Integer pizzaId) {
		this.pizzaId = pizzaId;
	}

}
