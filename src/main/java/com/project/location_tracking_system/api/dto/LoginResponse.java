package com.project.location_tracking_system.api.dto;

public record LoginResponse(

        String userId,
        String username,
        String token
) {
}