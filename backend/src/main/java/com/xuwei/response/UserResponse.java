package com.xuwei.response;

import com.xuwei.config.USER_ROLE;
import com.xuwei.dto.FavoriteRestaurantDTO;
import com.xuwei.model.Address;
import lombok.Data;
import java.util.List;

@Data
public class UserResponse {
    private Long id;
    private String fullName;
    private String email;
    private String role;
    private List<FavoriteRestaurantDTO> favorites;
    private List<Address> addresses;

    public UserResponse(Long id, String fullName, String email, USER_ROLE role,
                        List<FavoriteRestaurantDTO> favorites, List<Address> addresses) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.role = role.name();
        this.favorites = favorites;
        this.addresses = addresses;
    }
    
    public UserResponse(Long id, String fullName, String email, String role,
                       List<FavoriteRestaurantDTO> favorites, List<Address> addresses) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
        this.favorites = favorites;
        this.addresses = addresses;
    }
}