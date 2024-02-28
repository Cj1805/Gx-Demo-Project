package com.wellness.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.wellness.entities.Category;
import com.wellness.entities.Product;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;


@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{
	List<Product> findByProductNameContainingAllIgnoringCase(String name);
	Optional<Product> findById(Integer id);
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE product SET stock = :modifiedStock WHERE product_id = :productId" , nativeQuery = true)
	void modifyStock( int productId, int modifiedStock);
	
	List<Product> findAllByCategory(Category category);	
}
