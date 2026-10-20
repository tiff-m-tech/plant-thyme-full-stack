package com.plantthyme.plant_thyme_api.dto;

import jakarta.validation.constraints.NotBlank;

//DTOs are shaped around what each action needs, not around the model. One entity can have several DTOs, one for each job:
//
//DTO               Action      Fields
//RegisterRequest	Sign up	    username, email, password
//LoginRequest	    Log in	    username, password
//UserResponse	    Show user	id, username, email

public record LoginRequest(
        @NotBlank String username,
        @NotBlank String password
) {}