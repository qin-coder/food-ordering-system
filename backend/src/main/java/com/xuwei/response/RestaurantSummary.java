package com.xuwei.response;

import lombok.Data;

@Data
public class RestaurantSummary {
    private Long id;
    private String name;
    private String cuisineType;
}