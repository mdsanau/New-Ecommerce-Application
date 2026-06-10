package com.Ecommerce_app.services;

import com.Ecommerce_app.Dtos.OrderResponse;
import com.Ecommerce_app.entities.*;
import com.Ecommerce_app.exception.ResourceNotFoundException;
import com.Ecommerce_app.repositories.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private OrderService orderService;

    @Test
    void shouldCheckoutSuccessfully() {

        User user = new User();
        user.setUsername("akram");
        user.setEmail("akram@gmail.com");

        Address address = new Address();
        address.setId(1L);

        Product product = new Product();
        product.setPrice(50000.0);

        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(2);

        Order order = new Order();
        order.setId(1L);
        order.setStatus(OrderStatus.PLACED);
        order.setTotalAmount(100000.0);

        when(userRepository.findByUsername("akram"))
                .thenReturn(Optional.of(user));

        when(addressRepository.findById(1L))
                .thenReturn(Optional.of(address));

        when(cartRepository.findByUser(user))
                .thenReturn(List.of(cartItem));

        when(orderRepository.save(any(Order.class)))
                .thenReturn(order);

        when(orderItemRepository.save(any(OrderItem.class)))
                .thenReturn(new OrderItem());

        OrderResponse response =
                orderService.checkout(
                        "akram",
                        1L);

        assertNotNull(response);

        assertEquals(
                OrderStatus.PLACED.name(),
                response.getStatus());

        verify(orderRepository)
                .save(any(Order.class));

        verify(cartRepository)
                .deleteByUser(user);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {

        when(userRepository.findByUsername(
                "akram"))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> orderService.checkout(
                        "akram",
                        1L));
    }


    @Test
    void shouldThrowExceptionWhenAddressNotFound() {

        User user = new User();

        when(userRepository.findByUsername(
                "akram"))
                .thenReturn(Optional.of(user));

        when(addressRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> orderService.checkout(
                        "akram",
                        1L));
    }

    @Test
    void shouldThrowExceptionWhenCartIsEmpty() {

        User user = new User();

        Address address =
                new Address();

        when(userRepository.findByUsername(
                "akram"))
                .thenReturn(Optional.of(user));

        when(addressRepository.findById(1L))
                .thenReturn(Optional.of(address));

        when(cartRepository.findByUser(user))
                .thenReturn(Collections.emptyList());

        assertThrows(
                RuntimeException.class,
                () -> orderService.checkout(
                        "akram",
                        1L));
    }

    @Test
    void shouldCancelOrder() {

        Order order =
                new Order();

        order.setStatus(
                OrderStatus.PLACED);

        when(orderRepository.findById(1L))
                .thenReturn(Optional.of(order));

        String result =
                orderService.cancelOrder(1L);

        assertEquals(
                "Order cancelled successfully",
                result);

        assertEquals(
                OrderStatus.CANCELLED,
                order.getStatus());
    }

    @Test
    void shouldShipOrder() {

        Order order =
                new Order();

        order.setStatus(
                OrderStatus.PAID);

        when(orderRepository.findById(1L))
                .thenReturn(Optional.of(order));

        String result =
                orderService.shipOrder(1L);

        assertEquals(
                "Order shipped successfully",
                result);

        assertEquals(
                OrderStatus.SHIPPED,
                order.getStatus());
    }

    @Test
    void shouldDeliverOrder() {

        Order order =
                new Order();

        order.setStatus(
                OrderStatus.SHIPPED);

        when(orderRepository.findById(1L))
                .thenReturn(Optional.of(order));

        String result =
                orderService.deliverOrder(1L);

        assertEquals(
                "Order delivered successfully",
                result);

        assertEquals(
                OrderStatus.DELIVERED,
                order.getStatus());
    }

}
