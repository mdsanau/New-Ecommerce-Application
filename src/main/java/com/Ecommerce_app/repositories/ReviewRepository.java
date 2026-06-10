package com.Ecommerce_app.repositories;

import com.Ecommerce_app.entities.Product;
import com.Ecommerce_app.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository
        extends JpaRepository<Review, Long> {

    List<Review> findByProduct(Product product);
}
