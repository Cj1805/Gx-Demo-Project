package com.wellness.service;

import java.util.List;

import com.wellness.dto.AddressDTO;
import com.wellness.entities.Address;

public interface AddressService {

	Address addAddress(int userId, AddressDTO addressDto);

	List<Address> getAddressForUser(int userId);

	String removeAddress(int userId, int addressID);
	
	Address updateAddress(int addressId, AddressDTO addressDTO);

}
