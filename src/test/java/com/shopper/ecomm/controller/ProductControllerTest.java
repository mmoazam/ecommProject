package com.shopper.ecomm.controller;

import com.shopper.ecomm.model.Category;
import com.shopper.ecomm.payload.ProductDTO;
import com.shopper.ecomm.payload.ProductResponse;
import com.shopper.ecomm.service.ProductService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockingDetails;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ProductService productService;
    ProductDTO productDTO1;
    ProductDTO productDTO2;
    List<ProductDTO> productDTOs = new ArrayList<>();
    List<ProductDTO> emptyProductDto = new ArrayList<>();
    Category category = new Category(1L, "Test Category", null);


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productDTO1 = new ProductDTO(null, "Test Product", "TestImage.png", "test description", 10, 10.0, 10.0, 90.0);
        productDTO2 = new ProductDTO(null, "Test Product2", "TestImage2.png", "test description2", 20, 20.0, 20.0, 60.0);
        productDTOs.add(productDTO1);
        productDTOs.add(productDTO2);
    }



    @AfterEach
    void tearDown() {
    }

    @Test
    void addProduct() {
    }

    @Test
    void TestGetProducts() throws Exception {
        // Prepare mock data
        ProductResponse mockProductResponse = new ProductResponse(); // Set up your mock response as needed
        mockProductResponse.setContent(productDTOs);

        // Configure the mock service
        when(productService.getAllProducts(anyInt(), anyInt(), anyString(), anyString()))
                .thenReturn(mockProductResponse);

        // Perform the GET request
        mockMvc.perform(get("/api/v1/public/products")
                        .param("pageNumber", "1")
                        .param("pageSize", "10")
                        .param("sortBy", "productId")
                        .param("sortOrder", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].productName").value("Test Product"));
    }

    @Test
    void TestGetProducts_NoProducts() throws Exception {
        // Prepare mock data
        ProductResponse mockProductResponse = new ProductResponse(); // Set up your mock response as needed
        mockProductResponse.setContent(emptyProductDto);

        // Configure the mock service
        when(productService.getAllProducts(anyInt(), anyInt(), anyString(), anyString()))
                .thenReturn(mockProductResponse);

        // Perform the GET request
        mockMvc.perform(get("/api/v1/public/products")
                        .param("pageNumber", "1")
                        .param("pageSize", "10")
                        .param("sortBy", "productId")
                        .param("sortOrder", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isEmpty());
    }


    @Test
    void getProductsByCategoryId() {
    }

    @Test
    void getProductsByKeyWord() {
    }

    @Test
    void updateProduct() {
    }

    @Test
    void deleteProduct() {
    }

    @Test
    void updateProductImage() {
    }
}