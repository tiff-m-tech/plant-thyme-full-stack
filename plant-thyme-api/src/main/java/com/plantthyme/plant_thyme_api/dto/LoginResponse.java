package com.plantthyme.plant_thyme_api.dto;

// Returned after a successful login.
// token = the JWT that React sends with future requests
// user  = safe user info (no password hash) for displaying who's logged in
public record LoginResponse(String token, UserResponse user) {}