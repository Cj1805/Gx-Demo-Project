package com.wellness.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wellness.entities.Cart;
import com.wellness.entities.CartItem;
import com.wellness.entities.Product;
import com.wellness.exception.CartException;
import com.wellness.exception.ProductException;
import com.wellness.repository.CartItemRepository;
import com.wellness.repository.CartRepository;
import com.wellness.repository.ProductRepository;
import com.wellness.service.CartItemService;
import com.wellness.utils.ExceptionUtils;

@Service
public class CartItemServiceImpl implements CartItemService {

	@Autowired
	private CartItemRepository cartItemRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CartRepository cartRepository;
	
	/**
	 * This method modify the Stocks of Products 
	 * @param productId
	 * @param quantity
	 */

	public void modifyStock(int productId, int quantity) {
		Product product = productRepository.findById(productId).orElse(null);

		if (product == null || product.getStock() < quantity) {
			throw new ProductException(ExceptionUtils.PRODUCT_STOCK);
		}
		int modifiedStock = product.getStock() - quantity;
		productRepository.modifyStock(productId, modifiedStock);
	}

	
	/**
	 * This method add Product to Cart 
	 *
	 */
	
	public CartItem addToCart(int cartId, int productId, int quantity) {
		Cart cart = cartRepository.findById(cartId).orElse(null);
		Product product = productRepository.findById(productId).orElse(null);

		if (cart == null || product == null) {
			throw new CartException(ExceptionUtils.PRODUCT_STOCK);
		}
		
		CartItem cartItem = cartItemRepository.findByCartCartIdAndProductProductId(cartId, productId);
		
		if (cartItem == null) {
			if (quantity > 0 && quantity <= product.getStock() ) {
				cartItem = new CartItem();
				cartItem.setCart(cart);
				cartItem.setProduct(product);
				cartItem.setQuantity(quantity);
				modifyStock(productId, quantity);
				return cartItemRepository.save(cartItem);
			} else {
				throw new CartException(ExceptionUtils.PRODUCT_QUANTITY);
			}
		} else {
			if(cartItem.getQuantity() <= 5) {
				int newQuantity = cartItem.getQuantity() + quantity;
				if(newQuantity>5) {
					throw new ProductException(ExceptionUtils.PRODUCT_MAX);
				}else {
					return updateCartItem(cartItem.getId(), newQuantity);
				}
			}else {
				throw new ProductException(ExceptionUtils.PRODUCT_MAX);
			}
			
		}
	}
	
	/**
	 * This method updates the quantity of Products in Cart
	 * @param cartItemId
	 * @param newQuantity
	 * @return Updated cartItem 
	 */

	public CartItem updateCartItem(int cartItemId, int newQuantity) {
		CartItem cartItem = cartItemRepository.findById(cartItemId).orElse(null);

		if (cartItem == null) {
			throw new CartException(ExceptionUtils.CARTITEM_NOTFOUND);
		}

		Product product = cartItem.getProduct();
		int currentQuantity = cartItem.getQuantity();
		int productId = product.getProductId();
		int toBeAdded = newQuantity - currentQuantity;

		if (toBeAdded > product.getStock()) {
			throw new ProductException(ExceptionUtils.PRODUCT_QUANTITY);
		}

		cartItem.setQuantity(newQuantity);
		modifyStock(productId, toBeAdded);
		return cartItemRepository.save(cartItem);

	}

	
	/**
	 * This method remove the Cart Item from cart based on ID Given
	 */
	
	public void removeFromCart(int cartItemId) {
		CartItem cartItem = cartItemRepository.findById(cartItemId).orElse(null);

		if (cartItem != null) {
			Product product = cartItem.getProduct();
			int quantity = cartItem.getQuantity();
			int productId = product.getProductId();
			int productStock = product.getStock() + quantity;
			
			productRepository.modifyStock(productId, productStock);
			cartItemRepository.deleteById(cartItemId);
		}
	}
	
	
	/**
	 * This Method return the List of CartItems.
	 * @param Cart ID
	 */
	
	public List<CartItem> getCartItem(int cartId) {
		
		if(cartItemRepository.findAllByCartCartId(cartId).isEmpty()) {
			throw new CartException("No Items in Cart");
		}else {
			return cartItemRepository.findAllByCartCartId(cartId);
		}
	}

	
	/**
	 * This method Clear the CartItems.
	 * 
	 * @param CartId
	 */

	@Override
	public void clearCart(int cartId) {
		cartItemRepository.deleteByCartId(cartId);
	}

}
