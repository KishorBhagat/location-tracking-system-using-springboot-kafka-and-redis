package com.project.location_tracking_system.api.dto;

public record PermissionActionRequest(
        String ownerUserId,
        String viewerUserId
) {
}