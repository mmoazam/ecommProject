package com.shopper.ecomm.service;

import com.shopper.ecomm.model.Product;
import com.shopper.ecomm.payload.ProductDTO;
import com.shopper.ecomm.payload.ProductResponse;
import org.springframework.stereotype.Service;

public interface ProductService {
    ProductDTO addProduct(Long categoryId, Product product);

    ProductResponse getAllProducts();

    ProductResponse getProductsByCategoryId(Long categoryId);
}
