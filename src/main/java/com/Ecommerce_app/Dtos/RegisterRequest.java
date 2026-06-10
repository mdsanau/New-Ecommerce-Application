package com.Ecommerce_app.Dtos;

import lombok.Data;

@Data
public class RegisterRequest {

    private String username;
    private String email;
    private String password;

    // getters setters
}
