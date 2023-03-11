package com.radovan.spring.converter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.radovan.spring.dto.CartDto;
import com.radovan.spring.dto.CartItemDto;
import com.radovan.spring.dto.CustomerDto;
import com.radovan.spring.dto.OrderAddressDto;
import com.radovan.spring.dto.OrderDto;
import com.radovan.spring.dto.OrderItemDto;
import com.radovan.spring.dto.PizzaDto;
import com.radovan.spring.dto.PizzaSizeDto;
import com.radovan.spring.dto.RoleDto;
import com.radovan.spring.dto.ShippingAddressDto;
import com.radovan.spring.dto.UserDto;
import com.radovan.spring.entity.CartEntity;
import com.radovan.spring.entity.CartItemEntity;
import com.radovan.spring.entity.CustomerEntity;
import com.radovan.spring.entity.OrderAddressEntity;
import com.radovan.spring.entity.OrderEntity;
import com.radovan.spring.entity.OrderItemEntity;
import com.radovan.spring.entity.PizzaEntity;
import com.radovan.spring.entity.PizzaSizeEntity;
import com.radovan.spring.entity.RoleEntity;
import com.radovan.spring.entity.ShippingAddressEntity;
import com.radovan.spring.entity.UserEntity;
import com.radovan.spring.repository.CartItemRepository;
import com.radovan.spring.repository.CartRepository;
import com.radovan.spring.repository.CustomerRepository;
import com.radovan.spring.repository.OrderAddressRepository;
import com.radovan.spring.repository.OrderItemRepository;
import com.radovan.spring.repository.OrderRepository;
import com.radovan.spring.repository.PizzaRepository;
import com.radovan.spring.repository.PizzaSizeRepository;
import com.radovan.spring.repository.RoleRepository;
import com.radovan.spring.repository.ShippingAddressRepository;
import com.radovan.spring.repository.UserRepository;

public class TempConverter {

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ShippingAddressRepository shippingAddressRepository;

	@Autowired
	private PizzaRepository pizzaRepository;

	@Autowired
	private PizzaSizeRepository pizzaSizeRepository;

	@Autowired
	private CartItemRepository cartItemRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderAddressRepository orderAddressRepository;

	public CartDto cartEntityToDto(CartEntity cartEntity) {
		CartDto returnValue = mapper.map(cartEntity, CartDto.class);
		Optional<Float> cartPrice = Optional.ofNullable(cartEntity.getCartPrice());
		if (!cartPrice.isPresent()) {
			returnValue.setCartPrice(0f);
		}
		Optional<CustomerEntity> customerEntity = Optional.ofNullable(cartEntity.getCustomer());
		if (customerEntity.isPresent()) {
			returnValue.setCustomerId(customerEntity.get().getCustomerId());
		}

		List<Integer> itemsIds = new ArrayList<>();
		Optional<List<CartItemEntity>> cartItems = Optional.ofNullable(cartEntity.getCartItems());
		if (!cartItems.isEmpty()) {
			for (CartItemEntity itemEntity : cartItems.get()) {
				Integer itemId = itemEntity.getCartItemId();
				itemsIds.add(itemId);
			}

		}
		returnValue.setCartItemsIds(itemsIds);
		return returnValue;

	}

	public CartEntity cartDtoToEntity(CartDto cartDto) {
		CartEntity returnValue = mapper.map(cartDto, CartEntity.class);
		Optional<Float> cartPrice = Optional.ofNullable(cartDto.getCartPrice());
		if (!cartPrice.isPresent()) {
			returnValue.setCartPrice(0f);
		}
		Optional<Integer> customerId = Optional.ofNullable(cartDto.getCustomerId());
		if (customerId.isPresent()) {
			CustomerEntity customerEntity = customerRepository.getById(customerId.get());
			returnValue.setCustomer(customerEntity);
		}

		List<CartItemEntity> cartItems = new ArrayList<>();
		Optional<List<Integer>> itemIds = Optional.ofNullable(cartDto.getCartItemsIds());

		if (!itemIds.isEmpty()) {
			for (Integer itemId : itemIds.get()) {
				CartItemEntity itemEntity = cartItemRepository.getById(itemId);
				cartItems.add(itemEntity);
			}

		}
		returnValue.setCartItems(cartItems);
		return returnValue;
	}

