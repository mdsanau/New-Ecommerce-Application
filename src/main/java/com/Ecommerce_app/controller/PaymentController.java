package com.Ecommerce_app.controller;

import com.Ecommerce_app.Dtos.PaymentResponse;
import com.Ecommerce_app.Dtos.PaymentVerificationRequest;
import com.Ecommerce_app.entities.Payment;
import com.Ecommerce_app.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/{orderId}")
    public PaymentResponse createPayment(
            @PathVariable Long orderId) {

        return paymentService
                .createPayment(orderId);
    }
    @PostMapping("/{paymentId}/success")
    public ResponseEntity<String>
    paymentSuccess(

            @PathVariable
            Long paymentId) {

        return ResponseEntity.ok(
                paymentService
                        .paymentSuccess(
                                paymentId));
    }
    @PostMapping(
            "/create-order/{orderId}")
    public ResponseEntity<?> createOrder(
            @PathVariable Long orderId)
            throws Exception {

        return ResponseEntity.ok(
                paymentService
                        .createRazorpayOrder(
                                orderId));
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyPayment(
            @RequestBody
            PaymentVerificationRequest request)
            throws Exception {

        return ResponseEntity.ok(
                paymentService
                        .verifyPayment(request));
    }
}
