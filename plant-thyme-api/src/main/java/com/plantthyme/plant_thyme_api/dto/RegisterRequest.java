package com.plantthyme.plant_thyme_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "Username is required.")
        @Size(min = 3, max = 30, message = "Username must be 3–30 characters.")
        String username,

        @NotBlank(message = "Email is required.")
        @Email(message = "Please enter a valid email.")
        String email,

        @NotBlank(message = "Password is required.")
        @Size(min = 8, max = 72, message = "Password must be 8–72 characters.")
        String password
) {}