package com.wellness.service;

import java.util.List;
import java.util.Optional;

import com.wellness.dto.ProductDTO;
import com.wellness.entities.Category;
import com.wellness.entities.Product;
import com.wellness.exception.ProductException;

public interface ProductService {
	List<Product> getAllProducts();
	Product saveProduct(ProductDTO productDto);
	List<Product> findProductByNameContains(String name) throws ProductException;
	Optional<Product> findById(Integer id) throws ProductException;
//	List<Product> getProductByCategoryName(String name);
	List<Category> getCategoryList();
	List<Product> filterProducts(String category, Double minPrice, Double maxPrice, boolean stock, String sortBy);
}
