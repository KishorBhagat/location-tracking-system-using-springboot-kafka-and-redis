package com.project.location_tracking_system.api.dto;

public record LocationSharePermissionRequest(
        String ownerUserId,
        String viewerUserId
) {
}
