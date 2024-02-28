//package com.wellness.service;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.Mockito.when;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import com.wellness.entities.Category;
//import com.wellness.repository.CategoryRepository;
//import com.wellness.serviceImpl.ProductServiceImpl;
//
//@ExtendWith(MockitoExtension.class)
//public class ProductServiceTest {
//	
//	@Mock
//	private CategoryRepository repository;
//	
//	@InjectMocks
//	private ProductServiceImpl productServiceImpl;	
//	
//	@BeforeEach
//	public void getList() {
//		List<Category> expectedCategoryList = new ArrayList<>();
//
//		expectedCategoryList.add(new Category(1, "General"));
//		expectedCategoryList.add(new Category(3, "General Health"));
//		expectedCategoryList.add(new Category(4, "AquaCulture"));
//		expectedCategoryList.add(new Category(5, "Aqua"));
//		when(repository.findAll()).thenReturn(expectedCategoryList);
//	}
//	
//	@Test
//	public void getCategoryList() {
//		List<Category> catList = productServiceImpl.getCategoryList();
//		assertThat(catList).isEqualTo(catList);
//	}
//
//}
package Backup;


