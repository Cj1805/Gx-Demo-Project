package com.wellness.service;

import com.wellness.entities.ShippingAddress;

public interface ShippingAddressService {
	
	ShippingAddress addAddress(int userId, ShippingAddress shippingAddress);
}
