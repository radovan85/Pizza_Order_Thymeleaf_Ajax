package com.radovan.spring.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.radovan.spring.converter.TempConverter;
import com.radovan.spring.dto.ShippingAddressDto;
import com.radovan.spring.entity.ShippingAddressEntity;
import com.radovan.spring.repository.ShippingAddressRepository;
import com.radovan.spring.service.ShippingAddressService;

@Service
@Transactional
public class ShippingAddressServiceImpl implements ShippingAddressService {

	@Autowired
	private ShippingAddressRepository addressRepository;

	@Autowired
	private TempConverter tempConverter;

	@Override
	public ShippingAddressDto addShippingAddress(ShippingAddressDto shippingAddress) {
		// TODO Auto-generated method stub
		ShippingAddressEntity addressEntity = tempConverter.shippingAddressDtoToEntity(shippingAddress);
		ShippingAddressEntity storedAddress = addressRepository.save(addressEntity);
		ShippingAddressDto returnValue = tempConverter.shippingAddressEntityToDto(storedAddress);
		return returnValue;
	}

	@Override
	public ShippingAddressDto updateShippingAddress(Integer id, ShippingAddressDto shippingAddress) {
		// TODO Auto-generated method stub
		ShippingAddressEntity tempAddress = addressRepository.getById(id);
		ShippingAddressEntity addressEntity = tempConverter.shippingAddressDtoToEntity(shippingAddress);
		addressEntity.setShippingAddressId(id);
		addressEntity.setCustomer(tempAddress.getCustomer());
		ShippingAddressEntity updatedAddress = addressRepository.saveAndFlush(addressEntity);
		ShippingAddressDto returnValue = tempConverter.shippingAddressEntityToDto(updatedAddress);
		return returnValue;
	}

	@Override
	public ShippingAddressDto getShippingAddress(Integer addressId) {
		// TODO Auto-generated method stub
		Optional<ShippingAddressEntity> addressEntity = Optional.ofNullable(addressRepository.getById(addressId));
		ShippingAddressDto returnValue = null;

		if (addressEntity.isPresent()) {
			returnValue = tempConverter.shippingAddressEntityToDto(addressEntity.get());
		}
		return returnValue;
	}

	@Override
	public void deleteShippingAddress(Integer addressId) {
		// TODO Auto-generated method stub
		addressRepository.deleteById(addressId);
		addressRepository.flush();
	}

}