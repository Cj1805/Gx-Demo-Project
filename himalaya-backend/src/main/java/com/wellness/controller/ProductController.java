package com.wellness.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wellness.dto.ProductDTO;
import com.wellness.dto.ReviewsDTO;
import com.wellness.entities.Category;
import com.wellness.entities.Product;
import com.wellness.exception.ReviewException;
import com.wellness.service.ProductService;
import com.wellness.service.ReviewService;

@RestController
@RequestMapping("products")
@CrossOrigin(origins = "*")
public class ProductController {

	@Autowired
	ProductService productService;
	
	@Autowired
	private ReviewService reviewService;

	List<Product> product;

	/**
	 * This API returns all the Products present
	 * 
	 * @return List of Products in database
	 */

	@GetMapping("/GetAllProducts")
	public List<Product> getAllProducts() {
		return productService.getAllProducts();
	}

	/**
	 * This API is implement to search product
	 * 
	 * @param name
	 * @return List of Products matching with input
	 */

	@GetMapping("/getByName/{name}")
//	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<Product> findProductByNameContains(@PathVariable String name) {

		return productService.findProductByNameContains(name);
	}

	/**
	 * This API is implement to save Product in database
	 * 
	 * @param productDto
	 * @return Product Details saved in Database
	 */

	
	
	@PostMapping("/saveProduct")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Product> saveProduct(@RequestBody ProductDTO productDto) {
		Product product = productService.saveProduct(productDto);
		return new ResponseEntity<>(product, HttpStatus.CREATED);

	}

	/**
	 * This API is implement to Fetch Product based on ID
	 * 
	 * @param id
	 * @return Product Matching with Id
	 */

	@GetMapping("/getById/{id}")
	public Optional<Product> getProductById(@PathVariable Integer id) {
		return productService.findById(id);
	}

	/**
	 * This API helps in getting list of categories
	 * 
	 * @return List of categories present 
	 */
	
	@GetMapping("/category")
	public ResponseEntity<List<Category>> getCategoryList(){
		List<Category> categoryList = productService.getCategoryList();
		return new ResponseEntity<>(categoryList, HttpStatus.OK);
	}

//	@GetMapping("/getByCategory/{name}")
//	public ResponseEntity<List<Product>> getByCategory(@PathVariable String name) {
//		List<Product> filteredProducts = productService.getProductByCategoryName(name);
//		return new ResponseEntity<>(filteredProducts, HttpStatus.OK);
//	}
	
	/**
	 * This API helps in sorting and filtering the products.
	 * 
	 * @param category
	 * @param minPrice
	 * @param maxPrice
	 * @param stock
	 * @param sortBy
	 * @return List of Product based on given parameters
	 */
	
	@GetMapping("/filterSort")
	public ResponseEntity<List<Product>> filterProducts(@RequestParam(required = false) String category, 
			@RequestParam(required = false) Double minPrice,
			@RequestParam(required = false) Double maxPrice,
			@RequestParam(required = false , defaultValue = "false") Boolean stock,
			@RequestParam(required = false) String sortBy){
		List<Product> filterdProducts = productService.filterProducts(category, minPrice, maxPrice, stock, sortBy);
		return new ResponseEntity<>(filterdProducts, HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param productId
	 * @return
	 */
	
	@GetMapping("/getReview/{productId}")
	public ResponseEntity<?> getReviewByProduct(@PathVariable int productId){
		try {
			List<ReviewsDTO> reviews = reviewService.getReviews(productId);
			return new ResponseEntity<>(reviews, HttpStatus.OK);
		}catch(ReviewException ex) {
			return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
		}
	}

}
