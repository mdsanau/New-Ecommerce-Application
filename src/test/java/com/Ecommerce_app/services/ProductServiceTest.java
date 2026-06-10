package com.Ecommerce_app.services;

import com.Ecommerce_app.Dtos.ProductRequest;
import com.Ecommerce_app.Dtos.ProductResponse;
import com.Ecommerce_app.entities.Product;
import com.Ecommerce_app.exception.ResourceNotFoundException;
import com.Ecommerce_app.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {


    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;


    @Test
    void shouldAddProductSuccessfully() {

        ProductRequest request = new ProductRequest();

        request.setName("iPhone 16");
        request.setDescription("Apple Mobile");
        request.setPrice(80000.0);
        request.setStock(10);
        request.setCategory("Mobile");

        Product savedProduct = new Product();

        savedProduct.setId(1L);
        savedProduct.setName("iPhone 16");

        when(productRepository.save(any(Product.class)))
                .thenReturn(savedProduct);

        Product result =
                productService.addProduct(request);

        assertNotNull(result);

        assertEquals(
                "iPhone 16",
                result.getName());

        verify(productRepository)
                .save(any(Product.class));
    }


    @Test
    void shouldUpdateProductSuccessfully() {

        Product existing = new Product();

        existing.setId(1L);
        existing.setName("Old Product");

        ProductRequest request =
                new ProductRequest();

        request.setName("New Product");

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(existing));

        when(productRepository.save(any(Product.class)))
                .thenReturn(existing);

        Product result =
                productService.updateProduct(
                        1L,
                        request);

        assertEquals(
                "New Product",
                result.getName());
    }

    @Test
    void shouldThrowExceptionWhenUpdatingMissingProduct() {

        when(productRepository.findById(1L))
                .thenReturn(Optional.empty());

        ProductRequest request =
                new ProductRequest();

        ResourceNotFoundException ex =
                assertThrows(
                        ResourceNotFoundException.class,
                        () -> productService.updateProduct(
                                1L,
                                request));

        assertEquals(
                "Product not found",
                ex.getMessage());
    }

    @Test
    void shouldGetProductById() {

        Product product =
                new Product();

        product.setId(1L);

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        Product result =
                productService.getProduct(1L);

        assertNotNull(result);

        assertEquals(
                1L,
                result.getId());
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {

        when(productRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> productService.getProduct(1L));
    }

    @Test
    void shouldDeleteProduct() {

        productService.deleteProduct(1L);

        verify(productRepository)
                .deleteById(1L);
    }

    @Test
    void shouldReturnAllProducts() {

        List<Product> products =
                List.of(
                        new Product(),
                        new Product());

        when(productRepository.findAll())
                .thenReturn(products);

        List<Product> result =
                productService.getAllProducts();

        assertEquals(
                2,
                result.size());
    }

    @Test
    void shouldSearchProducts() {

        Product product =
                new Product();

        product.setId(1L);
        product.setName("iPhone");
        product.setPrice(80000.0);
        product.setCategory("Mobile");

        when(productRepository
                .findByNameContainingIgnoreCase("iphone"))
                .thenReturn(List.of(product));

        List<ProductResponse> result =
                productService.searchProducts(
                        "iphone");

        assertEquals(
                1,
                result.size());

        assertEquals(
                "iPhone",
                result.get(0).getName());
    }

    @Test
    void shouldReturnProductsByCategory() {

        List<Product> products =
                List.of(new Product());

        when(productRepository.findByCategory("Mobile"))
                .thenReturn(products);

        List<Product> result =
                productService.getByCategory(
                        "Mobile");

        assertEquals(
                1,
                result.size());
    }
}
