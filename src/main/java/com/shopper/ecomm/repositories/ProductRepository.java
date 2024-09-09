package com.shopper.ecomm.repositories;

import com.shopper.ecomm.model.Category;
import com.shopper.ecomm.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(Category category);

    List<Product> findByCategoryOrderByPriceAsc(Category category);
}
