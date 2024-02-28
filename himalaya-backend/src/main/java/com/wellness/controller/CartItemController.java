package com.wellness.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wellness.entities.CartItem;
import com.wellness.serviceImpl.CartItemServiceImpl;
import com.wellness.utils.ExceptionUtils;

@RestController
@RequestMapping("/cart")
@CrossOrigin(origins = "*")
public class CartItemController {
	
	@Autowired
	private CartItemServiceImpl cartItemServiceImpl;
	
	/**
	 * This API implements to save Product in Cart
	 * @param cartId
	 * @param productId
	 * @param quantity
	 * @return Products Added
	 */
	
	@PostMapping("/add")
	public ResponseEntity<CartItem> addToCart(@RequestParam int cartId, @RequestParam int productId, @RequestParam int quantity){
		CartItem addItem = cartItemServiceImpl.addToCart(cartId, productId, quantity);
		return ResponseEntity.ok(addItem);
	}
	
	
	/**
	 * This API helps in View Cart details
	 * @param cartId
	 * @return CartItems Detail
	 */
	
	@GetMapping("/viewCart/{cartId}")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<List<CartItem>> viewCart(@PathVariable int cartId){
		List<CartItem> cartItems = cartItemServiceImpl.getCartItem(cartId);
		return ResponseEntity.ok(cartItems);
	}
	
	/**
	 * This API is to Update cartItem Details
	 * @param cartItemId
	 * @param quantity
	 * @return  CartItem Details
	 */
	
	@PutMapping("/{cartItemId}")
	public ResponseEntity<String> updateCart(@PathVariable int cartItemId, @RequestParam("quantity") int quantity){
		CartItem  updateCartQuant = cartItemServiceImpl.updateCartItem(cartItemId, quantity);
		return ResponseEntity.ok(ExceptionUtils.CARTITEM_QUANTITY + updateCartQuant.getQuantity());
	}
	
	
	/**
	 * This API removes the Product form Cart
	 * @param cartItemId
	 * @return String
	 */
	
	@DeleteMapping("/remove/{cartItemId}")
	public ResponseEntity<String> removeFromCart(@PathVariable int cartItemId){
		cartItemServiceImpl.removeFromCart(cartItemId);
		return ResponseEntity.ok(ExceptionUtils.CARTITEM_REMOVED);
	}
	
	@DeleteMapping("/clearCart/{cartId}")
	public ResponseEntity<String> clearCart(@PathVariable int cartId){
		cartItemServiceImpl.clearCart(cartId);
		return ResponseEntity.ok(ExceptionUtils.CART_CLEAR);
	}
}
