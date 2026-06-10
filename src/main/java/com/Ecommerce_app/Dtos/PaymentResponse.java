package com.Ecommerce_app.Dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {

    private Long paymentId;

    private Double amount;

    private String paymentStatus;

    private Long orderId;
}
