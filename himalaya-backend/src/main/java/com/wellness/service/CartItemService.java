package com.wellness.service;

import java.util.List;

import com.wellness.entities.CartItem;

public interface CartItemService {

	CartItem addToCart(int cartId, int productId, int quantity);
	
	List<CartItem> getCartItem(int cartId);

	void removeFromCart(int cartItemId);
	
	void clearCart(int cartId);
}
