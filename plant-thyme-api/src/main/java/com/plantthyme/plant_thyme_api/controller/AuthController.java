package com.plantthyme.plant_thyme_api.controller;

import com.plantthyme.plant_thyme_api.dto.LoginRequest;
import com.plantthyme.plant_thyme_api.dto.LoginResponse;
import com.plantthyme.plant_thyme_api.dto.RegisterRequest;
import com.plantthyme.plant_thyme_api.dto.UserResponse;
import com.plantthyme.plant_thyme_api.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request) {
        UserResponse newUser = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    // Login is a POST (not a GET) because GET requests put data in the URL, where passwords could end up in browser history and server logs.
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}