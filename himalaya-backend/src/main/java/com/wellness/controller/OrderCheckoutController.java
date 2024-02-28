package com.wellness.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wellness.entities.Orders;
import com.wellness.entities.ShippingAddress;
import com.wellness.service.OrderCheckoutService;
import com.wellness.service.ShippingAddressService;

@RestController
@RequestMapping("/checkout")
@CrossOrigin(origins = "*")
public class OrderCheckoutController {

	@Autowired
	private ShippingAddressService shippingAddressService;
	
	@Autowired
	private OrderCheckoutService checkoutService;
	
	@PostMapping("/shipAddress/{id}")
	public ResponseEntity<ShippingAddress> saveAddress(@PathVariable int id, @RequestBody ShippingAddress shipAddress){
		ShippingAddress saveAddress = shippingAddressService.addAddress(id, shipAddress);
		return new ResponseEntity<>(saveAddress, HttpStatus.CREATED);
	}
	
	@PostMapping("/saveOrder/{userId}/{shipAddressId}/{paymentStatus}")
	public ResponseEntity<?> saveOrder(@PathVariable int userId, @PathVariable int shipAddressId, @PathVariable String paymentStatus){
		Orders order = checkoutService.saveOrder(userId, shipAddressId, paymentStatus);
		return new ResponseEntity<>(order, HttpStatus.CREATED);
	}
	
	@GetMapping("/getOrders/{userId}")
	public ResponseEntity<List<Orders>> getOrders(@PathVariable int userId){
		List<Orders> orders = checkoutService.getOrders(userId);
		return new ResponseEntity<>(orders, HttpStatus.CREATED);
	}
	
}
