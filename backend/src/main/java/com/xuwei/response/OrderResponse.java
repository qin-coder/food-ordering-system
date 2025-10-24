package com.xuwei.response;

import com.xuwei.model.Address;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class OrderResponse {
    private Long id;
    private UserSummary customer;
    private RestaurantSummary restaurant;
    private List<OrderItemResponse> items;
    private Long totalAmount;
    private String orderStatus;
    private Date createdAt;
    private Address deliveryAddress;
}