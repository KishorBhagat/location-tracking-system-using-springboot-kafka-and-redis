package com.project.location_tracking_system.domain.model;

public record Location(
        String userId,
        Double latitude,
        Double longitude,
        Long timestamp
) {
}