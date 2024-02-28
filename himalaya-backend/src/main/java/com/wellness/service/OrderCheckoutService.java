package com.wellness.service;

import java.util.List;

import com.wellness.entities.Orders;

public interface OrderCheckoutService {
	
	Orders saveOrder(int userId, int shipAddressId, String paymentStatus);
	
	List<Orders> getOrders(int userId);

}
