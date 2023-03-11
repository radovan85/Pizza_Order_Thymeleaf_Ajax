package com.radovan.spring.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "pizzas")
public class PizzaEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "pizza_id")
	private Integer pizzaId;
	
	private String name;
	
	private String description;
	
	@Transient
	@OneToMany(mappedBy = "pizza", cascade = CascadeType.ALL,fetch=FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<PizzaSizeEntity> pizzaSizes;
	
	@Column(name = "image_name")
	private String imageName;
	
	@Transient
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

	public List<PizzaSizeEntity> getPizzaSizes() {
		return pizzaSizes;
	}

	public void setPizzaSizes(List<PizzaSizeEntity> pizzaSizes) {
		this.pizzaSizes = pizzaSizes;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	
	
	
	
	
	

}
