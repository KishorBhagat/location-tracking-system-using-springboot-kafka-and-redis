package com.project.location_tracking_system.application.service;

import com.project.location_tracking_system.domain.model.LocationSharePermission;
import com.project.location_tracking_system.domain.model.ShareStatus;
import com.project.location_tracking_system.domain.ports.LocationSharePermissionRepository;
import com.project.location_tracking_system.domain.ports.UserRepository;
import com.project.location_tracking_system.exception.InvalidRequestException;
import com.project.location_tracking_system.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationSharePermissionService {

    final private LocationSharePermissionRepository repository;

    final private UserRepository userRepository;

    public LocationSharePermissionService(LocationSharePermissionRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public LocationSharePermission createPermission(String ownerUserId, String viewerUserId) {

        validateUsersExist(ownerUserId, viewerUserId);

        if(ownerUserId.equals(viewerUserId)) {
            throw new InvalidRequestException("User cannot share location with themselves");
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

        validateUsersExist(ownerUserId, viewerUserId);

        LocationSharePermission existing = repository.findByOwnerAndViewer(ownerUserId, viewerUserId);

        if (existing == null) {
            throw new ResourceNotFoundException("Permission request not found");
        }

        if (existing.status() != ShareStatus.PENDING) {
            throw new InvalidRequestException("Permission is not pending");
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

        validateUsersExist(ownerUserId, viewerUserId);

        LocationSharePermission existing = repository.findByOwnerAndViewer(ownerUserId, viewerUserId);

        if (existing == null) {
            throw new ResourceNotFoundException("Permission request not found");
        }

        if (existing.status() != ShareStatus.PENDING) {
            throw new InvalidRequestException("Permission is not pending");
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

        validateUsersExist(ownerUserId, viewerUserId);

        LocationSharePermission existing = repository.findByOwnerAndViewer(ownerUserId, viewerUserId);

        if(existing == null) {
            throw new ResourceNotFoundException("Permission does not exist");
        }

        if(existing.status() != ShareStatus.ACCEPTED) {
            throw new InvalidRequestException("Only accepted permissions can be revoked");
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

    private void validateUsersExist(String ownerUserId, String viewerUserId) {

        if (!userRepository.existsByUserId(ownerUserId)) {
            throw new ResourceNotFoundException(
                    "Owner not found: " + ownerUserId
            );
        }

        if (!userRepository.existsByUserId(viewerUserId)) {
            throw new ResourceNotFoundException(
                    "Viewer not found: " + viewerUserId
            );
        }
    }
}
