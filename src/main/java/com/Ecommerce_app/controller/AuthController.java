package com.Ecommerce_app.controller;

import com.Ecommerce_app.Dtos.AuthResponse;
import com.Ecommerce_app.Dtos.LoginRequest;
import com.Ecommerce_app.Dtos.RegisterRequest;
import com.Ecommerce_app.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService service;

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody RegisterRequest request) {

        service.register(request);

        return ResponseEntity.ok("Registered");
    }

    @PostMapping("/login")
    public AuthResponse login(
            @RequestBody LoginRequest request) {

        return service.login(request);
    }
}
