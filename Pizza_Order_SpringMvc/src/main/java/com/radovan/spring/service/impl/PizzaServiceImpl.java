package com.radovan.spring.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.radovan.spring.converter.TempConverter;
import com.radovan.spring.dto.PizzaDto;
import com.radovan.spring.entity.PizzaEntity;
import com.radovan.spring.repository.PizzaRepository;
import com.radovan.spring.service.PizzaService;

@Service
@Transactional
public class PizzaServiceImpl implements PizzaService {

	@Autowired
	private PizzaRepository pizzaRepository;

	@Autowired
	private TempConverter tempConverter;

	@Override
	public List<PizzaDto> listAll() {
		// TODO Auto-generated method stub
		Optional<List<PizzaEntity>> allPizzas = Optional.ofNullable(pizzaRepository.findAll());
		List<PizzaDto> returnValue = new ArrayList<PizzaDto>();

		if (!allPizzas.isEmpty()) {
			for (PizzaEntity pizza : allPizzas.get()) {
				PizzaDto pizzaDto = tempConverter.pizzaEntityToDto(pizza);
				returnValue.add(pizzaDto);
			}
		}
		return returnValue;
	}

	@Override
	public PizzaDto getPizzaById(Integer pizzaId) {
		// TODO Auto-generated method stub
		PizzaDto returnValue = null;
		Optional<PizzaEntity> pizzaEntity = Optional.ofNullable(pizzaRepository.getById(pizzaId));
		if (pizzaEntity.isPresent()) {
			returnValue = tempConverter.pizzaEntityToDto(pizzaEntity.get());
		}
		return returnValue;
	}

	@Override
	public void deletePizza(Integer pizzaId) {
		// TODO Auto-generated method stub

		pizzaRepository.deleteById(pizzaId);
		pizzaRepository.flush();
	}

	@Override
	public PizzaDto addPizza(PizzaDto pizza) {
		// TODO Auto-generated method stub
		PizzaEntity pizzaEntity = tempConverter.pizzaDtoToEntity(pizza);
		PizzaEntity storedPizza = pizzaRepository.save(pizzaEntity);
		PizzaDto returnValue = tempConverter.pizzaEntityToDto(storedPizza);
		return returnValue;
	}

	@Override
	public List<PizzaDto> listAllByKeyword(String keyword) {
		// TODO Auto-generated method stub
		Optional<List<PizzaEntity>> allPizzas = Optional.ofNullable(pizzaRepository.findAllByKeyword(keyword));
		List<PizzaDto> returnValue = new ArrayList<PizzaDto>();

		if (!allPizzas.isEmpty()) {
			for (PizzaEntity pizza : allPizzas.get()) {
				PizzaDto pizzaDto = tempConverter.pizzaEntityToDto(pizza);
				returnValue.add(pizzaDto);
			}
		}
		return returnValue;
	}

}
