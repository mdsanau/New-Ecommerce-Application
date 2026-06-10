package com.Ecommerce_app.configue;

import com.Ecommerce_app.Dtos.RatingResponse;
import com.Ecommerce_app.Dtos.ReviewRequest;
import com.Ecommerce_app.Dtos.ReviewResponse;
import com.Ecommerce_app.services.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/product/{productId}")
    public ResponseEntity<String>
    addReview(

            @PathVariable Long productId,

            @RequestBody
            ReviewRequest request,

            Authentication authentication) {

        return ResponseEntity.ok(
                reviewService.addReview(
                        authentication.getName(),
                        productId,
                        request));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ReviewResponse>>
    getReviews(
            @PathVariable Long productId) {

        return ResponseEntity.ok(
                reviewService.getProductReviews(
                        productId));
    }

    @GetMapping(
            "/product/{productId}/rating")
    public ResponseEntity<RatingResponse>
    getRating(
            @PathVariable Long productId) {

        return ResponseEntity.ok(
                reviewService.getAverageRating(
                        productId));
    }
}
