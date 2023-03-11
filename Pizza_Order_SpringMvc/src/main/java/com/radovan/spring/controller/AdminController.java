package com.radovan.spring.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.radovan.spring.dto.CartDto;
import com.radovan.spring.dto.CartItemDto;
import com.radovan.spring.dto.CustomerDto;
import com.radovan.spring.dto.OrderAddressDto;
import com.radovan.spring.dto.OrderDto;
import com.radovan.spring.dto.OrderItemDto;
import com.radovan.spring.dto.PizzaDto;
import com.radovan.spring.dto.PizzaSizeDto;
import com.radovan.spring.dto.ShippingAddressDto;
import com.radovan.spring.dto.UserDto;
import com.radovan.spring.exceptions.ImagePathException;
import com.radovan.spring.service.CartItemService;
import com.radovan.spring.service.CartService;
import com.radovan.spring.service.CustomerService;
import com.radovan.spring.service.OrderAddressService;
import com.radovan.spring.service.OrderItemService;
import com.radovan.spring.service.OrderService;
import com.radovan.spring.service.PizzaService;
import com.radovan.spring.service.PizzaSizeService;
import com.radovan.spring.service.ShippingAddressService;
import com.radovan.spring.service.UserService;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

	@Autowired
	private PizzaService pizzaService;

	@Autowired
	private PizzaSizeService pizzaSizeService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private UserService userService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderAddressService orderAddressService;

	@Autowired
	private OrderItemService orderItemService;

	@Autowired
	private CartItemService cartItemService;

	@Autowired
	private CartService cartService;
	
	@Autowired
	private ShippingAddressService shippingAddressService;

	@RequestMapping(value = "/createPizza", method = RequestMethod.GET)
	public String renderPizzaForm(ModelMap map) {
		PizzaDto pizza = new PizzaDto();
		map.put("pizza", pizza);
		return "fragments/pizzaForm :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/allPizzas", method = RequestMethod.GET)
	public String listAllPizzas(ModelMap map) {
		List<PizzaDto> allPizzas = pizzaService.listAll();
		map.put("allPizzas", allPizzas);
		map.put("recordsPerPage", 5);
		return "fragments/pizzaList :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/invalidPath", method = RequestMethod.GET)
	public String invalidImagePath() {
		return "fragments/invalidImagePath :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/createPizza", method = RequestMethod.POST)
	public String createBook(@ModelAttribute("pizza") PizzaDto pizza, ModelMap map,
			@RequestParam("pizzaImage") MultipartFile file, @RequestParam("imgName") String imgName) throws Throwable {

		String fileLocation = "C:\\Users\\Radovan\\eclipse-workspace\\Pizza_Order_SpringMvc\\src\\main\\resources\\static\\images\\pizzaImages";
		String imageUUID;

		Path locationPath = Paths.get(fileLocation);

		if (!Files.exists(locationPath)) {
			Error error = new Error("Invalid file path!");
			throw new ImagePathException(error);
		}

		imageUUID = file.getOriginalFilename();
		Path fileNameAndPath = Paths.get(fileLocation, imageUUID);

		if (file != null && !file.isEmpty()) {
			Files.write(fileNameAndPath, file.getBytes());
			System.out.println("IMage Save at:" + fileNameAndPath.toString());
		} else {
			imageUUID = imgName;
		}

		pizza.setImageName(imageUUID);
		pizzaService.addPizza(pizza);
		return "fragments/homePage :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/pizzaDetails/{pizzaId}", method = RequestMethod.GET)
	public String getPizzaDetails(@PathVariable("pizzaId") Integer pizzaId, ModelMap map) {
		PizzaDto pizza = pizzaService.getPizzaById(pizzaId);
		map.put("pizza", pizza);
		return "fragments/pizzaDetails :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/updatePizza/{pizzaId}", method = RequestMethod.GET)
	public String renderUpdatePizzaForm(@PathVariable("pizzaId") Integer pizzaId, ModelMap map) {
		PizzaDto pizza = new PizzaDto();
		PizzaDto currentPizza = pizzaService.getPizzaById(pizzaId);
		map.put("pizza", pizza);
		map.put("currentPizza", currentPizza);
		return "fragments/updatePizzaForm :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/deletePizza/{pizzaId}", method = RequestMethod.GET)
	public String deletePizza(@PathVariable("pizzaId") Integer pizzaId) throws Throwable {
		PizzaDto pizza = pizzaService.getPizzaById(pizzaId);
		List<PizzaSizeDto> allSizes = pizzaSizeService.listAllByPizzaId(pizzaId);
		for (PizzaSizeDto pizzaSize : allSizes) {
			cartItemService.eraseAllByPizzaSizeId(pizzaSize.getPizzaSizeId());
		}
		
		Path path = Paths.get(
				"C:\\Users\\Radovan\\eclipse-workspace\\Pizza_Order_SpringMvc\\src\\main\\resources\\static\\images\\pizzaImages\\"
						+ pizza.getImageName());

		if (Files.exists(path)) {
			Files.delete(path);
		} else {
			Error error = new Error("Invalid file path!");
			throw new ImagePathException(error);
		}

		pizzaSizeService.deleteAllByPizzaId(pizzaId);
		pizzaService.deletePizza(pizzaId);
		List<CartDto> allCarts = cartService.listAll();
		for (CartDto cart : allCarts) {
			cartService.refreshCartState(cart.getCartId());
		}
		return "fragments/homePage :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/sizeList/{pizzaId}", method = RequestMethod.GET)
	public String getSizeList(@PathVariable("pizzaId") Integer pizzaId, ModelMap map) {
		List<PizzaSizeDto> allPizzaSizes = pizzaSizeService.listAllByPizzaId(pizzaId);
		map.put("allPizzaSizes", allPizzaSizes);
		return "fragments/pizzaSizesForPizza :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/allSizes", method = RequestMethod.GET)
	public String getAllPizzaSizes(ModelMap map) {
		List<PizzaSizeDto> allPizzaSizes = pizzaSizeService.listAll();
		List<PizzaDto> allPizzas = pizzaService.listAll();
		map.put("allPizzaSizes", allPizzaSizes);
		map.put("allPizzas", allPizzas);
		map.put("recordsPerPage", 5);
		return "fragments/pizzaSizeList :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/allSizes/{pizzaId}", method = RequestMethod.GET)
	public String allSizesByPizzaId(@PathVariable("pizzaId") Integer pizzaId, ModelMap map) {
		List<PizzaSizeDto> allPizzaSizes = pizzaSizeService.listAllByPizzaId(pizzaId);
		PizzaDto pizza = pizzaService.getPizzaById(pizzaId);
		map.put("allPizzaSizes", allPizzaSizes);
		map.put("pizza", pizza);
		map.put("recordsPerPage", 5);
		return "fragments/pizzaSizeListByPizza :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/createPizzaSize", method = RequestMethod.GET)
	public String renderPizzaSizeForm(ModelMap map) {
		PizzaSizeDto pizzaSize = new PizzaSizeDto();
		List<PizzaDto> allPizzas = pizzaService.listAll();
		map.put("pizzaSize", pizzaSize);
		map.put("allPizzas", allPizzas);
		return "fragments/pizzaSizeForm :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/createPizzaSize", method = RequestMethod.POST)
	public String storePizzaSize(@ModelAttribute("pizzaSize") PizzaSizeDto pizzaSize) {
		Optional<Integer> pizzaSizeId = Optional.ofNullable(pizzaSize.getPizzaSizeId());
		pizzaSizeService.addPizzaSize(pizzaSize);
		if (pizzaSizeId.isPresent()) {
			List<CartItemDto> allItems = cartItemService.listAllByPizzaSizeId(pizzaSizeId.get());
			for (CartItemDto item : allItems) {
				item.setPrice(pizzaSize.getPrice() * item.getQuantity());
				cartItemService.addCartItem(item);
			}
			List<CartDto> allCarts = cartService.listAll();
			for (CartDto cart : allCarts) {
				cartService.refreshCartState(cart.getCartId());
			}
		}
		return "fragments/homePage :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/updatePizzaSize/{pizzaSizeId}", method = RequestMethod.GET)
	public String renderUpdatePizzaSizeForm(@PathVariable("pizzaSizeId") Integer pizzaSizeId, ModelMap map) {
		PizzaSizeDto pizzaSize = new PizzaSizeDto();
		PizzaSizeDto currentPizzaSize = pizzaSizeService.getPizzaSizeById(pizzaSizeId);
		List<PizzaDto> allPizzas = pizzaService.listAll();
		map.put("pizzaSize", pizzaSize);
		map.put("allPizzas", allPizzas);
		map.put("currentPizzaSize", currentPizzaSize);
		return "fragments/updatePizzaSizeForm :: ajaxLoadedContent";

	}

	@RequestMapping(value = "/deletePizzaSize/{pizzaSizeId}", method = RequestMethod.GET)
	public String deletePizzaSize(@PathVariable("pizzaSizeId") Integer pizzaSizeId) {
		cartItemService.eraseAllByPizzaSizeId(pizzaSizeId);
		pizzaSizeService.deletePizzaSize(pizzaSizeId);
		List<CartDto> allCarts = cartService.listAll();
		for (CartDto cart : allCarts) {
			cartService.refreshCartState(cart.getCartId());
		}
		return "fragments/homePage :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/pizzaSizeDetails/{pizzaSizeId}", method = RequestMethod.GET)
	public String getPizzaSizeDetails(@PathVariable("pizzaSizeId") Integer pizzaSizeId, ModelMap map) {
		PizzaSizeDto pizzaSize = pizzaSizeService.getPizzaSizeById(pizzaSizeId);
		PizzaDto pizza = pizzaService.getPizzaById(pizzaSize.getPizzaId());
		map.put("pizzaSize", pizzaSize);
		map.put("pizza", pizza);
		return "fragments/pizzaSizeDetails :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/allCustomers", method = RequestMethod.GET)
	public String getAllCustomers(ModelMap map) {
		List<CustomerDto> allCustomers = customerService.getAllCustomers();
		List<UserDto> allUsers = userService.listAllUsers();
		map.put("allCustomers", allCustomers);
		map.put("allUsers", allUsers);
		map.put("recordsPerPage", 8);
		return "fragments/customerList :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/customerDetails/{customerId}", method = RequestMethod.GET)
	public String getCustomerDetails(@PathVariable("customerId") Integer customerId, ModelMap map) {
		CustomerDto customer = customerService.getCustomer(customerId);
		UserDto user = userService.getUserById(customer.getUserId());
		map.put("customer", customer);
		map.put("user", user);
		return "fragments/customerDetails :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/suspendUser/{userId}", method = RequestMethod.GET)
	public String suspendUser(@PathVariable("userId") Integer userId) {
		userService.suspendUser(userId);
		return "fragments/homePage :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/clearSuspension/{userId}", method = RequestMethod.GET)
	public String removeSuspension(@PathVariable("userId") Integer userId) {
		userService.clearSuspension(userId);
		return "fragments/homePage :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/allOrders", method = RequestMethod.GET)
	public String listAllOrders(ModelMap map) {
		List<OrderDto> allOrders = orderService.listAll();
		List<CustomerDto> allCustomers = customerService.getAllCustomers();
		List<UserDto> allUsers = userService.listAllUsers();
		map.put("allOrders", allOrders);
		map.put("allCustomers", allCustomers);
		map.put("allUsers", allUsers);
		map.put("recordsPerPage", 8);
		return "fragments/orderList :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/deleteOrder/{orderId}", method = RequestMethod.GET)
	public String removeOrder(@PathVariable("orderId") Integer orderId) {
		orderService.deleteOrder(orderId);
		return "fragments/homePage :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/orderDetails/{orderId}", method = RequestMethod.GET)
	public String getOrderDetails(@PathVariable("orderId") Integer orderId, ModelMap map) {
		OrderDto order = orderService.getOrder(orderId);
		OrderAddressDto address = orderAddressService.getAddressById(order.getAddressId());
		List<OrderItemDto> orderedItems = orderItemService.listAllByOrderId(orderId);
		map.put("order", order);
		map.put("address", address);
		map.put("orderedItems", orderedItems);
		return "fragments/orderDetails :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/existingSizeError", method = RequestMethod.GET)
	public String sizeError() {
		return "fragments/pizzaSizeError :: ajaxLoadedContent";
	}
	
	@RequestMapping(value = "/deleteCustomer/{customerId}", method = RequestMethod.GET)
	public String removeCustomer(@PathVariable("customerId") Integer customerId) {
		CustomerDto customer = customerService.getCustomer(customerId);
		CartDto cart = cartService.getCartByCartId(customer.getCartId());
		ShippingAddressDto shippingAddress = shippingAddressService.getShippingAddress(customer.getShippingAddressId());
		UserDto user = userService.getUserById(customer.getUserId());
		
		List<OrderDto> allOrders = orderService.listAllByCustomerId(customerId);
		for(OrderDto order:allOrders) {
			orderItemService.eraseAllByOrderId(order.getCustomerOrderId());
			orderService.deleteOrder(order.getCustomerOrderId());
		}
			
		
		cartItemService.eraseAllCartItems(cart.getCartId());
		customerService.resetCustomer(customerId);
		shippingAddressService.deleteShippingAddress(shippingAddress.getShippingAddressId());
		cartService.deleteCart(cart.getCartId());		
		customerService.deleteCustomer(customerId);
		userService.deleteUser(user.getId());
		return "fragments/homePage :: ajaxLoadedContent";
	}


}
