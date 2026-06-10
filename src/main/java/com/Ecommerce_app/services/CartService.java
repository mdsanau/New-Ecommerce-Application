package com.Ecommerce_app.services;

import com.Ecommerce_app.Dtos.AddToCartRequest;
import com.Ecommerce_app.entities.CartItem;
import com.Ecommerce_app.entities.Product;
import com.Ecommerce_app.entities.User;
import com.Ecommerce_app.exception.ResourceNotFoundException;
import com.Ecommerce_app.repositories.CartRepository;
import com.Ecommerce_app.repositories.ProductRepository;
import com.Ecommerce_app.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    public CartItem addToCart(
            String email,
            AddToCartRequest request) {

        System.out.println("EMAIL RECEIVED = " + email);
        System.out.println(
                userRepository.findByUsername(email)
        );
        User user =
                userRepository.findByUsername(email)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User Not Found"));

        Product product =
                productRepository.findById(
                                request.getProductId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Product Not Found"));

        CartItem item = new CartItem();

        item.setUser(user);
        item.setProduct(product);
        item.setQuantity(
                request.getQuantity());

        return cartRepository.save(item);
    }

    public List<CartItem> getCart(
            String email) {

        User user =
                userRepository.findByUsername(email)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User Not Found"));

        return cartRepository.findByUser(user);
    }

    public CartItem updateQuantity(
            Long cartId,
            Integer quantity) {

        CartItem item =
                cartRepository.findById(cartId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Cart item not found"));

        item.setQuantity(quantity);

        return cartRepository.save(item);
    }

    public void removeFromCart(
            Long cartId) {

        cartRepository.deleteById(cartId);
    }
}
