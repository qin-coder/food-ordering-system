package com.xuwei.controller;


import com.xuwei.response.OrderResponse;
import com.xuwei.service.OrderService;
import com.xuwei.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/orders")
public class OrderAdminController {
    private final OrderService orderService;

    @PutMapping("/{orderId}/status")
    public ResponseEntity<OrderResponse> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam String orderStatus) throws Exception {

        OrderResponse updatedOrder = orderService.updateOrder(orderId, orderStatus);
        return ResponseEntity.ok(updatedOrder);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<OrderResponse>> getRestaurantOrders(
            @PathVariable Long restaurantId,
            @RequestParam(required = false) String orderStatus) throws Exception {


        List<OrderResponse> orders = orderService.getRestaurantsOrder(restaurantId, orderStatus);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(
            @PathVariable Long orderId) throws Exception {

        OrderResponse order = orderService.findOrderById(orderId);
        return ResponseEntity.ok(order);
    }

    @PutMapping("/{orderId}/admin-cancel")
    public ResponseEntity<OrderResponse> adminCancelOrder(
            @PathVariable Long orderId,
            @RequestHeader("Authorization") String jwt) throws Exception {

        OrderResponse cancelledOrder = orderService.cancelOrder(orderId);
        return ResponseEntity.ok(cancelledOrder);
    }
}