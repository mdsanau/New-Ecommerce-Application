package com.Ecommerce_app.Dtos;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewResponse {

    private String username;

    private Integer rating;

    private String comment;
}
