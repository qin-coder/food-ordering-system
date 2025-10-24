package com.xuwei.response;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class CartResponse {
    private Long id;
    private List<CartItemResponse> items = new ArrayList<>();
    private Long totalAmount;
    private Integer totalItems;
}