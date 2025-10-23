package com.xuwei.controller;

import com.xuwei.model.Category;
import com.xuwei.model.Restaurant;
import com.xuwei.model.User;
import com.xuwei.response.MessageResponse;
import com.xuwei.service.CategoryService;
import com.xuwei.service.RestaurantService;
import com.xuwei.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/category")
public class CategoryController {
    private final CategoryService categoryService;
    private final UserService userService;
    private final RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category,
                                                   @RequestHeader(
                                                           "Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Category createdCategory =
                categoryService.createCategory(category.getName(),
                        user.getId());
        return ResponseEntity.ok(createdCategory);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<MessageResponse> deleteCategory(@PathVariable Long categoryId) throws Exception {
        categoryService.deleteCategory(categoryId);
        MessageResponse res = new MessageResponse();
        res.setMessage("Category deleted successfully");
        return ResponseEntity.ok(res);
    }

    @GetMapping("/restaurant")
    public ResponseEntity<List<Category>> getRestaurantCategories(
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.getRestaurantByUserId(user.getId());
        List<Category> categories = categoryService.findCategoryByRestaurantId(restaurant.getId());
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> getCategory(@PathVariable Long categoryId) throws Exception {
        Category category =
                categoryService.findCategoryById(categoryId);
        return ResponseEntity.ok(category);
    }
}
