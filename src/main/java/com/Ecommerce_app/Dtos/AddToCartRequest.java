package com.Ecommerce_app.Dtos;

import lombok.Data;

@Data
public class AddToCartRequest {

    private Long productId;

    private Integer quantity;
}