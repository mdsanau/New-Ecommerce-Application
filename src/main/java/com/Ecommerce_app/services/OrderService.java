package com.Ecommerce_app.services;

import com.Ecommerce_app.Dtos.OrderResponse;
import com.Ecommerce_app.entities.*;
import com.Ecommerce_app.exception.BadRequestException;
import com.Ecommerce_app.exception.ResourceNotFoundException;
import com.Ecommerce_app.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final AddressRepository addressRepository;
    private final EmailService emailService;


    @Transactional
    public OrderResponse checkout(
            String email,
            Long addressId) {

        User user = userRepository
                .findByUsername(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"));
        Address address =
                addressRepository
                        .findById(addressId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Address not found"));

        List<CartItem> cartItems =
                cartRepository.findByUser(user);

        if (cartItems.isEmpty()) {
            throw new BadRequestException(
                    "Cart is empty");
        }

        double totalAmount = 0;

        for (CartItem cartItem : cartItems) {

            totalAmount +=
                    cartItem.getProduct().getPrice()
                            * cartItem.getQuantity();
        }

        Order order = new Order();

        order.setUser(user);
        order.setStatus(OrderStatus.PLACED);
        order.setOrderDate(LocalDateTime.now());
        order.setTotalAmount(totalAmount);
        order.setAddress(address);

        Order savedOrder =
                orderRepository.save(order);

        List<OrderItem> orderItems =
                new ArrayList<>();

        for (CartItem cartItem : cartItems) {

            OrderItem orderItem =
                    new OrderItem();

            orderItem.setOrder(savedOrder);

            orderItem.setProduct(
                    cartItem.getProduct());

            orderItem.setQuantity(
                    cartItem.getQuantity());

            orderItem.setPrice(
                    cartItem.getProduct()
                            .getPrice());

            orderItems.add(
                    orderItemRepository
                            .save(orderItem));
        }

        savedOrder.setOrderItems(orderItems);

        cartRepository.deleteByUser(user);
        emailService.sendEmail(

                order.getUser().getEmail(),

                "Order Delivered",

                "Your order #" +
                        order.getId() +
                        " has been delivered."
        );

        return new OrderResponse(
                savedOrder.getId(),
                savedOrder.getTotalAmount(),
                savedOrder.getStatus().name(),
                savedOrder.getOrderDate()
        );
    }

    public List<Order> getOrders(
            String email) {


        User user = userRepository
                .findByUsername(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"));

        return orderRepository.findByUser(user);
    }

    public Order getOrderDetails(
            Long orderId) {

        return orderRepository
                .findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Order not found"));
    }
    @Transactional
    public String cancelOrder(Long orderId) {

        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Order not found"));

        if (order.getStatus() ==
                OrderStatus.CANCELLED) {

            throw new BadRequestException(
                    "Order already cancelled");
        }

        order.setStatus(
                OrderStatus.CANCELLED);

        orderRepository.save(order);

        return "Order cancelled successfully";
    }

    @Transactional
    public String updateOrderStatus(
            Long orderId,
            String status) {

        Order order = orderRepository
                .findById(orderId).
        orElseThrow(() ->
                new ResourceNotFoundException(
                        "Order not found"));

        order.setStatus(
                OrderStatus.valueOf(status));

        orderRepository.save(order);

        return "Order status updated successfully";
    }

    @Transactional
    public String shipOrder(Long orderId) {

        Order order =
                orderRepository
                        .findById(orderId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Order Not Found"));

        if (order.getStatus()
                != OrderStatus.PAID) {

            throw new BadRequestException(
                    "Only PAID orders can be shipped");
        }

        order.setStatus(
                OrderStatus.SHIPPED);

        orderRepository.save(order);

        return "Order shipped successfully";
    }

    @Transactional
    public String deliverOrder(Long orderId) {

        Order order =
                orderRepository
                        .findById(orderId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Order Not Found"));

        if (order.getStatus()
                != OrderStatus.SHIPPED) {

            throw new BadRequestException(
                    "Only SHIPPED orders can be delivered");
        }

        order.setStatus(
                OrderStatus.DELIVERED);

        orderRepository.save(order);

        return "Order delivered successfully";
    }


}