	public CartItemDto cartItemEntityToDto(CartItemEntity cartItemEntity) {
		CartItemDto returnValue = mapper.map(cartItemEntity, CartItemDto.class);

		Optional<PizzaSizeEntity> pizzaSize = Optional.ofNullable(cartItemEntity.getPizzaSize());
		if (pizzaSize.isPresent()) {
			Float price = pizzaSize.get().getPrice();
			Integer quantity = returnValue.getQuantity();
			price = price * quantity;
			returnValue.setPrice(price);
			returnValue.setPizzaSizeId(pizzaSize.get().getPizzaSizeId());
		}

		Optional<CartEntity> cart = Optional.ofNullable(cartItemEntity.getCart());
		if (cart.isPresent()) {
			returnValue.setCartId(cart.get().getCartId());
		}

		return returnValue;
	}

	public CartItemEntity cartItemDtoToEntity(CartItemDto cartItemDto) {
		CartItemEntity returnValue = mapper.map(cartItemDto, CartItemEntity.class);
		Optional<Integer> cartId = Optional.ofNullable(cartItemDto.getCartId());
		if (cartId.isPresent()) {
			CartEntity cartEntity = cartRepository.getById(cartId.get());
			returnValue.setCart(cartEntity);
		}

		Optional<Integer> pizzaSizeId = Optional.ofNullable(cartItemDto.getPizzaSizeId());
		if (pizzaSizeId.isPresent()) {
			PizzaSizeEntity pizzaSizeEntity = pizzaSizeRepository.getById(pizzaSizeId.get());
			Float price = pizzaSizeEntity.getPrice();
			Integer quantity = returnValue.getQuantity();
			price = price * quantity;
			returnValue.setPrice(price);
			returnValue.setPizzaSize(pizzaSizeEntity);
		}

		return returnValue;
	}

	public CustomerDto customerEntityToDto(CustomerEntity customerEntity) {
		CustomerDto returnValue = mapper.map(customerEntity, CustomerDto.class);

		Optional<ShippingAddressEntity> shippingAddressEntity = Optional
				.ofNullable(customerEntity.getShippingAddress());
		if (shippingAddressEntity.isPresent()) {
			returnValue.setShippingAddressId(shippingAddressEntity.get().getShippingAddressId());
		}

		Optional<CartEntity> cartEntity = Optional.ofNullable(customerEntity.getCart());
		if (cartEntity.isPresent()) {
			returnValue.setCartId(cartEntity.get().getCartId());
		}

		Optional<UserEntity> userEntity = Optional.ofNullable(customerEntity.getUser());
		if (userEntity.isPresent()) {
			returnValue.setUserId(userEntity.get().getId());
		}

		return returnValue;
	}

	public CustomerEntity customerDtoToEntity(CustomerDto customerDto) {
		CustomerEntity returnValue = mapper.map(customerDto, CustomerEntity.class);

		Optional<Integer> shippingAddressId = Optional.ofNullable(customerDto.getShippingAddressId());
		if (shippingAddressId.isPresent()) {
			ShippingAddressEntity shippingAddressEntity = shippingAddressRepository.getById(shippingAddressId.get());
			returnValue.setShippingAddress(shippingAddressEntity);
		}

		Optional<Integer> cartId = Optional.ofNullable(customerDto.getCartId());
		if (cartId.isPresent()) {
			CartEntity cartEntity = cartRepository.getById(cartId.get());
			returnValue.setCart(cartEntity);
		}

		Optional<Integer> userId = Optional.ofNullable(customerDto.getUserId());
		if (userId.isPresent()) {
			UserEntity userEntity = userRepository.getById(userId.get());
			returnValue.setUser(userEntity);
		}

		return returnValue;
	}

