package com.radovan.spring.service;

import java.util.List;

import com.radovan.spring.dto.CartItemDto;

public interface CartItemService {

	CartItemDto addCartItem(CartItemDto cartItem);

	void removeCartItem(Integer cartId, Integer itemId);

	void eraseAllCartItems(Integer cartId);

	List<CartItemDto> listAllByCartId(Integer cartId);
	
	List<CartItemDto> listAllByPizzaSizeId(Integer pizzaSizeId);

	CartItemDto getCartItem(Integer id);

	void eraseAllByPizzaSizeId(Integer pizzaSizeId);

}
