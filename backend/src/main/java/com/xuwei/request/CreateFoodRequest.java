package com.xuwei.request;

import com.xuwei.model.Category;
import com.xuwei.model.IngredientsItem;
import lombok.Data;

import java.util.List;


@Data
public class CreateFoodRequest {
    private String name;
    private String description;
    private Long price;
    //private Category category;
    private Long categoryId;
    private List<String> images;
    private Long restaurantId;
    private boolean vegetarian;
    private boolean seasonal;
    private List<IngredientsItem> ingredients;

}
