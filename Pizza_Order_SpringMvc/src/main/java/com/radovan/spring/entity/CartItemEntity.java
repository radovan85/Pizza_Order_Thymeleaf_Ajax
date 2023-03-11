package com.radovan.spring.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "cart_items")
public class CartItemEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	private Integer cartItemId;

	private Integer quantity;

	private Float price;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "size_id")
	private PizzaSizeEntity pizzaSize;

	@ManyToOne
	@JoinColumn(name = "cart_id")
	private CartEntity cart;

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

	public CartEntity getCart() {
		return cart;
	}

	public void setCart(CartEntity cart) {
		this.cart = cart;
	}

	public PizzaSizeEntity getPizzaSize() {
		return pizzaSize;
	}

	public void setPizzaSize(PizzaSizeEntity pizzaSize) {
		this.pizzaSize = pizzaSize;
	}

}
