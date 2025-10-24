package com.xuwei.service.impl;

import com.xuwei.model.Cart;
import com.xuwei.model.CartItem;
import com.xuwei.model.Food;
import com.xuwei.model.User;
import com.xuwei.repository.CartItemRepository;
import com.xuwei.repository.CartRepository;
import com.xuwei.repository.UserRepository;
import com.xuwei.request.AddCartItemRequest;
import com.xuwei.response.CartItemResponse;
import com.xuwei.response.CartResponse;
import com.xuwei.response.FoodSummary;
import com.xuwei.service.CartService;
import com.xuwei.service.FoodService;
import com.xuwei.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final UserService userService;
    private final CartItemRepository cartItemRepository;
    private final FoodService foodService;
    private final UserRepository userRepository;

    @Override
    public CartItemResponse addItemToCart(AddCartItemRequest request, String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Food food = foodService.findFoodById(request.getFoodId());

        Cart cart = getOrCreateCartForUser(user);

        for (CartItem cartItem : cart.getItems()) {
            if (cartItem.getFood().equals(food)) {
                int newQuantity = cartItem.getQuantity() + request.getQuantity();
                return updateCartItemQuantity(cartItem.getId(), newQuantity);
            }
        }

        CartItem newCartItem = CartItem.builder()
                .cart(cart)
                .food(food)
                .quantity(request.getQuantity())
                .ingredients(request.getIngredients())
                .totalPrice(food.getPrice() * request.getQuantity())
                .build();

        CartItem savedCartItem = cartItemRepository.save(newCartItem);
        cart.getItems().add(savedCartItem);
        cartRepository.save(cart);

        return convertToCartItemResponse(savedCartItem);
    }

    @Override
    public CartItemResponse updateCartItemQuantity(Long cartItemId, Integer quantity) throws Exception {
        Optional<CartItem> cartItem = cartItemRepository.findById(cartItemId);
        if (cartItem.isEmpty()) {
            throw new Exception("Cart item not found");
        }
        CartItem item = cartItem.get();
        item.setQuantity(quantity);
        item.setTotalPrice(item.getFood().getPrice() * quantity);
        CartItem updatedItem = cartItemRepository.save(item);

        return convertToCartItemResponse(updatedItem);
    }

    @Override
    public CartResponse removeCartItem(Long cartItemId, String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Cart cart = getOrCreateCartForUser(user);

        Optional<CartItem> cartItem = cartItemRepository.findById(cartItemId);
        if (cartItem.isEmpty()) {
            throw new Exception("Cart item not found");
        }

        CartItem item = cartItem.get();

        if (!item.getCart().getId().equals(cart.getId())) {
            throw new Exception("Cart item does not belong to user's cart");
        }

        cart.getItems().remove(item);
        cartItemRepository.delete(item);
        Cart updatedCart = cartRepository.save(cart);

        return convertToCartResponse(updatedCart);
    }

    @Override
    public CartResponse findCartByCustomerId(Long userId) throws Exception {
        Cart cart = getOrCreateCartByUserId(userId);
        return convertToCartResponse(cart);
    }

    @Override
    @Transactional
    public CartResponse clearCart(Long userId) throws Exception {
        User user = userService.findUserById(userId);
        Cart cart = getOrCreateCartForUser(user);

        List<Long> cartItemIds = cart.getItems().stream()
                .map(CartItem::getId)
                .collect(Collectors.toList());

        if (!cartItemIds.isEmpty()) {
            cartItemRepository.deleteByIdIn(cartItemIds);
        }

        cart.getItems().clear();
        Cart clearedCart = cartRepository.save(cart);

        return convertToCartResponse(clearedCart);
    }

    @Override
    public Long calculateCartTotal(Cart cart) throws Exception {
        long total = 0L;
        for (CartItem item : cart.getItems()) {
            total += item.getFood().getPrice() * item.getQuantity();
        }
        return total;
    }

    private Cart getOrCreateCartForUser(User user) {
        Cart cart = cartRepository.findByCustomerId(user.getId());
        if (cart == null) {
            cart = new Cart();
            cart.setCustomer(user);
            cart = cartRepository.save(cart);
        }
        return cart;
    }

    @Override
    public User findUserById(Long userId) throws Exception {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new Exception("User not found with id: " + userId);
        }
        return user.get();
    }

    private Cart getOrCreateCartByUserId(Long userId) throws Exception {
        Cart cart = cartRepository.findByCustomerId(userId);
        if (cart == null) {
            User user = userService.findUserById(userId);
            cart = new Cart();
            cart.setCustomer(user);
            cart = cartRepository.save(cart);
        }
        return cart;
    }

    private CartItemResponse convertToCartItemResponse(CartItem cartItem) {
        CartItemResponse response = new CartItemResponse();
        response.setId(cartItem.getId());
        response.setQuantity(cartItem.getQuantity());
        response.setIngredients(cartItem.getIngredients());
        response.setTotalPrice(cartItem.getTotalPrice());

        if (cartItem.getFood() != null) {
            Food food = cartItem.getFood();
            FoodSummary foodSummary = new FoodSummary();
            foodSummary.setId(food.getId());
            foodSummary.setName(food.getName());
            foodSummary.setDescription(food.getDescription());
            foodSummary.setPrice(food.getPrice());
            foodSummary.setAvailable(food.isAvailable());

            if (food.getFoodCategory() != null) {
                foodSummary.setCategoryName(food.getFoodCategory().getName());
            }

            response.setFood(foodSummary);
        }

        return response;
    }

    private CartResponse convertToCartResponse(Cart cart) throws Exception {
        CartResponse response = new CartResponse();
        response.setId(cart.getId());

        List<CartItemResponse> itemResponses = cart.getItems().stream()
                .map(this::convertToCartItemResponse)
                .collect(Collectors.toList());
        response.setItems(itemResponses);

        Long totalAmount = calculateCartTotal(cart);
        Integer totalItems = itemResponses.stream()
                .mapToInt(CartItemResponse::getQuantity)
                .sum();

        response.setTotalAmount(totalAmount);
        response.setTotalItems(totalItems);

        return response;
    }
}