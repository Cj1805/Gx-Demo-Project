package com.wellness.service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wellness.entities.Cart;
import com.wellness.entities.CartItem;
import com.wellness.entities.Product;
import com.wellness.exception.CartException;
import com.wellness.repository.CartItemRepository;
import com.wellness.repository.CartRepository;
import com.wellness.repository.ProductRepository;
import com.wellness.serviceImpl.CartItemServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CartServiceImplTest {

	
  
	@Mock
    private CartItemRepository cartItemRepository;
	
	@Mock
    private ProductRepository productRepository;
	
	@Mock
    private CartRepository cartRepository;
    
	@InjectMocks
    private CartItemServiceImpl cartItemService;


//	@Test
//	public void testAddToCart() {
//	    CartItem cartItem = new CartItem();
//	    cartItem.setId(1);
//	    cartItem.setQuantity(2);
//
//	    Optional<Cart> cart = Optional.of(new Cart());
//	    cart.get().setCartId(1);
//
//	    Optional<Product> product = Optional.of(new Product());
//	    product.get().setProductId(1);
//	    product.get().setStock(5);
//
//	    when(cartRepository.findById(anyInt())).thenReturn(cart);
//	    when(productRepository.findById(anyInt())).thenReturn(product);
//	    when(cartItemRepository.findByCartCartIdAndProductProductId(anyInt(), anyInt())).thenReturn(null);
//	    when(cartItemRepository.save(any(CartItem.class))).thenReturn(cartItem);
//
//	    CartItem result = cartItemService.addToCart(1, 1, 2);
//	    assertEquals(cartItem.getId(), result.getId());
//	    assertEquals(cartItem.getQuantity(), result.getQuantity());
//	}

	@BeforeAll
    public static void beforeClass(TestInfo testInfo)  {
    	System.out.println(testInfo.getDisplayName() + " -Junit Test is started.\n");
    }
    
    @BeforeEach
    public void beforeTestMethods(TestInfo testInfo) {
    	System.out.println("Method "+ testInfo.getDisplayName()+ " is started.");
    }
    
    @AfterEach
    public void afterTestMethods(TestInfo testInfo){
    	System.out.println("Method "+ testInfo.getDisplayName()+ " is ended.");
    }

    @AfterAll
    public static void afterClass() {
    	System.out.println("\nTest is end.");
    }

    @Test
    public void testAddToCartInvalidCart() {
        when(cartRepository.findById(anyInt())).thenReturn(java.util.Optional.empty());
        
        assertThrows(CartException.class, ()-> cartItemService.addToCart(1, 1, 2));
    }

    @Test
    public void testAddToCartInvalidProduct() {
        Optional<Cart> cart = Optional.of(new Cart());
        cart.get().setCartId(1);
        when(cartRepository.findById(anyInt())).thenReturn(cart);
        when(productRepository.findById(anyInt())).thenReturn(java.util.Optional.empty());
        
        
        assertThrows(CartException.class, ()-> cartItemService.addToCart(1, 1, 2));
    }

    @Test
    public void testAddToCartExceedsStock() {
        Cart cart = new Cart();
        cart.setCartId(1);
        Product product = new Product();
        product.setProductId(1);
        product.setStock(1);
        when(cartRepository.findById(anyInt())).thenReturn(Optional.of(cart));
        when(productRepository.findById(anyInt())).thenReturn(Optional.of(product));
        
        assertThrows(CartException.class,()-> cartItemService.addToCart(1, 1, 2));
        
    }
    
    
    @Test
    public void testClearCart() {
    	when(cartItemRepository.deleteByCartId(anyInt())).thenReturn(0);
    	assertDoesNotThrow(()-> cartItemService.clearCart(1));
    }
    
}
