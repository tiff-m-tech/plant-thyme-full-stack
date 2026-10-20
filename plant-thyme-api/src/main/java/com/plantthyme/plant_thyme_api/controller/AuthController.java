package com.plantthyme.plant_thyme_api.controller;

import com.plantthyme.plant_thyme_api.dto.LoginRequest;
import com.plantthyme.plant_thyme_api.dto.LoginResponse;
import com.plantthyme.plant_thyme_api.dto.RegisterRequest;
import com.plantthyme.plant_thyme_api.dto.UserResponse;
import com.plantthyme.plant_thyme_api.model.User;
import com.plantthyme.plant_thyme_api.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    // Returns the currently logged-in user, based on the JWT in the request.
    // Used by the front end to check if a saved token is still valid
    // (e.g. after a page refresh) and to get who's logged in.
    @GetMapping("/me")
    public UserResponse me(@AuthenticationPrincipal User user) {
        return UserResponse.fromEntity(user);
    }
}