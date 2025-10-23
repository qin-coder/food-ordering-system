package com.xuwei.controller;

import com.xuwei.dto.RestaurantDTO;
import com.xuwei.model.Restaurant;
import com.xuwei.model.User;
import com.xuwei.service.RestaurantService;
import com.xuwei.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/restaurants")
public class RestaurantController {
    private final RestaurantService restaurantService;
    private final UserService userService;

    @GetMapping("/search")
    public ResponseEntity<List<Restaurant>> searchRestaurants(
            @RequestParam String keyword
    ) {
        return ResponseEntity.ok(restaurantService.searchRestaurants(keyword));
    }

    @GetMapping
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        return ResponseEntity.ok(restaurantService.getAllRestaurants());
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<Restaurant> getRestaurantById(
            @PathVariable Long restaurantId
    ) throws Exception {
        return ResponseEntity.ok(restaurantService.findRestaurantById(restaurantId));
    }

    @PostMapping("/favorite/{restaurantId}")
    public ResponseEntity<RestaurantDTO> toggleFavorite(
            @PathVariable Long restaurantId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        RestaurantDTO dto =
                restaurantService.addToFavorites(restaurantId, user);
        return ResponseEntity.ok(dto);
    }


}
