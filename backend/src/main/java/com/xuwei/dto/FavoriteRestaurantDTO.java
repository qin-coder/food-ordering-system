package com.xuwei.dto;

import com.xuwei.model.Address;
import lombok.Data;

@Data
public class FavoriteRestaurantDTO {
    private Long id;
    private String name;
    private String description;
    private String cuisineType;
    private Address address;
    
    public FavoriteRestaurantDTO(Long id, String name, String description, String cuisineType, Address address) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cuisineType = cuisineType;
        this.address = address;
    }
}