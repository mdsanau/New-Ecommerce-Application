package com.Ecommerce_app.services;

import com.Ecommerce_app.entities.Product;
import com.Ecommerce_app.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AiChatService {

    private final ProductRepository productRepository;

    public String chat(String message) {

        String query =
                message.toLowerCase();

        if(query.contains("samsung")) {

            List<Product> products =
                    productRepository
                            .findByNameContainingIgnoreCase(
                                    "samsung");

            return products.stream()
                    .map(Product::getName)
                    .collect(Collectors.joining(", "));
        }

        return "Sorry, I couldn't understand.";
    }
}
