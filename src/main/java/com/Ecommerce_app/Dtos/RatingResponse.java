package com.Ecommerce_app.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RatingResponse {

    private Long productId;

    private Double averageRating;
}
