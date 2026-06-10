package com.Ecommerce_app.Dtos;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {

    private Long id;

    private String name;

    private Double price;

    private String category;

    private String imageUrl;
}
