package com.Ecommerce_app.repositories;

import com.Ecommerce_app.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByRazorpayOrderId(
            String razorpayOrderId);
}
