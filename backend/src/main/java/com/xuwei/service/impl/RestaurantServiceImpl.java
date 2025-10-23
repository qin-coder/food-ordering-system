package com.xuwei.service.impl;

import com.xuwei.dto.RestaurantDTO;
import com.xuwei.model.Address;
import com.xuwei.model.Restaurant;
import com.xuwei.model.User;
import com.xuwei.repository.AddressRepository;
import com.xuwei.repository.RestaurantRepository;
import com.xuwei.repository.UserRepository;
import com.xuwei.request.CreateRestaurantRequest;
import com.xuwei.service.RestaurantService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    @Override
    public Restaurant createRestaurant(CreateRestaurantRequest request, User user) {
        Address address = addressRepository.save(request.getAddress());

        Restaurant restaurant = Restaurant.builder()
                .address(address)
                .contactInformation(request.getContactInformation())
                .cuisineType(request.getCuisineType())
                .description(request.getDescription())
                .images(request.getImages())
                .name(request.getName())
                .openingHours(request.getOpeningHours())
                .registrationDate(LocalDateTime.now())
                .owner(user)
                .build();

        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(Long restaurantId,
                                       CreateRestaurantRequest updatedRestaurant,
                                       User user) throws Exception {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found"));

        if (!restaurant.getOwner().getId().equals(user.getId())) {
            throw new AccessDeniedException("You are not the owner of this restaurant.");
        }

        Optional.ofNullable(updatedRestaurant.getCuisineType()).ifPresent(restaurant::setCuisineType);
        Optional.ofNullable(updatedRestaurant.getDescription()).ifPresent(restaurant::setDescription);
        Optional.ofNullable(updatedRestaurant.getName()).ifPresent(restaurant::setName);
        Optional.ofNullable(updatedRestaurant.getImages()).ifPresent(restaurant::setImages);
        Optional.ofNullable(updatedRestaurant.getContactInformation()).ifPresent(restaurant::setContactInformation);
        Optional.ofNullable(updatedRestaurant.getOpeningHours()).ifPresent(restaurant::setOpeningHours);
        Optional.ofNullable(updatedRestaurant.getAddress()).ifPresent(addr -> {
            Address saved = addressRepository.save(addr);
            restaurant.setAddress(saved);
        });

        return restaurantRepository.save(restaurant);
    }

    @Override
    public void deleteRestaurant(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found"));
        restaurantRepository.delete(restaurant);
    }


    @Override
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    @Override
    public List<Restaurant> searchRestaurants(String keyword) {
        return restaurantRepository.findBySearchQuery(keyword);
    }

    @Override
    public Restaurant findRestaurantById(Long restaurantId) {
        return restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found"));
    }

    @Override
    public Restaurant getRestaurantByUserId(Long userId) {
        Restaurant restaurant = restaurantRepository.findByOwnerId(userId);
        if (restaurant == null) {
            throw new EntityNotFoundException("Restaurant not found for user ID: " + userId);
        }
        return restaurant;
    }

    @Override
    public RestaurantDTO addToFavorites(Long restaurantId, User user) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found"));
        boolean isFavorite = user.getFavorites().contains(restaurant);

        if (isFavorite) {
            user.getFavorites().remove(restaurant);
        } else {
            user.getFavorites().add(restaurant);
        }

        userRepository.save(user);

        return RestaurantDTO.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .description(restaurant.getDescription())
                .images(restaurant.getImages())
                .cuisineType(restaurant.getCuisineType())
                .build();
    }


    @Override
    public Restaurant updateRestaurantStatus(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found"));

        restaurant.setOpen(!restaurant.isOpen());
        return restaurantRepository.save(restaurant);
    }
}
