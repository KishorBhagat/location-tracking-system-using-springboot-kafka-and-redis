package com.project.location_tracking_system.domain.event;

public record LocationUpdatedEvent(
    String userId,
    Double latitude,
    Double longitude,
    Long timestamp
) {
}
