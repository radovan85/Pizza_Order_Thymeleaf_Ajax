package com.radovan.spring.service;

import java.util.List;

import com.radovan.spring.dto.PizzaDto;

public interface PizzaService {

	List<PizzaDto> listAll();

	PizzaDto getPizzaById(Integer pizzaId);

	void deletePizza(Integer pizzaId);

	PizzaDto addPizza(PizzaDto pizza);

	List<PizzaDto> listAllByKeyword(String keyword);
	
}
