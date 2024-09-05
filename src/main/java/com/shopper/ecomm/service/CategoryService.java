package com.shopper.ecomm.service;

import com.shopper.ecomm.model.Category;
import com.shopper.ecomm.payload.CategoryDTO;
import com.shopper.ecomm.payload.CategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryResponse getAllCategories();

    CategoryDTO createCategory(CategoryDTO category);

    CategoryDTO deleteCategory(Long categoryId);

    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId);
}
