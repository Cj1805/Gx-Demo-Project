package com.wellness.mapper;


import org.springframework.stereotype.Component;

import com.wellness.dto.ProductDTO;
import com.wellness.entities.Product;

@Component
public class ProductMapper {
	
	
	public ProductDTO convertToDto(Product product) {
		ProductDTO productDto = new ProductDTO();
		productDto.setProductId(product.getProductId());
		productDto.setProductName(product.getProductName());
		productDto.setDescription(product.getDescription());
		productDto.setPrice(product.getPrice());
		productDto.setImageURL(product.getImageURL());
		productDto.setCategory(product.getCategory());;
		
		return productDto;
		
	}
	
	public Product convertToEntity(ProductDTO productDto) {
		Product product = new Product();
		product.setProductId(productDto.getProductId());
		product.setProductName(productDto.getProductName());
		product.setDescription(productDto.getDescription());
		product.setPrice(productDto.getPrice());
		product.setImageURL(productDto.getImageURL());
		product.setCategory(productDto.getCategory());
		return product;
		
	}
}
