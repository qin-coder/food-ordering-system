package com.xuwei.dto;

import com.xuwei.model.Category;
import com.xuwei.model.Food;
import com.xuwei.model.IngredientsItem;
import lombok.Data;

import java.util.List;

@Data
public class FoodDTO {
    private Long id;
    private String name;
    private String description;
    private Long price;
    private Category foodCategory;
    private List<String> images;
    private boolean available;
    private List<IngredientsItem> ingredients;
    private boolean seasonal;
    private boolean vegetarian;
    

    public FoodDTO(Food food) {
        this.id = food.getId();
        this.name = food.getName();
        this.description = food.getDescription();
        this.price = food.getPrice();
        this.foodCategory = food.getFoodCategory();
        this.images = food.getImages();
        this.available = food.isAvailable();
        this.ingredients = food.getIngredients();
        this.seasonal = food.isSeasonal();
        this.vegetarian = food.isVegetarian();
    }
}