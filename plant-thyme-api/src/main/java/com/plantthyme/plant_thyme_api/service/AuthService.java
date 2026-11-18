package com.plantthyme.plant_thyme_api.service;

import com.plantthyme.plant_thyme_api.dto.LoginRequest;
import com.plantthyme.plant_thyme_api.dto.LoginResponse;
import com.plantthyme.plant_thyme_api.dto.RegisterRequest;
import com.plantthyme.plant_thyme_api.dto.UserResponse;
import com.plantthyme.plant_thyme_api.model.User;
import com.plantthyme.plant_thyme_api.repository.UserRepository;
import com.plantthyme.plant_thyme_api.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public UserResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username is already taken.");
        }
        if (userRepository.existsByEmail(request.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email is already registered.");
        }

        User user = new User();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPasswordHash(passwordEncoder.encode(request.password()));

        User savedUser = userRepository.save(user);
        return UserResponse.fromEntity(savedUser);
    }

    // Why the same message for both failures? If you said “username not found” vs. “wrong password,” an attacker could use your
    // login form to figure out which usernames exist. The same vague message for both gives nothing away.
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "Invalid username or password."));

        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Invalid username or password.");
        }

        String token = jwtService.generateToken(user.getUsername());
        return new LoginResponse(token, UserResponse.fromEntity(user));
    }
}