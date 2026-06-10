package com.Ecommerce_app.services;

import com.Ecommerce_app.Dtos.DashboardResponse;
import com.Ecommerce_app.repositories.OrderRepository;
import com.Ecommerce_app.repositories.ProductRepository;
import com.Ecommerce_app.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;

    private final ProductRepository
            productRepository;

    private final OrderRepository
            orderRepository;

    public DashboardResponse
    getDashboard() {

        return new DashboardResponse(

                userRepository.count(),

                productRepository.count(),

                orderRepository.count(),

                orderRepository
                        .getTotalRevenue()
        );
    }
}
