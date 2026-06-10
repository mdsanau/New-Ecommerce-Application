package com.Ecommerce_app.controller;

import com.Ecommerce_app.Dtos.DashboardResponse;
import com.Ecommerce_app.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService
            adminService;

    @GetMapping("/dashboard")
    public DashboardResponse
    dashboard() {

        return adminService
                .getDashboard();
    }
}
