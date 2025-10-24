package com.xuwei.response;

import lombok.Data;
import java.util.List;

@Data
public class CartItemResponse {
    private Long id;
    private FoodSummary food;
    private Integer quantity;
    private List<String> ingredients;
    private Long totalPrice;
}