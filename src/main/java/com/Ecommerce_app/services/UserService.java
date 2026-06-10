package com.Ecommerce_app.services;

import com.Ecommerce_app.Dtos.AuthResponse;
import com.Ecommerce_app.Dtos.LoginRequest;
import com.Ecommerce_app.Dtos.RegisterRequest;
import com.Ecommerce_app.entities.User;
import com.Ecommerce_app.exception.BadRequestException;
import com.Ecommerce_app.exception.ResourceNotFoundException;
import com.Ecommerce_app.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    public void register(RegisterRequest request) {

        if(repository.findByEmail(request.getEmail())
                .isPresent()) {

            throw new BadRequestException("Email already exists");
        }

        User user = new User();

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());

        user.setPassword(
                encoder.encode(request.getPassword()));

        user.setRole("USER");

        repository.save(user);
    }
    public AuthResponse login(LoginRequest request) {

        User user = repository.findByEmail(
                        request.getEmail())
                .orElseThrow(()->
        new ResourceNotFoundException(
                "User not found"));

        if(!encoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new BadRequestException(
                    "Invalid credentials");
        }

        String token =
                jwtService.generateToken(
                        user.getEmail());

        return new AuthResponse(token);
    }
}
