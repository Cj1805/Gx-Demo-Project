package com.wellness.mapper;

import org.springframework.stereotype.Component;

import com.wellness.dto.AddressDTO;
import com.wellness.entities.Address;

@Component
public class AddressMapper {

	public AddressDTO toAddressDto(Address address) {
		AddressDTO addressDTO = new AddressDTO();
		addressDTO.setFirstName(address.getFirstName());
		addressDTO.setLastName(address.getLastName());
		addressDTO.setAddressLine1(address.getAddressLine1());
		addressDTO.setAddressLine2(address.getAddressLine2());
		addressDTO.setCity(address.getCity());
		addressDTO.setState(address.getState());
		addressDTO.setPinCode(address.getPinCode());
		addressDTO.setPhone(address.getPhone());
		
		return addressDTO;
	}
	
	public Address toAddressEntity(AddressDTO addressDTO) {
		Address address = new Address();
		address.setFirstName(addressDTO.getFirstName());
		address.setLastName(addressDTO.getLastName());
		address.setAddressLine1(addressDTO.getAddressLine1());
		address.setAddressLine2(addressDTO.getAddressLine2());
		address.setCity(addressDTO.getCity());
		address.setState(addressDTO.getState());
		address.setPinCode(addressDTO.getPinCode());
		address.setPhone(addressDTO.getPhone());
		
		return address;
	}
}
