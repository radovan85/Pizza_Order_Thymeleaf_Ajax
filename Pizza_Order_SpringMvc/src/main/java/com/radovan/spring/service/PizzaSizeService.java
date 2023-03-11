package com.radovan.spring.service;

import java.util.List;

import com.radovan.spring.dto.PizzaSizeDto;

public interface PizzaSizeService {

	PizzaSizeDto addPizzaSize(PizzaSizeDto pizzaSize);
	PizzaSizeDto getPizzaSizeById(Integer pizzaSizeId);
	void deletePizzaSize(Integer pizzaSizeId);
	void deleteAllByPizzaId(Integer pizzaId);
	List<PizzaSizeDto> listAll();
	List<PizzaSizeDto> listAllByPizzaId(Integer pizzaId);
	
	
}
