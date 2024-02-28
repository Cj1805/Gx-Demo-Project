package com.wellness.serviceImpl;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wellness.dto.ProductDTO;
import com.wellness.entities.Category;
import com.wellness.entities.Product;
import com.wellness.exception.ProductException;
import com.wellness.mapper.ProductMapper;
import com.wellness.repository.CategoryRepository;
import com.wellness.repository.ProductRepository;
import com.wellness.service.ProductService;
import com.wellness.utils.ExceptionUtils;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductRepository productRepository;

	@Autowired
	ProductMapper productMapper;

	@Autowired
	CategoryRepository categoryRepository;
	

	/**
	 * This method saves product details in Database
	 * 
	 * @param ProductDto
	 * @return Product Details
	 */

	@Override
	public Product saveProduct(ProductDTO productDto) {

		Product product = productMapper.convertToEntity(productDto);
		Category category = categoryRepository.findByCategoryName(productDto.getCategory().getCategoryName());
		if (category != null) {
			product.setCategory(category);
		} else {
			Category newCategory = new Category();
			newCategory.setCategoryName(productDto.getCategory().getCategoryName());
			Category saveCategory = categoryRepository.save(product.getCategory());
			product.setCategory(saveCategory);
		}
		return productRepository.save(product);
	}

	/**
	 * @return List Of Products
	 */

	@Override
	public List<Product> getAllProducts() {
		List<Product> product;
		if (!productRepository.findAll().isEmpty()) {
			product = productRepository.findAll();
			return product;
		} else {
			throw new ProductException(ExceptionUtils.PRODUCT_NOTFOUND);
		}
	}

	/**
	 * Method returns product matching with given name
	 */
	@Override
	public List<Product> findProductByNameContains(String name) {
		List<Product> products = productRepository.findByProductNameContainingAllIgnoringCase(name);
		if (products.isEmpty()) {
			throw new ProductException(ExceptionUtils.PRODUCT_NOTFOUND + "for " + name);
		} else {
			return products;
		}
	}

	/**
	 * This method return product based on ID
	 * 
	 * @param Id
	 */

	@Override
	public Optional<Product> findById(Integer id) {
		Optional<Product> product = productRepository.findById(id);
		if (!product.isPresent()) {
			throw new ProductException(ExceptionUtils.PRODUCT_NOTFOUND);
		} else {
			return product;
		}
	}

	/**
	 * @return List of category
	 */

	@Override
	public List<Category> getCategoryList() {
		List<Category> category = categoryRepository.findAll();
		if (category.isEmpty()) {
			throw new ProductException(ExceptionUtils.CATEGORY_NOTFOUND);
		} else {
			return category;
		}
	}


	
	
	/**
	 * @param category, minPrice, maxPrice,stock,sort(condition)
	 * 
	 * @return List of products based on parameter provided
	 */
	@Override
	public List<Product> filterProducts(String category, Double minPrice, Double maxPrice, boolean stock,
			String sortBy) {
		List<Product> products = productRepository.findAll();
		if (category != null && !category.isEmpty()) {
			Category categoryFilter = categoryRepository.findByCategoryName(category);
			if (categoryFilter != null) {
				products = products.stream().filter(product -> product.getCategory().equals(categoryFilter))
						.collect(Collectors.toList());
			} else {
				throw new ProductException(ExceptionUtils.PRODUCT_NOTFOUND);
			}
		}
		if (minPrice != null) {
			products = products.stream().filter(product -> Double.compare(product.getPrice(), minPrice) >= 0)
					.collect(Collectors.toList());
		}
		if (maxPrice != null) {
			products = products.stream().filter(product -> Double.compare(product.getPrice(), maxPrice) <= 0)
					.collect(Collectors.toList());
		}
		if (stock) {
			products = products.stream().filter(product -> Double.compare(product.getStock(), 0) > 0)
					.collect(Collectors.toList());
		}
		if (sortBy != null && !sortBy.isEmpty()) {

			switch (sortBy.toLowerCase()) {
			case "priceasc":
				return products.stream().sorted(Comparator.comparing(Product::getPrice)).collect(Collectors.toList());
			case "pricedesc":
				return products.stream().sorted(Comparator.comparing(Product::getPrice).reversed())
						.collect(Collectors.toList());
			case "nameasc":
				return products.stream().sorted(Comparator.comparing(Product::getProductName))
						.collect(Collectors.toList());
			case "namedesc":
				return products.stream().sorted(Comparator.comparing(Product::getProductName).reversed())
						.collect(Collectors.toList());
			default:
				return products;
			}
		}

		if (products.isEmpty()) {
			throw new ProductException(ExceptionUtils.PRODUCT_NOTFOUND);
		}
		return products;
	}

}
