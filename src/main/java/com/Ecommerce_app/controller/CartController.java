package com.Ecommerce_app.controller;

import com.Ecommerce_app.Dtos.AddToCartRequest;
import com.Ecommerce_app.Dtos.UpdateCartRequest;
import com.Ecommerce_app.entities.CartItem;
import com.Ecommerce_app.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService service;

//    http://localhost:8080/cart
    @PostMapping
    public CartItem addToCart(
            Authentication authentication,
            @RequestBody AddToCartRequest request) {
        return service.addToCart(
                authentication.getName(),
                request);
    }

    @GetMapping
    public List<CartItem> viewCart(
            Authentication authentication) {

        return service.getCart(
                authentication.getName());
    }

    @PutMapping("/{cartId}")
    public CartItem updateQuantity(
            @PathVariable Long cartId,
            @RequestBody UpdateCartRequest request) {

        return service.updateQuantity(
                cartId,
                request.getQuantity());
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<String> remove(
            @PathVariable Long cartId) {
        service.removeFromCart(cartId);
        return ResponseEntity.ok("Item removed from cart successfully");
    }
}