	public PizzaDto pizzaEntityToDto(PizzaEntity pizzaEntity) {
		PizzaDto returnValue = mapper.map(pizzaEntity, PizzaDto.class);

		Optional<List<PizzaSizeEntity>> pizzaSizes = Optional.ofNullable(pizzaEntity.getPizzaSizes());
		List<Integer> pizzaSizesIds = new ArrayList<Integer>();
		if (!pizzaSizes.isEmpty()) {
			for (PizzaSizeEntity size : pizzaSizes.get()) {
				pizzaSizesIds.add(size.getPizzaSizeId());
			}
		}

		returnValue.setPizzaSizesIds(pizzaSizesIds);
		return returnValue;
	}

	public PizzaEntity pizzaDtoToEntity(PizzaDto pizzaDto) {
		PizzaEntity returnValue = mapper.map(pizzaDto, PizzaEntity.class);

		Optional<List<Integer>> pizzaSizeIds = Optional.ofNullable(pizzaDto.getPizzaSizesIds());
		List<PizzaSizeEntity> pizzaSizes = new ArrayList<PizzaSizeEntity>();

		if (!pizzaSizeIds.isEmpty()) {
			for (Integer sizeId : pizzaSizeIds.get()) {
				PizzaSizeEntity pizzaSizeEntity = pizzaSizeRepository.getById(sizeId);
				pizzaSizes.add(pizzaSizeEntity);
			}
		}

		returnValue.setPizzaSizes(pizzaSizes);
		return returnValue;
	}

	public PizzaSizeDto pizzaSizeEntityToDto(PizzaSizeEntity sizeEntity) {
		PizzaSizeDto returnValue = mapper.map(sizeEntity, PizzaSizeDto.class);
		Optional<PizzaEntity> pizzaEntity = Optional.ofNullable(sizeEntity.getPizza());
		if (pizzaEntity.isPresent()) {
			returnValue.setPizzaId(pizzaEntity.get().getPizzaId());
		}

		return returnValue;
	}

	public PizzaSizeEntity pizzaSizeDtoToEntity(PizzaSizeDto sizeDto) {
		PizzaSizeEntity returnValue = mapper.map(sizeDto, PizzaSizeEntity.class);
		Optional<Integer> pizzaId = Optional.ofNullable(sizeDto.getPizzaId());
		if (pizzaId.isPresent()) {
			PizzaEntity pizzaEntity = pizzaRepository.getById(pizzaId.get());
			returnValue.setPizza(pizzaEntity);
		}

		return returnValue;
	}

	public OrderDto orderEntityToDto(OrderEntity orderEntity) {
		OrderDto returnValue = mapper.map(orderEntity, OrderDto.class);

		Optional<OrderAddressEntity> addressEntity = Optional.ofNullable(orderEntity.getAddress());
		if (addressEntity.isPresent()) {
			returnValue.setAddressId(addressEntity.get().getOrderAddressId());
		}

		Optional<CustomerEntity> customerEntity = Optional.ofNullable(orderEntity.getCustomer());
		if (customerEntity.isPresent()) {
			returnValue.setCustomerId(customerEntity.get().getCustomerId());
		}

		Optional<CartEntity> cartEntity = Optional.ofNullable(orderEntity.getCart());
		if (cartEntity.isPresent()) {
			returnValue.setCartId(cartEntity.get().getCartId());
		}

		List<Integer> orderedItemsIds = new ArrayList<>();
		Optional<List<OrderItemEntity>> orderedItems = Optional.ofNullable(orderEntity.getOrderedItems());
		if (!orderedItems.isEmpty()) {
			for (OrderItemEntity item : orderedItems.get()) {
				Integer itemId = item.getOrderItemId();
				orderedItemsIds.add(itemId);
			}
		}
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		
		Optional<Timestamp> createdAtOpt = 
				Optional.ofNullable(orderEntity.getCreatedAt());
		if(createdAtOpt.isPresent()) {
			LocalDateTime createdAtLocal = createdAtOpt.get().toLocalDateTime();
			String createdAtStr = createdAtLocal.format(formatter);
			returnValue.setCreatedAtStr(createdAtStr);
		}
		
		returnValue.setOrderedItemsIds(orderedItemsIds);

		return returnValue;
	}

