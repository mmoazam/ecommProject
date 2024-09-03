package com.shopper.ecomm.service;

import com.shopper.ecomm.model.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getAllCategories();
    void createCategory(Category category);
}
