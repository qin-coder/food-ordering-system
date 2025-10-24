package com.xuwei.controller;


import com.xuwei.request.AddCartItemRequest;
import com.xuwei.request.UpdateCartItemRequest;
import com.xuwei.response.CartItemResponse;
import com.xuwei.response.CartResponse;
import com.xuwei.service.CartService;
import com.xuwei.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;
    private final UserService userService;

    @PutMapping("/add")
    public ResponseEntity<CartItemResponse> addItemToCart(@RequestBody AddCartItemRequest request,
                                                          @RequestHeader("Authorization") String jwt) throws Exception {
        CartItemResponse cartItem = cartService.addItemToCart(request, jwt);
        return ResponseEntity.ok(cartItem);
    }

    @PutMapping("/cart-item/update")
    public ResponseEntity<CartItemResponse> updateCartItemQuantity(@RequestBody UpdateCartItemRequest request,
                                                                   @RequestHeader("Authorization") String jwt) throws Exception {
        CartItemResponse cartItem = cartService.updateCartItemQuantity(request.getCartItemId(), request.getQuantity());
        return ResponseEntity.ok(cartItem);
    }

    @DeleteMapping("/cart-item/remove/{cartItemId}")
    public ResponseEntity<CartResponse> removeCartItem(
            @PathVariable Long cartItemId,
            @RequestHeader("Authorization") String jwt) throws Exception {
        CartResponse cart = cartService.removeCartItem(cartItemId, jwt);
        return ResponseEntity.ok(cart);
    }

    @PutMapping("/clear")
    public ResponseEntity<CartResponse> clearCart(@RequestHeader("Authorization") String jwt) throws Exception {
        CartResponse cart = cartService.clearCart(jwt);
        return ResponseEntity.ok(cart);
    }

    @GetMapping
    public ResponseEntity<CartResponse> findUserCart(@RequestHeader("Authorization") String jwt) throws Exception {
        CartResponse cart = cartService.findCartByCustomerId(jwt);
        return ResponseEntity.ok(cart);
    }
}