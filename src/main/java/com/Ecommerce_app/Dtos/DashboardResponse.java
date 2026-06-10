package com.Ecommerce_app.Dtos;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DashboardResponse {

    private Long totalUsers;

    private Long totalProducts;

    private Long totalOrders;

    private Double totalRevenue;
}
