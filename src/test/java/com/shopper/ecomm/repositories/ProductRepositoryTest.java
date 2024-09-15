package com.shopper.ecomm.repositories;

import com.shopper.ecomm.model.Category;
import com.shopper.ecomm.model.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    Product product1;
    Product product2;
    List<Product> products;

    @BeforeEach
    void setUp() {

        product1 = new Product(null, "Test Product", "TestImage.png", "test description", 10, 10.0, 10.0, 90.0, null);
        product2 = new Product(null, "Test Product2", "TestImage2.png", "test description2", 20, 20.0, 20.0, 60.0, null);
        products = List.of(product1, product2);
        System.out.println(products.size());
        productRepository.saveAll(products);

    }

    @AfterEach
    void tearDown() {
        product1 = null;
        product2 = null;
        products = null;

        productRepository.deleteAll();
    }


    @Test
    public void testFindByProductName_ReturnsProducts() {
        List<Product> foundProduct1 = productRepository.findByProductName(product1.getProductName());
        assertEquals(product1.getProductName(), foundProduct1.get(0).getProductName());
        assertEquals(foundProduct1.size(), 1);
    }

    @Test
    public void testFindByProductName_ReturnsNull() {
        List<Product> foundProduct = productRepository.findByProductName("Not Found");
        assertEquals(foundProduct.size(), 0);
    }

    @Test
    public void testFindByProductNameLikeIgnoreCase_ReturnsProducts() {
        Sort sortByAndOrder = Sort.by(Sort.Direction.ASC, "productName");
        Pageable pageDetails = PageRequest.of(0, 10, sortByAndOrder);
        Page<Product> productPage = productRepository.findByProductNameLikeIgnoreCase('%' + "test" + '%', pageDetails);
        List<Product> products = productPage.getContent();

        assertEquals(product1.getProductName(), products.get(0).getProductName());
        assertEquals(products.size(), 2);
    }

}

