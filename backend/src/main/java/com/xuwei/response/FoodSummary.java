package com.xuwei.response;

import lombok.Data;

@Data
public class FoodSummary {
    private Long id;
    private String name;
    private String description;
    private Long price;
    private Boolean available;
    private String categoryName;
}

