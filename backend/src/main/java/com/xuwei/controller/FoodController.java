package com.xuwei.controller;

import com.xuwei.model.Food;
import com.xuwei.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/foods")
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Food>> getRestaurantFoods(
            @PathVariable Long restaurantId,
            @RequestParam(defaultValue = "false") boolean isVegetarian,
            @RequestParam(defaultValue = "false") boolean isNonveg,
            @RequestParam(defaultValue = "false") boolean isSeasonal,
            @RequestParam(required = false) String foodCategory
    ) {
        List<Food> foods = foodService.getRestaurantFoods(
                restaurantId,
                isVegetarian,
                isNonveg,
                isSeasonal,
                foodCategory
        );
        return ResponseEntity.ok(foods);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Food>> searchFood(@RequestParam String keyword) {
        List<Food> results = foodService.searchFood(keyword);
        return ResponseEntity.ok(results);
    }


    @GetMapping("/{foodId}")
    public ResponseEntity<Food> getFoodById(@PathVariable Long foodId) throws Exception {
        Food food = foodService.findFoodById(foodId);
        return ResponseEntity.ok(food);
    }
}
