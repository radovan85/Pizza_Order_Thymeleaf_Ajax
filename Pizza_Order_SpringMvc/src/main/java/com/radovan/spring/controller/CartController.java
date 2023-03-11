package com.radovan.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.radovan.spring.dto.CartDto;
import com.radovan.spring.dto.CartItemDto;
import com.radovan.spring.dto.CustomerDto;
import com.radovan.spring.dto.PizzaDto;
import com.radovan.spring.dto.PizzaSizeDto;
import com.radovan.spring.dto.UserDto;
import com.radovan.spring.exceptions.CartItemsNumberException;
import com.radovan.spring.service.CartItemService;
import com.radovan.spring.service.CartService;
import com.radovan.spring.service.CustomerService;
import com.radovan.spring.service.PizzaService;
import com.radovan.spring.service.PizzaSizeService;
import com.radovan.spring.service.UserService;

@Controller
@RequestMapping(value = "/carts")
public class CartController {

	@Autowired
	private PizzaService pizzaService;

	@Autowired
	private PizzaSizeService pizzaSizeService;

	@Autowired
	private CartItemService cartItemService;

	@Autowired
	private UserService userService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CartService cartService;

	@RequestMapping(value = "/addToCart/{pizzaId}", method = RequestMethod.GET)
	public String addToCart(@PathVariable("pizzaId") Integer pizzaId, ModelMap map) {
		CartItemDto cartItem = new CartItemDto();
		List<PizzaSizeDto> allPizzaSizes = pizzaSizeService.listAllByPizzaId(pizzaId);
		PizzaDto pizza = pizzaService.getPizzaById(pizzaId);
		map.put("allPizzaSizes", allPizzaSizes);
		map.put("pizza", pizza);
		map.put("cartItem", cartItem);
		return "fragments/addPizzaToCart :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/storeCartItem", method = RequestMethod.POST)
	public String storeCartItem(@ModelAttribute("cartItem") CartItemDto cartItem) {
		Integer pizzaSizeId = cartItem.getPizzaSizeId();
		UserDto authUser = userService.getCurrentUser();
		CustomerDto customer = customerService.getCustomerByUserId(authUser.getId());
		Integer cartId = customer.getCartId();
		cartItem.setCartId(cartId);
		List<CartItemDto> cartItems = cartItemService.listAllByCartId(cartId);
		Integer pizzaNumber = 0;
		for(CartItemDto itemDto : cartItems) {
			pizzaNumber = pizzaNumber + itemDto.getQuantity();
		}
		pizzaNumber = pizzaNumber + cartItem.getQuantity();
		if(pizzaNumber > 20) {
			Error error = new Error("Maximum 20 items allowed");
			throw new CartItemsNumberException(error);
		}
		for (CartItemDto itemDto : cartItems) {
			if (itemDto.getPizzaSizeId() == pizzaSizeId) {
				Integer quantity = cartItem.getQuantity() + itemDto.getQuantity();
				cartItem.setQuantity(quantity);
				cartItem.setCartItemId(itemDto.getCartItemId());
				cartItemService.addCartItem(cartItem);
				cartService.refreshCartState(cartId);
				return "fragments/homePage :: ajaxLoadedContent";
			}
		}
		cartItemService.addCartItem(cartItem);
		cartService.refreshCartState(cartId);
		return "fragments/homePage :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/itemAdded", method = RequestMethod.GET)
	public String itemAddedToCart() {
		return "fragments/itemAdded :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/cart", method = RequestMethod.GET)
	public String goToCart(ModelMap map) {
		UserDto authUser = userService.getCurrentUser();
		CustomerDto customer = customerService.getCustomerByUserId(authUser.getId());
		CartDto cart = cartService.getCartByCartId(customer.getCartId());
		List<CartItemDto> allCartItems = cartItemService.listAllByCartId(cart.getCartId());
		List<PizzaSizeDto> allPizzaSizes = pizzaSizeService.listAll();
		List<PizzaDto> allPizzas = pizzaService.listAll();
		map.put("allCartItems", allCartItems);
		map.put("allPizzas", allPizzas);
		map.put("allPizzaSizes", allPizzaSizes);
		map.put("cart", cart);
		return "fragments/cart :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/deleteItem/{cartId}/{itemId}", method = RequestMethod.GET)
	public String deleteItem(@PathVariable("cartId") Integer cartId, @PathVariable("itemId") Integer itemId) {
		cartItemService.removeCartItem(cartId, itemId);
		return "fragments/homePage :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/deleteAllItems/{cartId}", method = RequestMethod.GET)
	public String clearCart(@PathVariable("cartId") Integer cartId) {
		cartItemService.eraseAllCartItems(cartId);
		return "fragments/homePage :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/validateCart/{cartId}", method = RequestMethod.GET)
	public String cartValidation(@PathVariable("cartId") Integer cartId) {
		cartService.validateCart(cartId);
		return "fragments/homePage :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/cartError", method = RequestMethod.GET)
	public String cartWarning() {
		return "fragments/invalidCart :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/itemsError", method = RequestMethod.GET)
	public String itemNumberErr() {
		return "fragments/itemsNumberError :: ajaxLoadedContent";
	}
}
