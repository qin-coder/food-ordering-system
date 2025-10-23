package com.xuwei.service.impl;

import com.xuwei.model.Category;
import com.xuwei.model.Food;
import com.xuwei.model.Restaurant;
import com.xuwei.repository.FoodRepository;
import com.xuwei.request.CreateFoodRequest;
import com.xuwei.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {
    private final FoodRepository foodRepository;

    @Override
    public Food createFood(CreateFoodRequest request,
                           Category category, Restaurant restaurant) {
        Food food = Food.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .foodCategory(category)
                .images(request.getImages())
                .restaurant(restaurant)
                .isVegetarian(request.isVegetarian())
                .isSeasonal(request.isSeasonal())
                .ingredients(request.getIngredients())
                .build();
        Food savedFood = foodRepository.save(food);
        restaurant.getFoods().add(savedFood);

        return savedFood;
    }


    @Override
    public void deleteFood(Long foodId) throws Exception {
        foodRepository.deleteById(foodId);
    }

    @Override
    public List<Food> getRestaurantFoods(Long restaurantId,
                                         boolean isVegetarian,
                                         boolean isNonveg,
                                         boolean isSeasonal,
                                         String foodCategory) {
        Stream<Food> stream = foodRepository.findByRestaurantId(restaurantId).stream();

        if (isVegetarian)
            stream = stream.filter(Food::isVegetarian);

        if (isNonveg)
            stream = stream.filter(food -> !food.isVegetarian());

        if (isSeasonal)
            stream = stream.filter(Food::isSeasonal);

        if (foodCategory != null && !foodCategory.isEmpty())
            stream = stream.filter(food -> food.getFoodCategory() != null &&
                    foodCategory.equals(food.getFoodCategory().getName()));

        return stream.collect(Collectors.toList());
    }


    @Override
    public List<Food> searchFood(String keyword) {
        return foodRepository.searchFood(keyword);
    }

    @Override
    public Food findFoodById(Long foodId) throws Exception {
        Optional<Food> food = foodRepository.findById(foodId);
        if (food.isEmpty()){
            throw new Exception("Food not found");
        }
        return food.get();
    }

    @Override
    public Food updateAvailabilityStatus(Long foodId) throws Exception {
        Food food = findFoodById(foodId);
        food.setAvailable(!food.isAvailable());
        foodRepository.save(food);
        return food;
    }

//    @Override
//    public Food updateFood(Long foodId, CreateFoodRequest updatedFood) throws Exception {
//        Food food = findFoodById(foodId);
//        if (updatedFood.getName() != null && updatedFood.getName().trim().isEmpty()) {
//            throw new IllegalArgumentException("Food name cannot be empty");
//        }
//        if (updatedFood.getPrice() != null && updatedFood.getPrice().doubleValue() <= 0) {
//            throw new IllegalArgumentException("Price must be positive");
//        }
//        Optional.ofNullable(updatedFood.getName()).ifPresent(food::setName);
//        Optional.ofNullable(updatedFood.getDescription()).ifPresent(food::setDescription);
//        Optional.ofNullable(updatedFood.getPrice()).ifPresent(food::setPrice);
//        Optional.ofNullable(updatedFood.getImages()).filter(list -> !list.isEmpty()).ifPresent(food::setImages);
//        Optional.ofNullable(updatedFood.getIngredients()).ifPresent(food::setIngredients);
//
//        food.setVegetarian(updatedFood.isVegetarian());
//        food.setSeasonal(updatedFood.isSeasonal());
//
//        return foodRepository.save(food);
//    }
}
