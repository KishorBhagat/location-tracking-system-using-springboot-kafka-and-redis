package com.project.location_tracking_system.domain.model;

public record LocationSharePermission (
        Long id,
        String ownerUserId,
        String viewerUserId,
        ShareStatus status
) {
}
