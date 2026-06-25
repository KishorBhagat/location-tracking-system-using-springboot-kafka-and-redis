package com.project.location_tracking_system.application.service;

import com.project.location_tracking_system.domain.model.LocationSharePermission;
import com.project.location_tracking_system.domain.model.ShareStatus;
import com.project.location_tracking_system.domain.ports.LocationSharePermissionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationSharePermissionService {

    private LocationSharePermissionRepository repository;

    public LocationSharePermissionService(LocationSharePermissionRepository repository) {
        this.repository = repository;
    }

    public LocationSharePermission createPermission(String ownerUserId, String viewerUserId) {

        if(ownerUserId.equals(viewerUserId)) {
            throw new IllegalArgumentException("User cannot share location with themselves");
        }

        LocationSharePermission existing = repository.findByOwnerAndViewer(ownerUserId, viewerUserId);

        if(existing != null) {
            if (existing.status() == ShareStatus.PENDING) {
                return existing;
            }
            if (existing.status() == ShareStatus.ACCEPTED) {
                return existing;
            }
            if(existing.status() == ShareStatus.REJECTED || existing.status() == ShareStatus.REVOKED) {
                return repository.save(
                        new LocationSharePermission(
                                existing.id(),
                                ownerUserId,
                                viewerUserId,
                                ShareStatus.PENDING
                        )
                );
            }
            return existing;
        }

        return repository.save(
                new LocationSharePermission(
                        null,
                        ownerUserId,
                        viewerUserId,
                        ShareStatus.PENDING
                )
        );
    }

    public List<LocationSharePermission> getPermissions(String ownerUserId) {
        return repository.findByOwnerUserId(ownerUserId);
    }

    public LocationSharePermission acceptPermission(String ownerUserId, String viewerUserId) {

        LocationSharePermission existing = repository.findByOwnerAndViewer(ownerUserId, viewerUserId);

        if (existing == null) {
            throw new IllegalArgumentException("Permission request not found");
        }

        if (existing.status() != ShareStatus.PENDING) {
            throw new IllegalArgumentException("Permission is not pending");
        }

        return repository.save(
                new LocationSharePermission(
                        existing.id(),
                        ownerUserId,
                        viewerUserId,
                        ShareStatus.ACCEPTED
                )
        );
    }

    public LocationSharePermission rejectPermission(String ownerUserId, String viewerUserId) {

        LocationSharePermission existing = repository.findByOwnerAndViewer(ownerUserId, viewerUserId);

        if (existing == null) {
            throw new IllegalArgumentException("Permission request not found");
        }

        if (existing.status() != ShareStatus.PENDING) {
            throw new IllegalArgumentException("Permission is not pending");
        }

        return repository.save(
                new LocationSharePermission(
                        existing.id(),
                        ownerUserId,
                        viewerUserId,
                        ShareStatus.REJECTED
                )
        );
    }

    public LocationSharePermission revokePermission(String ownerUserId, String viewerUserId) {

        LocationSharePermission existing = repository.findByOwnerAndViewer(ownerUserId, viewerUserId);

        if(existing == null) {
            throw new IllegalArgumentException("Permission does not exist");
        }

        if(existing.status() != ShareStatus.ACCEPTED) {
            throw new IllegalArgumentException("Only accepted permissions can be revoked");
        }

        return repository.save(
                new LocationSharePermission(
                        existing.id(),
                        ownerUserId,
                        viewerUserId,
                        ShareStatus.REVOKED
                )
        );
    }

    public List<LocationSharePermission> getPendingRequests(String ownerUserId) {

        return repository.findByOwnerAndStatus(
                ownerUserId,
                ShareStatus.PENDING
        );
    }
}
