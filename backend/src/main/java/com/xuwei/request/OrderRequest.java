package com.xuwei.request;

import com.xuwei.model.Address;
import lombok.Data;

@Data
public class OrderRequest {
    private Long restaurantId;
    private Address deliveryAddress;
    private Long cartId;
}
