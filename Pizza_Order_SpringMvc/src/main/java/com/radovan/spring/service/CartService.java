package com.radovan.spring.service;

import java.util.List;

import com.radovan.spring.dto.CartDto;

public interface CartService {

	CartDto getCartByCartId(Integer cartId);

	void refreshCartState(Integer cartId);

	Float calculateGrandTotal(Integer cartId);

	CartDto validateCart(Integer cartId);
	
	List<CartDto> listAll();

	void deleteCart(Integer cartId);
	
	

}
