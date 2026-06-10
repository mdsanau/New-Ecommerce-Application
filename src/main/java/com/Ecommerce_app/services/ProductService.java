package com.Ecommerce_app.services;

import com.Ecommerce_app.Dtos.ProductRequest;
import com.Ecommerce_app.Dtos.ProductResponse;
import com.Ecommerce_app.entities.Product;
import com.Ecommerce_app.exception.ResourceNotFoundException;
import com.Ecommerce_app.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    public Product addProduct(ProductRequest request) {

        Product product = new Product();

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setCategory(request.getCategory());
        product.setImageUrl(request.getImageUrl());

        return repository.save(product);
    }

    public Product updateProduct(
            Long id,
            ProductRequest request) {

        Product existing =
                repository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Product not found"));

        existing.setName(request.getName());
        existing.setDescription(
                request.getDescription());

        existing.setPrice(
                request.getPrice());

        existing.setStock(
                request.getStock());

        existing.setCategory(
                request.getCategory());

        existing.setImageUrl(
                request.getImageUrl());

        return repository.save(existing);
    }

    public void deleteProduct(Long id) {

        repository.deleteById(id);
    }

    public List<Product> getAllProducts() {
        return repository.findAll();
    }


    public Product getProduct(Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found"));
    }

    public List<ProductResponse> searchProducts(
            String keyword) {

        return repository
                .findByNameContainingIgnoreCase(
                        keyword)
                .stream()
                .map(product ->
                        new ProductResponse(
                                product.getId(),
                                product.getName(),
                                product.getPrice(),
                                product.getCategory(),
                                product.getImageUrl()
                        ))
                .toList();
    }

    public List<Product> getByCategory(
            String category) {

        return repository
                .findByCategory(category);
    }


}