package com.Ecommerce_app.Dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WishlistResponse {

    private Long wishlistId;

    private Long productId;

    private String productName;

    private Double price;
}
