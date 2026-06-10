package com.Ecommerce_app.services;

import com.Ecommerce_app.Dtos.CreateOrderResponse;
import com.Ecommerce_app.Dtos.PaymentResponse;
import com.Ecommerce_app.Dtos.PaymentVerificationRequest;
import com.Ecommerce_app.entities.Order;
import com.Ecommerce_app.entities.OrderStatus;
import com.Ecommerce_app.entities.Payment;
import com.Ecommerce_app.entities.PaymentStatus;
import com.Ecommerce_app.exception.ResourceNotFoundException;
import com.Ecommerce_app.repositories.OrderRepository;
import com.Ecommerce_app.repositories.PaymentRepository;
import com.razorpay.RazorpayClient;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Hex;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final RazorpayClient razorpayClient;
    @Value("${razorpay.key.secret}")
    private String razorpaySecret;


    public PaymentResponse createPayment(
            Long orderId) {

        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Order Not Found"));

        Payment payment = new Payment();

        payment.setOrder(order);
        payment.setAmount(order.getTotalAmount());
        payment.setStatus(PaymentStatus.PENDING);
        payment.setPaymentDate(LocalDateTime.now());

        Payment savedPayment =
                paymentRepository.save(payment);

        return new PaymentResponse(
                savedPayment.getId(),
                savedPayment.getAmount(),
                savedPayment.getStatus().name(),
                order.getId()
        );
    }

    @Transactional
    public String paymentSuccess(Long paymentId) {

        Payment payment =
                paymentRepository
                        .findById(paymentId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Payment not found"));

        payment.setStatus(
                PaymentStatus.SUCCESS);

        payment.setPaymentDate(
                LocalDateTime.now());

        Order order =
                payment.getOrder();

        order.setStatus(
                OrderStatus.PROCESSING);

        paymentRepository.save(payment);
        orderRepository.save(order);

        return "Payment completed successfully";
    }

    @Transactional
    public CreateOrderResponse createRazorpayOrder(
            Long orderId) throws Exception {

        Order order =
                orderRepository.findById(orderId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Order Not Found"));

        JSONObject options =
                new JSONObject();

        options.put(
                "amount",
                (int)(order.getTotalAmount() * 100));

        options.put(
                "currency",
                "INR");

        options.put(
                "receipt",
                "order_" + orderId);

        com.razorpay.Order razorpayOrder =
                razorpayClient.orders.create(options);

        Payment payment =
                new Payment();

        payment.setOrder(order);

        payment.setAmount(
                order.getTotalAmount());

        payment.setStatus(
                PaymentStatus.PENDING);

        payment.setRazorpayOrderId(
                razorpayOrder.get("id"));

        paymentRepository.save(payment);

        return new CreateOrderResponse(
                razorpayOrder.get("id"),
                order.getId(),
                order.getTotalAmount(),
                "INR"
        );
    }

    @Transactional
    public String verifyPayment(
            PaymentVerificationRequest request)
            throws Exception {

        String payload =
                request.getRazorpayOrderId()
                        + "|"
                        + request.getRazorpayPaymentId();

        boolean valid =
                verifySignature(
                        payload,
                        request.getRazorpaySignature());

        if (!valid) {

            throw new RuntimeException(
                    "Invalid Signature");
        }

        Payment payment =
                paymentRepository
                        .findByRazorpayOrderId(
                                request.getRazorpayOrderId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Payment not found for Razorpay Order ID:"));

        payment.setPaymentId(
                request.getRazorpayPaymentId());

        payment.setStatus(
                PaymentStatus.SUCCESS);

        payment.setPaymentDate(
                LocalDateTime.now());

        paymentRepository.save(payment);

        Order order =
                payment.getOrder();

        order.setStatus(
                OrderStatus.PAID);

        orderRepository.save(order);

        return "Payment verified successfully";
    }
    private boolean verifySignature(
            String payload,
            String actualSignature)
            throws Exception {

        SecretKeySpec secretKey =
                new SecretKeySpec(
                        razorpaySecret.getBytes(),
                        "HmacSHA256");

        Mac mac =
                Mac.getInstance(
                        "HmacSHA256");

        mac.init(secretKey);

        byte[] hash =
                mac.doFinal(
                        payload.getBytes());

        String generatedSignature =
                Hex.encodeHexString(hash);

        return generatedSignature
                .equals(actualSignature);
    }

}
