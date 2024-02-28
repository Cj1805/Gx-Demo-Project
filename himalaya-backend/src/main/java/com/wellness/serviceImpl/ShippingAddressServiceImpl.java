package com.wellness.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wellness.entities.ShippingAddress;
import com.wellness.entities.UserInfo;
import com.wellness.exception.AddressException;
import com.wellness.repository.ShippingAddressRepository;
import com.wellness.repository.UserDetailsRepository;
import com.wellness.service.ShippingAddressService;


@Service
public class ShippingAddressServiceImpl implements ShippingAddressService {
	
	@Autowired
	UserDetailsRepository userDetailsRepository;
	
	@Autowired
	ShippingAddressRepository shippingAddressRepository;

	@Override
	public ShippingAddress addAddress(int userId, ShippingAddress shippingAddress) {
		Optional<UserInfo> user = userDetailsRepository.findById(userId);
		if(user.isPresent()) {
			UserInfo users = user.get();
			shippingAddress.setUserDetails(users);
			shippingAddressRepository.save(shippingAddress);
			return shippingAddress;
		}
		throw new AddressException("User with Id not present.");
	}

}
