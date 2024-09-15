package com.shopper.ecomm.controller;

import com.shopper.ecomm.model.Category;
import com.shopper.ecomm.model.Product;
import com.shopper.ecomm.payload.ProductDTO;
import com.shopper.ecomm.payload.ProductResponse;
import com.shopper.ecomm.service.ProductService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
    Category category = new Category(1L, "Test Category", null);

    private Long productId;
    private String productName;
    private String image;
    private String description;
    private Integer quantity;
    private double price;
    private double discount;
    private double specialPrice;

    @BeforeEach
    void setUp() {
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
        when(productService.getAllProducts( 0, 5, "productId", "asc"))
                .thenReturn(new ProductResponse(productDTOs, 1, 10, 1, 1, true));

        this.mockMvc.perform(get("/api/v1/public/products")).andDo(print()).andExpect(status().isOk());
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