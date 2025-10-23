package com.xuwei.controller;

import com.xuwei.model.IngredientCategory;
import com.xuwei.model.IngredientsItem;
import com.xuwei.request.IngredientCategoryRequest;
import com.xuwei.request.IngredientRequest;
import com.xuwei.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/ingredient")
public class IngredientController {
    private final IngredientService ingredientService;

    @PostMapping("/category")
    public ResponseEntity<IngredientCategory> createIngredientCategory(@RequestBody IngredientCategoryRequest request) throws Exception {
        IngredientCategory item =
                ingredientService.createIngredientCategory(request.getName(), request.getRestaurantId());
        return ResponseEntity.ok(item);

    }

    @PostMapping
    public ResponseEntity<IngredientsItem> createIngredientItem(@RequestBody IngredientRequest request) throws Exception {
        IngredientsItem item =
                ingredientService.createIngredientItem(request.getRestaurantId(), request.getName(), request.getCategoryId());
        return ResponseEntity.ok(item);

    }

    @PutMapping("/{ingredientId}/stock")
    public ResponseEntity<IngredientsItem> updateStock(@PathVariable Long ingredientId) throws Exception {
        IngredientsItem item =
                ingredientService.updateStock(ingredientId);
        return ResponseEntity.ok(item);
    }

    @DeleteMapping("/{ingredientId}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable Long ingredientId) throws Exception {
        ingredientService.deleteIngredient(ingredientId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<IngredientsItem>> findRestaurantIngredients(@PathVariable Long restaurantId) {
        List<IngredientsItem> items =
                ingredientService.findRestaurantIngredients(restaurantId);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/restaurant/category/{categoryId}")
    public ResponseEntity<List<IngredientCategory>> findRestaurantIngredientsCategory(@PathVariable Long categoryId) throws Exception {
        List<IngredientCategory> items =
                ingredientService.findIngredientCategoryByRestaurantId(categoryId);
        return ResponseEntity.ok(items);
    }
}
