package com.xuwei.service.impl;

import com.xuwei.model.IngredientCategory;
import com.xuwei.model.IngredientsItem;
import com.xuwei.model.Restaurant;
import com.xuwei.repository.IngredientCategoryRepository;
import com.xuwei.repository.IngredientsItemRepository;
import com.xuwei.service.IngredientService;
import com.xuwei.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {
    private final IngredientCategoryRepository ingredientCategoryRepository;
    private final IngredientsItemRepository ingredientsItemRepository;
    private final RestaurantService restaurantService;

    @Override
    public IngredientCategory createIngredientCategory(String name,
                                                       Long restaurantId) throws Exception {
        Restaurant restaurant =
                restaurantService.findRestaurantById(restaurantId);
        IngredientCategory ingredientCategory =
                IngredientCategory.builder()
                        .name(name)
                        .restaurant(restaurant)
                        .build();
        return ingredientCategoryRepository.save(ingredientCategory);
    }

    @Override
    public void deleteIngredient(Long ingredientId) throws Exception {
        ingredientCategoryRepository.deleteById(ingredientId);
    }

    @Override
    public IngredientCategory findIngredientCategoryById(Long ingredientId) throws Exception {
        Optional<IngredientCategory> ingredientCategory =
                ingredientCategoryRepository.findById(ingredientId);
        if (ingredientCategory.isEmpty())
            throw new Exception("Ingredient not found");
        return ingredientCategory.get();

    }

    @Override
    public List<IngredientCategory> findIngredientCategoryByRestaurantId(Long restaurantId) throws Exception {
        restaurantService.findRestaurantById(restaurantId);
        return ingredientCategoryRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public List<IngredientsItem> findRestaurantIngredients(Long restaurantId) {
        return ingredientsItemRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public IngredientsItem createIngredientItem(Long restaurantId,
                                                String name,
                                                Long categoryId) throws Exception {
        Restaurant restaurant =
                restaurantService.findRestaurantById(restaurantId);
        IngredientCategory category =
                findIngredientCategoryById(categoryId);

        IngredientsItem item = new IngredientsItem();
        item.setName(name);
        item.setRestaurant(restaurant);
        item.setCategory(category);

        IngredientsItem savedItem =
                ingredientsItemRepository.save(item);
        category.getIngredients().add(savedItem);
        return savedItem;

    }

    @Override
    public IngredientsItem updateStock(Long ingredientId) throws Exception {
        Optional<IngredientsItem> item =
                ingredientsItemRepository.findById(ingredientId);
        if (item.isEmpty()) {
            throw new Exception("Ingredient not found");
        }
        IngredientsItem ingredientsItem = item.get();
        ingredientsItem.setStock(!ingredientsItem.isStock());
        return ingredientsItemRepository.save(ingredientsItem);
    }
}
