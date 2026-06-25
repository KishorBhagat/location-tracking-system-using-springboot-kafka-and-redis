package com.project.location_tracking_system.domain.ports;

import com.project.location_tracking_system.domain.model.LocationSharePermission;
import com.project.location_tracking_system.domain.model.ShareStatus;

import java.util.List;

public interface LocationSharePermissionRepository {

    LocationSharePermission save(LocationSharePermission permission);

    List<LocationSharePermission> findByOwnerUserId(String ownerUserId);

    boolean exists(String ownerUserId, String viewerUserId);

    LocationSharePermission findByOwnerAndViewer(String ownerUserId, String viewerUserId);

    List<LocationSharePermission> findByOwnerAndStatus(String ownerUserId, ShareStatus status);
}
