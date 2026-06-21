package com.project.location_tracking_system.api.dto;

public record LocationResponse(
        String userId,
        Double latitude,
        Double longitude,
        Long timestamp
) {
}