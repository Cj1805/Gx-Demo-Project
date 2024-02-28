package com.wellness.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.wellness.entities.Category;
import com.wellness.entities.Product;
import com.wellness.exception.CartException;
import com.wellness.exception.ProductException;
import com.wellness.repository.CategoryRepository;
import com.wellness.repository.ProductRepository;
import com.wellness.serviceImpl.DemoClass;
import com.wellness.serviceImpl.ProductServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;
    
    @Mock
    private DemoClass democlass;

    @InjectMocks
    private ProductServiceImpl productService;

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
    
    
    /**
     *Test the method to fetch all products
     */
    
    @Disabled
    @Test
    void testGetAllProducts() {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product());
        productList.add(new Product());
        productList.add(new Product());
        productList.add(new Product());

        when(productRepository.findAll()).thenReturn(productList);

        List<Product> result = productService.getAllProducts();
        assertEquals(4, result.size());
    }
    
    
    /**
     *Test the method to fetch all products that have name given
     */

 
    @Test
    void testFindProductByNameContains() {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product(1, "Himalaya", null, 0, null, 0, null, null));
        productList.add(new Product(2, "Himalaya", null, 0, null, 0, null, null));
        productList.add(new Product(3, "Wellness", null, 0, null, 0, null, null));

        when(productRepository.findByProductNameContainingAllIgnoringCase(anyString())).thenReturn(productList
        		.stream()
        		.filter(product->product.getProductName().equalsIgnoreCase("Himalaya"))
        		.toList());

        List<Product> result = productService.findProductByNameContains("Himalaya");
        assertEquals(2, result.size());
        verify(productRepository, times(1)).findByProductNameContainingAllIgnoringCase(anyString());
    }
    

    /**
     *Test the method to fetch all products that have name given
     * but not found any specific product.
     */
    
   
    @Test
    void testFindProductByNameContains_NoProductFound() {
    	 List<Product> productList = new ArrayList<>();
         productList.add(new Product(1, "Himalaya", null, 0, null, 0, null, null));
         productList.add(new Product(2, "Himalaya", null, 0, null, 0, null, null));
         productList.add(new Product(3, "Wellness", null, 0, null, 0, null, null));
        when(productRepository.findByProductNameContainingAllIgnoringCase(anyString())).thenReturn(productList
        		.stream()
        		.filter(product->product.getProductName().equalsIgnoreCase("Detol"))
        		.toList());

        assertThrows(Exception.class, () -> {
            productService.findProductByNameContains("Detol");
        });
    }
    
    
    /**
     *Test the method to fetch product based on particular ID.
     */

    @Disabled
    @Test
    void testFindById() {
        Product product = new Product();
        product.setProductId(1);

        when(productRepository.findById(anyInt())).thenReturn(Optional.of(product));

        Optional<Product> result = productService.findById(1);

        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(1, result.get().getProductId());
    }
    
    
    /**
     *Test the method to fetch product based on particular ID,
     *But not found.
     */


    @Disabled
    @Test
    void testFindById_ProductNotFound() {
        when(productRepository.findById(anyInt())).thenReturn(Optional.empty());
        
        assertThrows(ProductException.class,()-> productService.findById(2));
    }
    /**
     *Test the method to fetch category List.
     *     
     */
    
    @Disabled
    @Test
    void testGetCategoryList() {
 
    	List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category());
        categoryList.add(new Category());

        when(categoryRepository.findAll()).thenReturn(categoryList);
       
        List<Category> result = productService.getCategoryList();
        assertEquals(2, result.size());
        verify(categoryRepository, times(1)).findAll();
    }
    
    
    /**
     *	Test the method to fetch category List,
     *     but is empty.
     */

    @Disabled
    @Test
    void testGetCategoryList_NoCategoryFound() {
    	 List<Category> categoryList = new ArrayList<>();
        when(categoryRepository.findAll()).thenReturn(categoryList);
  
        assertThrows(ProductException.class, () -> {
            productService.getCategoryList();
        });
        
    }
    
}
