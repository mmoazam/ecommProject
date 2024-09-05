package com.shopper.ecomm.service;

import com.shopper.ecomm.model.Category;
import com.shopper.ecomm.payload.CategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryResponse getAllCategories();

    void createCategory(Category category);

    String deleteCategory(Long categoryId);

    Category updateCategory(Category category, Long categoryId);
}
