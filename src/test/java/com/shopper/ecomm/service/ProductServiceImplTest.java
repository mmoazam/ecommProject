package com.shopper.ecomm.service;


import com.shopper.ecomm.exceptions.ApiException;
import com.shopper.ecomm.model.Product;
import com.shopper.ecomm.payload.ProductDTO;
import com.shopper.ecomm.payload.ProductResponse;
import com.shopper.ecomm.repositories.CategoryRepository;
import com.shopper.ecomm.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productService = new ProductServiceImpl(productRepository, categoryRepository, modelMapper, null);
    }

    @Test
    public void testGetAllProducts_success() {
        // Arrange

        Product product1 = new Product(1L, "Product1", "product1.jpg", "product1 desc", 2, 10.0, 5.0, 9.5, null, null);
        Product product2 = new Product(2L, "Product2", "product2.jpg", "product2 desc", 2, 10.0, 5.0, 9.5, null, null);
        Product p3 = new Product();
        List<Product> products = List.of(product1, product2, p3);
        Page<Product> productPage = new PageImpl<>(products, PageRequest.of(0, 10), 3);

        when(productRepository.findAll(any(Pageable.class))).thenReturn(productPage);
        when(modelMapper.map(ProductDTO.class, Product.class))
                .thenAnswer(invocation -> modelMapper.map(invocation.getArgument(0), invocation.getArgument(1)));

        // Act
        ProductResponse response = productService.getAllProducts(0, 10, "productId", "ASC");

        // Assert
        assertEquals(3, response.getContent().size());
        assertEquals(0, response.getPageNumber());
        assertEquals(10, response.getPageSize());
        assertEquals(1, response.getTotalPages());
        assertTrue(response.isLastPage());

    }


    @Test
    public void testGetAllProducts_ThrowsException_ifNoProducts() {
        List<Product> products = new ArrayList<>();
        Page<Product> productPage = new PageImpl<>(products, PageRequest.of(0, 10), 3);

        when(productRepository.findAll(any(Pageable.class))).thenReturn(productPage);

        assertThatThrownBy(() -> productService.getAllProducts(0, 10, "productId", "ASC"))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining("No products found");
    }
}