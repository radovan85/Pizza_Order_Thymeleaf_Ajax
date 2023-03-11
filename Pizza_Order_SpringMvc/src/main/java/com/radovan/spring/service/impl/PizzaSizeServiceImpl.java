package com.radovan.spring.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.radovan.spring.converter.TempConverter;
import com.radovan.spring.dto.PizzaSizeDto;
import com.radovan.spring.entity.PizzaSizeEntity;
import com.radovan.spring.exceptions.ExistingSizeException;
import com.radovan.spring.repository.PizzaSizeRepository;
import com.radovan.spring.service.PizzaSizeService;

@Service
@Transactional
public class PizzaSizeServiceImpl implements PizzaSizeService {

	@Autowired
	private PizzaSizeRepository pizzaSizeRepository;

	@Autowired
	private TempConverter tempConverter;

	@Override
	public PizzaSizeDto addPizzaSize(PizzaSizeDto pizzaSize) {
		// TODO Auto-generated method stub
		Optional<Integer> sizeId = Optional.ofNullable(pizzaSize.getPizzaSizeId());
		Optional<PizzaSizeEntity> sizeOpt = 
				Optional.ofNullable(pizzaSizeRepository.findByNameAndPizzaId(pizzaSize.getName(), pizzaSize.getPizzaId()));
		if(sizeOpt.isPresent() && !sizeId.isPresent()) {
			Error error = new Error("Size already exists");
			throw new ExistingSizeException(error);
		}
		PizzaSizeEntity sizeEntity = tempConverter.pizzaSizeDtoToEntity(pizzaSize);
		PizzaSizeEntity storedSize = pizzaSizeRepository.save(sizeEntity);
		PizzaSizeDto returnValue = tempConverter.pizzaSizeEntityToDto(storedSize);
		return returnValue;
	}

	@Override
	public PizzaSizeDto getPizzaSizeById(Integer pizzaSizeId) {
		// TODO Auto-generated method stub
		PizzaSizeDto returnValue = null;
		Optional<PizzaSizeEntity> sizeEntity = Optional.ofNullable(pizzaSizeRepository.getById(pizzaSizeId));
		if (sizeEntity.isPresent()) {
			returnValue = tempConverter.pizzaSizeEntityToDto(sizeEntity.get());
		}
		return returnValue;
	}

	@Override
	public void deletePizzaSize(Integer pizzaSizeId) {
		// TODO Auto-generated method stub
		pizzaSizeRepository.deleteById(pizzaSizeId);
		pizzaSizeRepository.flush();
	}

	@Override
	public List<PizzaSizeDto> listAll() {
		// TODO Auto-generated method stub
		List<PizzaSizeDto> returnValue = new ArrayList<PizzaSizeDto>();
		Optional<List<PizzaSizeEntity>> allSizes = Optional.ofNullable(pizzaSizeRepository.findAll());
		if (!allSizes.isEmpty()) {
			for (PizzaSizeEntity size : allSizes.get()) {
				PizzaSizeDto sizeDto = tempConverter.pizzaSizeEntityToDto(size);
				returnValue.add(sizeDto);
			}
		}
		return returnValue;
	}

	@Override
	public List<PizzaSizeDto> listAllByPizzaId(Integer pizzaId) {
		// TODO Auto-generated method stub
		List<PizzaSizeDto> returnValue = new ArrayList<PizzaSizeDto>();
		Optional<List<PizzaSizeEntity>> allSizes = Optional.ofNullable(pizzaSizeRepository.findAllByPizzaId(pizzaId));
		if (!allSizes.isEmpty()) {
			for (PizzaSizeEntity size : allSizes.get()) {
				PizzaSizeDto sizeDto = tempConverter.pizzaSizeEntityToDto(size);
				returnValue.add(sizeDto);
			}
		}
		return returnValue;
	}

	@Override
	public void deleteAllByPizzaId(Integer pizzaId) {
		// TODO Auto-generated method stub
		pizzaSizeRepository.deleteAllByPizzaId(pizzaId);
		pizzaSizeRepository.flush();
	}

}
