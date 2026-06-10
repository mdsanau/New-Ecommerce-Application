package com.Ecommerce_app.repositories;


import com.Ecommerce_app.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository
        extends JpaRepository<OrderItem, Long> {
}
