package com.xuwei.service;

import com.xuwei.model.User;
import com.xuwei.request.OrderRequest;
import com.xuwei.response.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(OrderRequest order, User user) throws Exception;

    OrderResponse updateOrder(Long orderId, String orderStatus) throws Exception;


    List<OrderResponse> getUserOrders(User user) throws Exception;

    List<OrderResponse> getRestaurantsOrder(Long restaurantId,
                                            String orderStatus) throws Exception;

    OrderResponse findOrderById(Long orderId) throws Exception;

    OrderResponse cancelOrder(Long orderId) throws Exception;
}