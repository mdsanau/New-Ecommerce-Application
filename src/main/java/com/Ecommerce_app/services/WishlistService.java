package com.Ecommerce_app.services;

import com.Ecommerce_app.Dtos.WishlistResponse;
import com.Ecommerce_app.entities.Product;
import com.Ecommerce_app.entities.User;
import com.Ecommerce_app.entities.Wishlist;
import com.Ecommerce_app.exception.ResourceNotFoundException;
import com.Ecommerce_app.repositories.ProductRepository;
import com.Ecommerce_app.repositories.UserRepository;
import com.Ecommerce_app.repositories.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public String addToWishlist(
            String email,
            Long productId) {

        User user = userRepository
                .findByUsername(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"));

        Product product = productRepository
                .findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found"));

        boolean exists =
                wishlistRepository
                        .findByUserAndProduct(
                                user,
                                product)
                        .isPresent();

        if (exists) {
            return "Product already in wishlist";
        }

        Wishlist wishlist = new Wishlist();

        wishlist.setUser(user);
        wishlist.setProduct(product);

        wishlistRepository.save(wishlist);

        return "Product added to wishlist";
    }

    public List<WishlistResponse>
    getWishlist(String email) {

        User user = userRepository
                .findByUsername(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"));

        return wishlistRepository
                .findByUser(user)
                .stream()
                .map(item ->
                        new WishlistResponse(
                                item.getId(),
                                item.getProduct().getId(),
                                item.getProduct().getName(),
                                item.getProduct().getPrice()
                        ))
                .toList();
    }

    public String removeFromWishlist(
            String email,
            Long productId) {

        User user = userRepository
                .findByUsername(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"));

        Product product = productRepository
                .findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found"));

        Wishlist wishlist =
                wishlistRepository
                        .findByUserAndProduct(
                                user,
                                product)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Wishlist item not found"));

        wishlistRepository.delete(wishlist);

        return "Product removed from wishlist";
    }
}