	public OrderEntity orderDtoToEntity(OrderDto orderDto) {
		OrderEntity returnValue = mapper.map(orderDto, OrderEntity.class);

		Optional<Integer> addressId = Optional.ofNullable(orderDto.getAddressId());
		if (addressId.isPresent()) {
			OrderAddressEntity address = orderAddressRepository.getById(addressId.get());
			returnValue.setAddress(address);
		}

		Optional<Integer> customerId = Optional.ofNullable(orderDto.getCustomerId());
		if (customerId.isPresent()) {
			CustomerEntity customerEntity = customerRepository.getById(customerId.get());
			returnValue.setCustomer(customerEntity);
		}

		Optional<Integer> cartId = Optional.ofNullable(orderDto.getCartId());
		if (cartId.isPresent()) {
			CartEntity cartEntity = cartRepository.getById(cartId.get());
			returnValue.setCart(cartEntity);
		}

		List<OrderItemEntity> orderedItems = new ArrayList<>();
		Optional<List<Integer>> orderedItemsIds = Optional.ofNullable(orderDto.getOrderedItemsIds());
		if (!orderedItemsIds.isEmpty()) {
			for (Integer itemId : orderedItemsIds.get()) {
				OrderItemEntity itemEntity = orderItemRepository.getById(itemId);
				orderedItems.add(itemEntity);
			}
		}

		returnValue.setOrderedItems(orderedItems);
		return returnValue;
	}

	public OrderAddressDto orderAddressEntityToDto(OrderAddressEntity address) {
		OrderAddressDto returnValue = mapper.map(address, OrderAddressDto.class);
		Optional<OrderEntity> orderEntity = Optional.ofNullable(address.getOrder());
		if (orderEntity.isPresent()) {
			returnValue.setOrderId(orderEntity.get().getCustomerOrderId());
		}

		return returnValue;
	}

	public OrderAddressEntity orderAddressDtoToEntity(OrderAddressDto address) {
		OrderAddressEntity returnValue = mapper.map(address, OrderAddressEntity.class);
		Optional<Integer> orderId = Optional.ofNullable(address.getOrderId());
		if (orderId.isPresent()) {
			OrderEntity orderEntity = orderRepository.getById(orderId.get());
			returnValue.setOrder(orderEntity);
		}

		return returnValue;
	}

	public OrderItemDto orderItemEntityToDto(OrderItemEntity itemEntity) {
		OrderItemDto returnValue = mapper.map(itemEntity, OrderItemDto.class);

		Optional<OrderEntity> orderEntity = Optional.ofNullable(itemEntity.getOrder());
		if (orderEntity.isPresent()) {
			returnValue.setOrderId(orderEntity.get().getCustomerOrderId());
		}

		return returnValue;
	}

	public OrderItemEntity orderItemDtoToEntity(OrderItemDto itemDto) {
		OrderItemEntity returnValue = mapper.map(itemDto, OrderItemEntity.class);

		Optional<Integer> orderId = Optional.ofNullable(itemDto.getOrderId());
		if (orderId.isPresent()) {
			OrderEntity orderEntity = orderRepository.getById(orderId.get());
			returnValue.setOrder(orderEntity);
		}

		return returnValue;
	}

