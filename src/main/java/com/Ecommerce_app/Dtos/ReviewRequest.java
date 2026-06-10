package com.Ecommerce_app.Dtos;

import lombok.Data;

@Data
public class ReviewRequest {

    private Integer rating;

    private String comment;
}
