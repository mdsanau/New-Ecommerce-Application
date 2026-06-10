package com.Ecommerce_app.services;

import com.Ecommerce_app.Dtos.RatingResponse;
import com.Ecommerce_app.Dtos.ReviewRequest;
import com.Ecommerce_app.Dtos.ReviewResponse;
import com.Ecommerce_app.entities.Product;
import com.Ecommerce_app.entities.Review;
import com.Ecommerce_app.entities.User;
import com.Ecommerce_app.exception.ResourceNotFoundException;
import com.Ecommerce_app.repositories.ProductRepository;
import com.Ecommerce_app.repositories.ReviewRepository;
import com.Ecommerce_app.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public String addReview(
            String email,
            Long productId,
            ReviewRequest request) {

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

        Review review = new Review();

        review.setUser(user);
        review.setProduct(product);
        review.setRating(request.getRating());
        review.setComment(request.getComment());
        review.setReviewDate(LocalDateTime.now());

        reviewRepository.save(review);

        return "Review added successfully";
    }

    public List<ReviewResponse>
    getProductReviews(Long productId) {

        Product product =
                productRepository
                        .findById(productId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Product not found"));

        return reviewRepository
                .findByProduct(product)
                .stream()
                .map(review ->
                        new ReviewResponse(
                                review.getUser().getUsername(),
                                review.getRating(),
                                review.getComment()
                        ))
                .toList();
    }

    public RatingResponse
    getAverageRating(Long productId) {

        Product product =
                productRepository
                        .findById(productId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Product not found"));

        List<Review> reviews =
                reviewRepository
                        .findByProduct(product);

        double avg =
                reviews.stream()
                        .mapToInt(Review::getRating)
                        .average()
                        .orElse(0.0);

        return new RatingResponse(
                productId,
                avg);
    }
}