	public OrderItemEntity cartItemToOrderItemEntity(CartItemEntity cartItemEntity) {
		OrderItemEntity returnValue = mapper.map(cartItemEntity, OrderItemEntity.class);

		Optional<PizzaSizeEntity> pizzaSize = Optional.ofNullable(cartItemEntity.getPizzaSize());
		if (pizzaSize.isPresent()) {
			Optional<PizzaEntity> pizzaEntity = Optional.ofNullable(pizzaSize.get().getPizza());
			if (pizzaEntity.isPresent()) {
				returnValue.setPizza(pizzaEntity.get().getName());
				returnValue.setPizzaSize(pizzaSize.get().getName());
				returnValue.setPizzaPrice(pizzaSize.get().getPrice());
				returnValue.setPrice(pizzaSize.get().getPrice() * returnValue.getQuantity());
			}
		}

		return returnValue;
	}

	public ShippingAddressDto shippingAddressEntityToDto(ShippingAddressEntity addressEntity) {
		ShippingAddressDto returnValue = mapper.map(addressEntity, ShippingAddressDto.class);

		Optional<CustomerEntity> customerEntity = Optional.ofNullable(addressEntity.getCustomer());
		if (customerEntity.isPresent()) {
			returnValue.setCustomerId(customerEntity.get().getCustomerId());
		}

		return returnValue;
	}

	public ShippingAddressEntity shippingAddressDtoToEntity(ShippingAddressDto addressDto) {
		ShippingAddressEntity returnValue = mapper.map(addressDto, ShippingAddressEntity.class);

		Optional<Integer> customerId = Optional.ofNullable(addressDto.getCustomerId());
		if (customerId.isPresent()) {
			CustomerEntity customerEntity = customerRepository.getById(customerId.get());
			returnValue.setCustomer(customerEntity);
		}

		return returnValue;
	}

	public UserDto userEntityToDto(UserEntity userEntity) {
		UserDto returnValue = mapper.map(userEntity, UserDto.class);
		returnValue.setEnabled(userEntity.getEnabled());
		Optional<List<RoleEntity>> roles = Optional.ofNullable(userEntity.getRoles());
		List<Integer> rolesIds = new ArrayList<Integer>();

		if (!roles.isEmpty()) {
			for (RoleEntity roleEntity : roles.get()) {
				rolesIds.add(roleEntity.getId());
			}
		}

		returnValue.setRolesIds(rolesIds);

		return returnValue;
	}

	public UserEntity userDtoToEntity(UserDto userDto) {
		UserEntity returnValue = mapper.map(userDto, UserEntity.class);
		List<RoleEntity> roles = new ArrayList<>();
		Optional<List<Integer>> rolesIds = Optional.ofNullable(userDto.getRolesIds());

		if (!rolesIds.isEmpty()) {
			for (Integer roleId : rolesIds.get()) {
				RoleEntity role = roleRepository.getById(roleId);
				roles.add(role);
			}
		}

		returnValue.setRoles(roles);

		return returnValue;
	}

	public RoleDto roleEntityToDto(RoleEntity roleEntity) {
		RoleDto returnValue = mapper.map(roleEntity, RoleDto.class);
		Optional<List<UserEntity>> users = Optional.ofNullable(roleEntity.getUsers());
		List<Integer> userIds = new ArrayList<>();

		if (!users.isEmpty()) {
			for (UserEntity user : users.get()) {
				userIds.add(user.getId());
			}
		}

		returnValue.setUserIds(userIds);
		return returnValue;
	}

	public RoleEntity roleDtoToEntity(RoleDto roleDto) {
		RoleEntity returnValue = mapper.map(roleDto, RoleEntity.class);
		Optional<List<Integer>> usersIds = Optional.ofNullable(roleDto.getUserIds());
		List<UserEntity> users = new ArrayList<>();

		if (!usersIds.isEmpty()) {
			for (Integer userId : usersIds.get()) {
				UserEntity userEntity = userRepository.getById(userId);
				users.add(userEntity);
			}
		}
		returnValue.setUsers(users);
		return returnValue;
	}

	public OrderAddressEntity shippingAddressToOrderAddress(ShippingAddressEntity shippingAddress) {
		// TODO Auto-generated method stub
		OrderAddressEntity returnValue = mapper.map(shippingAddress, OrderAddressEntity.class);
		return returnValue;
	}
}
