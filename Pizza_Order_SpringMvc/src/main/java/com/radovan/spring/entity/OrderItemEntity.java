package com.radovan.spring.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "order_items")
public class OrderItemEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer orderItemId;

	private Integer quantity;

	private Float price;

	private String pizza;

	@Column(name = "pizza_size")
	private String pizzaSize;

	@Column(name = "pizza_price")
	private Float pizzaPrice;

	@ManyToOne
	@JoinColumn(name = "order_id")
	private OrderEntity order;

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

	public OrderEntity getOrder() {
		return order;
	}

	public void setOrder(OrderEntity order) {
		this.order = order;
	}

}
