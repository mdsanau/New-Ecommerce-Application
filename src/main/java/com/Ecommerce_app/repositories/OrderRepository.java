package com.Ecommerce_app.repositories;

import com.Ecommerce_app.entities.Order;
import com.Ecommerce_app.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository
        extends JpaRepository<Order, Long> {

    List<Order> findByUser(User user);
    long count();
    @Query("""
       SELECT COALESCE(
       SUM(o.totalAmount),0)
       FROM Order o
       """)
    Double getTotalRevenue();
}
