package com.xuwei.service;

import com.xuwei.model.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(String name, Long userId) throws Exception;

    void deleteCategory(Long categoryId);

    Category findCategoryById(Long categoryId) throws Exception;

    List<Category> findCategoryByRestaurantId(Long restaurantId) throws Exception;

}
