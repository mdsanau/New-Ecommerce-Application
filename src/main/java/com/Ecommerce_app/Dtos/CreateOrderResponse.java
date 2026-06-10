package com.Ecommerce_app.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateOrderResponse {

    private String razorpayOrderId;

    private Long orderId;

    private Double amount;

    private String currency;
}
