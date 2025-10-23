package com.xuwei.service;

import java.util.List;

public interface CategoryService {
    CategoryService createCategory(String name, Long userId);

    void deleteCategory(Long categoryId);

    List<CategoryService> findCategoryByRestaurantId() throws Exception;

    CategoryService findCategoryById(Long categoryId) throws Exception;

}
