package com.xuwei.service.impl;

import com.xuwei.model.*;
import com.xuwei.repository.*;
import com.xuwei.request.OrderRequest;
import com.xuwei.response.*;
import com.xuwei.service.CartService;
import com.xuwei.service.FoodService;
import com.xuwei.service.OrderService;
import com.xuwei.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final RestaurantService restaurantService;
    private final CartService cartService;
    private final FoodService foodService;

    @Override
    @Transactional
    public OrderResponse createOrder(OrderRequest orderRequest, User user) throws Exception {
        Address deliveryAddress = orderRequest.getDeliveryAddress();
        Address savedAddress = addressRepository.save(deliveryAddress);
        if (!user.getAddresses().contains(savedAddress)) {
            user.getAddresses().add(savedAddress);
            userRepository.save(user);
        }

        Restaurant restaurant = restaurantService.findRestaurantById(orderRequest.getRestaurantId());
        Order createdOrder = new Order();
        createdOrder.setCustomer(user);
        createdOrder.setCreatedAt(new Date());
        createdOrder.setOrderStatus("PENDING");
        createdOrder.setDeliveryAddress(savedAddress);
        createdOrder.setRestaurant(restaurant);

        CartResponse cart = cartService.findCartByCustomerId(user.getId());
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItemResponse cartItemResponse : cart.getItems()) {
            Food food = foodService.findFoodById(cartItemResponse.getFood().getId());

            OrderItem orderItem = new OrderItem();
            orderItem.setFood(food);
            orderItem.setQuantity(cartItemResponse.getQuantity());
            orderItem.setIngredients(cartItemResponse.getIngredients());
            orderItem.setTotalPrice(cartItemResponse.getTotalPrice());

            OrderItem savedOrderItem = orderItemRepository.save(orderItem);
            orderItems.add(savedOrderItem);
        }

        createdOrder.setItems(orderItems);
        createdOrder.setTotalAmount(cart.getTotalAmount());

        Order savedOrder = orderRepository.save(createdOrder);

        try {
            cartService.clearCart(user.getId());
        } catch (Exception e) {
            System.err.println("Failed to clear cart after order creation: " + e.getMessage());
        }

        return convertToOrderResponse(savedOrder);
    }

    @Override
    @Transactional
    public OrderResponse updateOrder(Long orderId, String orderStatus) throws Exception {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new Exception("Order not found with id: " + orderId));
        order.setOrderStatus(orderStatus);
        Order updatedOrder = orderRepository.save(order);
        return convertToOrderResponse(updatedOrder);
    }

    @Override
    @Transactional
    public OrderResponse cancelOrder(Long orderId) throws Exception {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new Exception("Order not found with id: " + orderId));
        order.setOrderStatus("CANCELLED");
        Order cancelledOrder = orderRepository.save(order);
        return convertToOrderResponse(cancelledOrder);
    }

    @Override
    public List<OrderResponse> getUserOrders(User user) throws Exception {
        List<Order> orders = orderRepository.findByCustomerId(user.getId());
        return orders.stream()
                .map(this::convertToOrderResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderResponse> getRestaurantsOrder(Long restaurantId, String orderStatus) throws Exception {
        List<Order> orders;
        if (orderStatus != null) {
            orders = orderRepository.findByRestaurantIdAndOrderStatus(restaurantId, orderStatus);
        } else {
            orders = orderRepository.findByRestaurantId(restaurantId);
        }
        return orders.stream()
                .map(this::convertToOrderResponse)
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponse findOrderById(Long orderId) throws Exception {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new Exception("Order not found with id: " + orderId));
        return convertToOrderResponse(order);
    }

    private OrderResponse convertToOrderResponse(Order order) {
        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setTotalAmount(order.getTotalAmount());
        response.setOrderStatus(order.getOrderStatus());
        response.setCreatedAt(order.getCreatedAt());
        response.setDeliveryAddress(order.getDeliveryAddress());

        if (order.getCustomer() != null) {
            UserSummary userSummary = new UserSummary();
            userSummary.setId(order.getCustomer().getId());
            userSummary.setFullName(order.getCustomer().getFullName());
            userSummary.setEmail(order.getCustomer().getEmail());
            response.setCustomer(userSummary);
        }

        if (order.getRestaurant() != null) {
            RestaurantSummary restaurantSummary = new RestaurantSummary();
            restaurantSummary.setId(order.getRestaurant().getId());
            restaurantSummary.setName(order.getRestaurant().getName());
            restaurantSummary.setCuisineType(order.getRestaurant().getCuisineType());
            response.setRestaurant(restaurantSummary);
        }

        if (order.getItems() != null) {
            List<OrderItemResponse> itemResponses = order.getItems().stream()
                    .map(this::convertToOrderItemResponse)
                    .collect(Collectors.toList());
            response.setItems(itemResponses);
        }

        return response;
    }

    private OrderItemResponse convertToOrderItemResponse(OrderItem orderItem) {
        OrderItemResponse response = new OrderItemResponse();
        response.setId(orderItem.getId());
        response.setQuantity(orderItem.getQuantity());
        response.setIngredients(orderItem.getIngredients());
        response.setTotalPrice(orderItem.getTotalPrice());

        if (orderItem.getFood() != null) {
            Food food = orderItem.getFood();
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
}