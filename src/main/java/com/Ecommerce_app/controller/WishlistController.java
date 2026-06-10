package com.Ecommerce_app.controller;

import com.Ecommerce_app.Dtos.WishlistResponse;
import com.Ecommerce_app.services.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlist")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;

    @PostMapping("/{productId}")
    public ResponseEntity<String>
    addToWishlist(
            @PathVariable Long productId,
            Authentication authentication) {

        return ResponseEntity.ok(
                wishlistService.addToWishlist(
                        authentication.getName(),
                        productId));
    }

    @GetMapping
    public ResponseEntity<List<WishlistResponse>>
    getWishlist(
            Authentication authentication) {

        return ResponseEntity.ok(
                wishlistService.getWishlist(
                        authentication.getName()));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String>
    removeFromWishlist(
            @PathVariable Long productId,
            Authentication authentication) {

        return ResponseEntity.ok(
                wishlistService.removeFromWishlist(
                        authentication.getName(),
                        productId));
    }
}