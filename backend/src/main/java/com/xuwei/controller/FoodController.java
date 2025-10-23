package com.xuwei.controller;

import com.xuwei.dto.FoodDTO;
import com.xuwei.model.Food;
import com.xuwei.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/foods")
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<FoodDTO>> getRestaurantFoods(
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

        List<FoodDTO> foodDTOs = foods.stream()
                .map(FoodDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(foodDTOs);
    }

    @GetMapping("/search")
    public ResponseEntity<List<FoodDTO>> searchFood(@RequestParam String keyword) {
        List<Food> results = foodService.searchFood(keyword);
        List<FoodDTO> foodDTOs = results.stream()
                .map(FoodDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(foodDTOs);
    }

    @GetMapping("/{foodId}")
    public ResponseEntity<FoodDTO> getFoodById(@PathVariable Long foodId) throws Exception {
        Food food = foodService.findFoodById(foodId);
        FoodDTO foodDTO = new FoodDTO(food);
        return ResponseEntity.ok(foodDTO);
    }
}