package com.Ecommerce_app.Dtos;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private Long orderId;
    private Double totalAmount;
    private String status;
    private LocalDateTime orderDate;
}
