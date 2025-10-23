package com.xuwei.service;

import com.xuwei.model.IngredientCategory;
import com.xuwei.model.IngredientsItem;

import java.util.List;

public interface IngredientService {
    IngredientCategory createIngredientCategory(String name,
                                        Long restaurantId) throws Exception;

    void deleteIngredient(Long ingredientId) throws Exception;

    IngredientCategory findIngredientCategoryById(Long ingredientId) throws Exception;

    List<IngredientCategory> findIngredientCategoryByRestaurantId(Long restaurantId) throws Exception;

    List<IngredientsItem> findRestaurantIngredients(Long restaurantId);

    IngredientsItem createIngredientItem(Long restaurantId,
                                         String name,
                                         Long categoryId) throws Exception;

    IngredientsItem updateStock(Long ingredientId) throws Exception;
}
