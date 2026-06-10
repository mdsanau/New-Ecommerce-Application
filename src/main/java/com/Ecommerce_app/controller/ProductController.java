package com.Ecommerce_app.controller;

import com.Ecommerce_app.Dtos.ProductRequest;
import com.Ecommerce_app.Dtos.ProductResponse;
import com.Ecommerce_app.entities.Product;
import com.Ecommerce_app.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Product addProduct(
            @RequestBody ProductRequest request) {

        return service.addProduct(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Product updateProduct(
            @PathVariable Long id,
            @RequestBody ProductRequest request) {
        return service.updateProduct(
                id,
                request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteProduct(
            @PathVariable Long id) {

        service.deleteProduct(id);
    }
    @GetMapping
    public List<Product> getAllProducts() {

        return service.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProduct(
            @PathVariable Long id) {

        return service.getProduct(id);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>>
    searchProducts(

            @RequestParam
            String keyword) {

        return ResponseEntity.ok(
                service
                        .searchProducts(
                                keyword));
    }

    @GetMapping("/category/{category}")
    public List<Product> category(
            @PathVariable String category) {

        return service.getByCategory(
                category);
    }
}
