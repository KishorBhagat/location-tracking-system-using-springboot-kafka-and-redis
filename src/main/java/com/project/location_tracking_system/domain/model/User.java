package com.project.location_tracking_system.domain.model;

public record User(
        Long id,
        String userId,
        String username,
        String email,
        String password
) {
}