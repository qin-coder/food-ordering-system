package com.xuwei.controller;

import com.xuwei.model.Restaurant;
import com.xuwei.model.User;
import com.xuwei.request.CreateRestaurantRequest;
import com.xuwei.response.MessageResponse;
import com.xuwei.service.RestaurantService;
import com.xuwei.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/restaurants")
public class AdminRestaurantController {
    private final RestaurantService restaurantService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody CreateRestaurantRequest request,
                                                       @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant =
                restaurantService.createRestaurant(request, user);
        return ResponseEntity.ok(restaurant);
    }

    @PutMapping("/{restaurantId}")
    public ResponseEntity<Restaurant> updateRestaurant(
            @PathVariable Long restaurantId,
            @RequestBody CreateRestaurantRequest request,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Restaurant updated =
                restaurantService.updateRestaurant(restaurantId,
                        request, user);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{restaurantId}")
    public ResponseEntity<MessageResponse> deleteRestaurant(
            @PathVariable Long restaurantId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant =
                restaurantService.getRestaurantByUserId(user.getId());
        if (!restaurant.getId().equals(restaurantId)) {
            return ResponseEntity.status(403).build(); // Forbidden
        }
        restaurantService.deleteRestaurant(restaurantId);
        MessageResponse message = new MessageResponse();
        message.setMessage("Restaurant deleted");
        return ResponseEntity.ok(message);
    }

    @PutMapping("/status/{restaurantId}")
    public ResponseEntity<Restaurant> updateUserStatus(
            @PathVariable Long restaurantId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.getRestaurantByUserId(user.getId());
        if (!restaurant.getId().equals(restaurantId)) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(restaurantService.updateRestaurantStatus(restaurantId));
    }
    @GetMapping()
    public ResponseEntity<Restaurant> findRestaurantByUserId(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.getRestaurantByUserId(user.getId());
        return ResponseEntity.ok(restaurant);
    }

}
