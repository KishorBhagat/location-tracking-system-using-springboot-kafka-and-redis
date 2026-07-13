package com.project.location_tracking_system.api.dto;

public record UserResponse(
        String userId,
        String username,
        String email
) {
}