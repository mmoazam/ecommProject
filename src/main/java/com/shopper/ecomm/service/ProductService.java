package com.shopper.ecomm.service;

import com.shopper.ecomm.model.Product;
import com.shopper.ecomm.payload.ProductDTO;
import org.springframework.stereotype.Service;

public interface ProductService {
    ProductDTO addProduct(Long categoryId, Product product);
}
