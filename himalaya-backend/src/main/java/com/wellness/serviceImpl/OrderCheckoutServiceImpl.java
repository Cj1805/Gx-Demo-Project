package com.wellness.serviceImpl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wellness.entities.Cart;
import com.wellness.entities.CartItem;
import com.wellness.entities.OrderItems;
import com.wellness.entities.Orders;
import com.wellness.entities.ShippingAddress;
import com.wellness.entities.UserInfo;
import com.wellness.exception.OrderException;
import com.wellness.repository.OrdersRepository;
import com.wellness.repository.ShippingAddressRepository;
import com.wellness.repository.UserDetailsRepository;
import com.wellness.service.OrderCheckoutService;
import com.wellness.utils.ExceptionUtils;


@Service
public class OrderCheckoutServiceImpl implements OrderCheckoutService {
	
	@Autowired
	private UserDetailsRepository userDetailsRepository;

	@Autowired
	private OrdersRepository ordersRepository;
	
	@Autowired
	private ShippingAddressRepository shippingAddressRepository;
	
	
	@Override
	public Orders saveOrder(int userId, int shipAddressId , String paymentStatus) {
		UserInfo user = userDetailsRepository.findById(userId).get();
		Orders orders = new Orders();
		if(user != null) {
			Cart cart = user.getCart();
			if(cart != null && !cart.getCartItems().isEmpty()) {
				ShippingAddress shipAddress = shippingAddressRepository.findById(shipAddressId).orElse(null);
				orders.setUserDetails(user);
				orders.setShipAddress(shipAddress);
				orders.setOrderDate(LocalDate.now());
				double totalPrice = cart.getCartItems().stream()
						.mapToDouble(cartitem-> cartitem.getProduct().getPrice() * cartitem.getQuantity())
						.sum();
				if(paymentStatus.equalsIgnoreCase("no")) {
					orders.setTotalPrice(totalPrice+50);
				}else {
					orders.setTotalPrice(totalPrice);
				}
				orders.setPaymentStatus(paymentStatus);
				
				for (CartItem cartItem : cart.getCartItems()) {
					OrderItems orderItems = new OrderItems();
					orderItems.setProduct(cartItem.getProduct());
					orderItems.setQuantity(cartItem.getQuantity());
					orderItems.setOrder(orders);
					orders.getOrderItem().add(orderItems);
				}
				ordersRepository.save(orders);
				
				return orders;
			}
		}
		throw new OrderException(ExceptionUtils.CART_EMPTY);
	}
	

	@Override
	public List<Orders> getOrders(int userId) {
		List<Orders> orders = ordersRepository.findByUserDetailsId(userId);
		if(orders.isEmpty()) {
			throw new OrderException(ExceptionUtils.ORDER_NOTFOUND);
		}else {
			return orders;
		}

	}

}
