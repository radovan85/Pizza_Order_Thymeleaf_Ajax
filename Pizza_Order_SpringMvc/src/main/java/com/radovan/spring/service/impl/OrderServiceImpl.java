package com.radovan.spring.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.radovan.spring.converter.TempConverter;
import com.radovan.spring.dto.OrderDto;
import com.radovan.spring.entity.CartEntity;
import com.radovan.spring.entity.CartItemEntity;
import com.radovan.spring.entity.CustomerEntity;
import com.radovan.spring.entity.OrderAddressEntity;
import com.radovan.spring.entity.OrderEntity;
import com.radovan.spring.entity.OrderItemEntity;
import com.radovan.spring.entity.ShippingAddressEntity;
import com.radovan.spring.entity.UserEntity;
import com.radovan.spring.repository.CartItemRepository;
import com.radovan.spring.repository.CustomerRepository;
import com.radovan.spring.repository.OrderAddressRepository;
import com.radovan.spring.repository.OrderItemRepository;
import com.radovan.spring.repository.OrderRepository;
import com.radovan.spring.service.CartService;
import com.radovan.spring.service.OrderService;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private TempConverter tempConverter;

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Autowired
	private CartItemRepository cartItemRepository;

	@Autowired
	private CartService cartService;

	@Autowired
	private OrderAddressRepository orderAddressRepository;

	@Override
	public OrderDto addCustomerOrder() {
		// TODO Auto-generated method stub
		UserEntity authUser = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CustomerEntity customerEntity = customerRepository.findByUserId(authUser.getId());
		ShippingAddressEntity shippingAddress = customerEntity.getShippingAddress();
		CartEntity cartEntity = customerEntity.getCart();
		OrderEntity orderEntity = new OrderEntity();
		List<OrderItemEntity> orderedItems = new ArrayList<OrderItemEntity>();

		Optional<List<CartItemEntity>> cartItems = Optional.ofNullable(cartEntity.getCartItems());

		if (!cartItems.isEmpty()) {
			for (CartItemEntity item : cartItems.get()) {
				OrderItemEntity orderedItem = tempConverter.cartItemToOrderItemEntity(item);
				OrderItemEntity storedOrderedItem = orderItemRepository.save(orderedItem);
				orderedItems.add(storedOrderedItem);
			}
		}

		OrderAddressEntity orderAddress = tempConverter.shippingAddressToOrderAddress(shippingAddress);
		OrderAddressEntity storedOrderAddress = orderAddressRepository.save(orderAddress);

		orderEntity.setCart(cartEntity);
		orderEntity.setCustomer(customerEntity);
		orderEntity.setOrderedItems(orderedItems);
		orderEntity.setAddress(storedOrderAddress);
		orderEntity.setOrderPrice(cartEntity.getCartPrice());

		OrderEntity storedOrder = orderRepository.save(orderEntity);

		storedOrderAddress.setOrder(storedOrder);
		orderAddressRepository.saveAndFlush(storedOrderAddress);

		OrderDto returnValue = tempConverter.orderEntityToDto(storedOrder);

		for (OrderItemEntity item : storedOrder.getOrderedItems()) {
			item.setOrder(storedOrder);
			orderItemRepository.saveAndFlush(item);
		}

		cartItemRepository.removeAllByCartId(cartEntity.getCartId());
		cartService.refreshCartState(cartEntity.getCartId());
		return returnValue;
	}

	@Override
	public List<OrderDto> listAll() {
		// TODO Auto-generated method stub
		List<OrderEntity> allOrders = orderRepository.findAll();
		List<OrderDto> returnValue = new ArrayList<>();

		for (OrderEntity order : allOrders) {
			OrderDto orderDto = tempConverter.orderEntityToDto(order);
			returnValue.add(orderDto);
		}
		return returnValue;
	}

	@Override
	public Float calculateOrderTotal(Integer orderId) {
		// TODO Auto-generated method stub
		Optional<Float> orderTotal = Optional.ofNullable(orderItemRepository.calculateGrandTotal(orderId));
		Float returnValue = 0f;

		if (orderTotal.isPresent()) {
			returnValue = orderTotal.get();
		}

		return returnValue;
	}

	@Override
	public OrderDto getOrder(Integer orderId) {
		// TODO Auto-generated method stub
		OrderEntity orderEntity = orderRepository.getById(orderId);
		OrderDto returnValue = tempConverter.orderEntityToDto(orderEntity);
		return returnValue;
	}

	@Override
	public void deleteOrder(Integer orderId) {
		// TODO Auto-generated method stub
		orderRepository.deleteById(orderId);
		orderRepository.flush();

	}
	
	@Override
	public List<OrderDto> listAllByCustomerId(Integer customerId) {
		// TODO Auto-generated method stub
		List<OrderDto> returnValue = new ArrayList<OrderDto>();
		Optional<List<OrderEntity>> allOrders = 
				Optional.ofNullable(orderRepository.findAllByCustomerId(customerId));
		if(!allOrders.isEmpty()) {
			for(OrderEntity order : allOrders.get()) {
				OrderDto orderDto = tempConverter.orderEntityToDto(order);
				returnValue.add(orderDto);
			}
		}
		return returnValue;
	}

}
