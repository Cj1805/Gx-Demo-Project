package com.wellness.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.wellness.entities.CartItem;

import jakarta.transaction.Transactional;


@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
	
	CartItem findByCartCartIdAndProductProductId(int cartId, int productId);
	
	void deleteById(int cartItemId);
	
	List<CartItem> findAllByCartCartId(int cartId); 
	
	
	@Modifying
	@Transactional
	@Query(value = "DELETE from cart_item where cart_id = :cartId", nativeQuery = true)
	int deleteByCartId(int cartId);
}
