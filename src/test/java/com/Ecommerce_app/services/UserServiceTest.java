package com.Ecommerce_app.services;


import com.Ecommerce_app.Dtos.AuthResponse;
import com.Ecommerce_app.Dtos.LoginRequest;
import com.Ecommerce_app.Dtos.RegisterRequest;
import com.Ecommerce_app.entities.User;
import com.Ecommerce_app.exception.BadRequestException;
import com.Ecommerce_app.exception.ResourceNotFoundException;
import com.Ecommerce_app.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldRegisterUserSuccessfully() {

        RegisterRequest request =
                new RegisterRequest();

        request.setUsername("akram");
        request.setEmail("inamazam2@gmail.com");
        request.setPassword("akram123");

        when(passwordEncoder.encode("akram123"))
                .thenReturn("encodedPassword");

        User savedUser = new User();
        savedUser.setId(1L);

        when(userRepository.save(any(User.class)))
                .thenReturn(savedUser);

        userService.register(request);

        verify(userRepository, times(1))
                .save(any(User.class));
    }
    @Test
    void shouldLoginSuccessfully() {

        LoginRequest request =
                new LoginRequest();

        request.setEmail("john@gmail.com");
        request.setPassword("123456");

        User user = new User();

        user.setEmail("john@gmail.com");
        user.setPassword("encoded");

        when(userRepository.findByEmail(
                "john@gmail.com"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(
                "123456",
                "encoded"))
                .thenReturn(true);

        when(jwtService.generateToken(
                "john@gmail.com"))
                .thenReturn("jwt-token");

        AuthResponse response =
                userService.login(request);

        assertEquals(
                "jwt-token",
                response.getToken());
    }
    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {

        RegisterRequest request = new RegisterRequest();
        request.setEmail("akram@gmail.com");

        User existingUser = new User();

        when(userRepository.findByEmail(request.getEmail()))
                .thenReturn(Optional.of(existingUser));

        BadRequestException exception =
                assertThrows(
                        BadRequestException.class,
                        () -> userService.register(request));

        assertEquals(
                "Email already exists",
                exception.getMessage());
    }
    @Test
    void shouldThrowExceptionWhenUserNotFound() {

        LoginRequest request = new LoginRequest();

        request.setEmail("akram@gmail.com");

        when(userRepository.findByEmail(
                request.getEmail()))
                .thenReturn(Optional.empty());

        ResourceNotFoundException exception =
                assertThrows(
                        ResourceNotFoundException.class,
                        () -> userService.login(request));

        assertEquals(
                "User not found",
                exception.getMessage());
    }
    @Test
    void shouldThrowExceptionForInvalidCredentials() {

        LoginRequest request = new LoginRequest();

        request.setEmail("akram@gmail.com");
        request.setPassword("wrong-password");

        User user = new User();

        user.setEmail("akram@gmail.com");
        user.setPassword("encodedPassword");

        when(userRepository.findByEmail(
                request.getEmail()))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(
                "wrong-password",
                "encodedPassword"))
                .thenReturn(false);

        BadRequestException exception =
                assertThrows(
                        BadRequestException.class,
                        () -> userService.login(request));

        assertEquals(
                "Invalid credentials",
                exception.getMessage());
    }
}
