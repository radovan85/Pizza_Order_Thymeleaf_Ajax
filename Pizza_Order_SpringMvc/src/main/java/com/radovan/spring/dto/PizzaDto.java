package com.radovan.spring.dto;

import java.io.Serializable;
import java.util.List;

public class PizzaDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer pizzaId;

	private String name;

	private String description;

	private List<Integer> pizzaSizesIds;

	private String imageName;

	public String getMainImagePath() {
		if (pizzaId == null || imageName == null)
			return "/images/pizzaImages/unknown.jpg";
		return "/images/pizzaImages/" + this.imageName;
	}

	public Integer getPizzaId() {
		return pizzaId;
	}

	public void setPizzaId(Integer pizzaId) {
		this.pizzaId = pizzaId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Integer> getPizzaSizesIds() {
		return pizzaSizesIds;
	}

	public void setPizzaSizesIds(List<Integer> pizzaSizesIds) {
		this.pizzaSizesIds = pizzaSizesIds;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

}
