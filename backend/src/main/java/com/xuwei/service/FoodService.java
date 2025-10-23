package com.xuwei.service;

import com.xuwei.model.Category;
import com.xuwei.model.Food;
import com.xuwei.model.Restaurant;
import com.xuwei.request.CreateFoodRequest;

import java.util.List;

public interface FoodService {
    Food createFood(CreateFoodRequest request,
                           Category category, Restaurant restaurant);

    void deleteFood(Long foodId) throws Exception;

    List<Food> getRestaurantFoods(Long restaurantId
            , boolean isVegetarian
            , boolean isNonveg
            , boolean isSeasonal
            , String foodCategory);

    List<Food> searchFood(String keyword);
    Food findFoodById(Long foodId) throws Exception;
    Food updateAvailabilityStatus(Long foodId) throws Exception;
    //Food updateFood(Long foodId, CreateFoodRequest updatedFood) throws Exception;

}
