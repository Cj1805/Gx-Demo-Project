package com.wellness.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.wellness.entities.Category;
import com.wellness.entities.Product;
import com.wellness.exception.ProductException;

@SpringBootTest
public class TestProductService {


	@Autowired
	private ProductService productService;

	@Test
	public void testGetCategoryList() {

		List<Category> actualCategoryList = productService.getCategoryList();

		List<Category> expectedCategoryList = new ArrayList<>();
		expectedCategoryList.add(new Category(1, "General"));
		expectedCategoryList.add(new Category(3, "General Health"));
		expectedCategoryList.add(new Category(4, "AquaCulture"));
		expectedCategoryList.add(new Category(5, "Aqua"));

		assertEquals(expectedCategoryList, actualCategoryList);

	}


	@Test
	public void testFindById() {
		Integer id = 7;
		
		Product actualProduct = productService.findById(id).get();
		
		Product expectedProduct = new Product();
		expectedProduct.setProductId(id);
		
		assertEquals(expectedProduct.getProductId(), actualProduct.getProductId());
		
	}
	
	@Test
	void testFindByIdError() {
		Integer id =1;
		assertThrows(ProductException.class, ()-> productService.findById(id));
	}

}
