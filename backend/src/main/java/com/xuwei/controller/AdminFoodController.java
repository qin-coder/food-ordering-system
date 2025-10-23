package com.xuwei.controller;

import com.xuwei.model.Category;
import com.xuwei.model.Food;
import com.xuwei.model.Restaurant;
import com.xuwei.model.User;
import com.xuwei.request.CreateFoodRequest;
import com.xuwei.response.MessageResponse;
import com.xuwei.service.CategoryService;
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
    private final CategoryService categoryService;

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
        Category category = categoryService.findCategoryById(request.getCategoryId());

        Food food = foodService.createFood(request, category, restaurant);
        return ResponseEntity.ok(food);
    }

    @DeleteMapping("/{foodId}")
    public ResponseEntity<MessageResponse> deleteFood(@PathVariable Long foodId) throws Exception {
        foodService.deleteFood(foodId);
        MessageResponse message = new MessageResponse();
        message.setMessage("Food deleted");
        return ResponseEntity.ok(message);
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
