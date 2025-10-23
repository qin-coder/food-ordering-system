package com.xuwei.service.impl;

import com.xuwei.model.Category;
import com.xuwei.service.CategoryService;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    @Override
    public CategoryService createCategory(String name, Long userId) {
        return null;
    }

    @Override
    public void deleteCategory(Long categoryId) {

    }

    @Override
    public List<CategoryService> findCategoryByRestaurantId() throws Exception {
        return List.of();
    }

    @Override
    public CategoryService findCategoryById(Long categoryId) throws Exception {
        return null;
    }
}
