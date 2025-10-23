package com.xuwei.controller;

import com.xuwei.model.Food;
import com.xuwei.model.Restaurant;
import com.xuwei.model.User;
import com.xuwei.request.CreateFoodRequest;
import com.xuwei.response.MessageResponse;
import com.xuwei.service.FoodService;
import com.xuwei.service.RestaurantService;
import com.xuwei.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/food")
public class AdminFoodController {
    private final FoodService foodService;
    private final UserService userService;
    private final RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<Food> createFood(@RequestBody CreateFoodRequest request,
                                           @RequestHeader(
                                                   "Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant =
                restaurantService.getRestaurantByUserId(user.getId());

        if (!restaurant.getId().equals(request.getRestaurantId())) {
            throw new Exception("You are not authorized to add food" +
                    " to this restaurant");
        }

        Food food = foodService.createFood(request,
                request.getCategory(), restaurant);
        return ResponseEntity.ok(food);
    }

    @DeleteMapping("/{foodId}")
    public ResponseEntity<Food> deleteFood(@PathVariable Long foodId) throws Exception {
        Food food = foodService.findFoodById(foodId);
        foodService.deleteFood(foodId);
        return ResponseEntity.ok(food);
    }

    @PostMapping("/{foodId}")
    public ResponseEntity<Food> updateFoodAvailability(@PathVariable Long foodId, @RequestHeader("Authorization") String jwt) throws Exception {
        Food updated = foodService.updateAvailabilityStatus(foodId);
        return ResponseEntity.ok(updated);
    }


    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Food>> getRestaurantFoods(@PathVariable Long restaurantId) {
        List<Food> foods =
                foodService.getRestaurantFoods(restaurantId, false,
                        false, false, null);
        return ResponseEntity.ok(foods);
    }

}
