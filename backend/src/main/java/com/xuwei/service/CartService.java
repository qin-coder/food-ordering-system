package com.xuwei.service;

import com.xuwei.model.Cart;
import com.xuwei.model.CartItem;
import com.xuwei.model.User;
import com.xuwei.request.AddCartItemRequest;
import com.xuwei.response.CartItemResponse;
import com.xuwei.response.CartResponse;

public interface CartService {
    CartItemResponse addItemToCart(AddCartItemRequest request,
                                   String jwt) throws Exception;

    CartItemResponse updateCartItemQuantity(Long cartItemId,
                                    Integer quantity) throws Exception;

    CartResponse removeCartItem(Long cartItemId, String jwt) throws Exception;

    Long calculateCartTotal(Cart cart) throws Exception;

    User findUserById(Long userId) throws Exception;

    CartResponse findCartByCustomerId(Long userId) throws Exception;

    CartResponse clearCart(Long userId) throws Exception;
}
