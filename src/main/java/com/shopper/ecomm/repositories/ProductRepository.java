package com.shopper.ecomm.repositories;

import com.shopper.ecomm.model.Category;
import com.shopper.ecomm.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(Category category);

    Page<Product> findByCategoryOrderByPriceAsc(Category category, Pageable pageDetails);

    List<Product> findByProductName(String keyword);

    List<Product> findByProductNameIgnoreCase(String s);

    Page<Product> findByProductNameLikeIgnoreCase(String s, Pageable pageDetails);
}
