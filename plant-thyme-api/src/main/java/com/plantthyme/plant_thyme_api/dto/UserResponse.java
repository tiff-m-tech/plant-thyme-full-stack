package com.plantthyme.plant_thyme_api.dto;

import com.plantthyme.plant_thyme_api.model.User;

public record UserResponse(Long id, String username, String email) {
    public static UserResponse fromEntity(User user) {
        return new UserResponse(user.getId(), user.getUsername(), user.getEmail());
    }
}
