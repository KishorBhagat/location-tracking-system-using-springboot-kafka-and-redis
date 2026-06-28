package com.project.location_tracking_system.infrastructure.persistence;

import com.project.location_tracking_system.domain.model.LocationSharePermission;
import com.project.location_tracking_system.domain.model.ShareStatus;
import com.project.location_tracking_system.domain.ports.LocationSharePermissionRepository;
import com.project.location_tracking_system.infrastructure.persistence.entity.LocationSharePermissionEntity;
import com.project.location_tracking_system.infrastructure.persistence.repository.JpaLocationSharePermissionRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MysqlLocationSharePermissionRepository implements LocationSharePermissionRepository {

    private final JpaLocationSharePermissionRepository repository;

    public MysqlLocationSharePermissionRepository(JpaLocationSharePermissionRepository repository) {
        this.repository = repository;
    }

    @Override
    public LocationSharePermission save(LocationSharePermission permission) {
        LocationSharePermissionEntity entity = permission.id() != null
                        ? repository.findById(permission.id()).orElse(new LocationSharePermissionEntity())
                        : new LocationSharePermissionEntity();

        entity.setId(permission.id());

        entity.setOwnerUserId(permission.ownerUserId());
        entity.setViewerUserId(permission.viewerUserId());
        entity.setStatus(permission.status());

        entity = repository.save(entity);

        return new LocationSharePermission(
                entity.getId(),
                entity.getOwnerUserId(),
                entity.getViewerUserId(),
                entity.getStatus()
        );
    }

    @Override
    public List<LocationSharePermission> findByOwnerUserId(String ownerUserId) {
        return repository.findByOwnerUserId(ownerUserId)
                .stream()
                .map(entity ->
                        new LocationSharePermission(
                                entity.getId(),
                                entity.getOwnerUserId(),
                                entity.getViewerUserId(),
                                entity.getStatus()
                        )
                ).toList();
    }

    @Override
    public boolean exists(String ownerUserId, String viewerUserId) {
        return repository.findByOwnerUserIdAndViewerUserId(ownerUserId, viewerUserId).isPresent();
    }

    @Override
    public LocationSharePermission findByOwnerAndViewer(String ownerUserId, String viewerUserId) {
        return repository.findByOwnerUserIdAndViewerUserId(ownerUserId, viewerUserId)
                .map(entity ->
                        new LocationSharePermission(
                                entity.getId(),
                                entity.getOwnerUserId(),
                                entity.getViewerUserId(),
                                entity.getStatus()
                        )
                )
                .orElse(null);
    }

    @Override
    public List<LocationSharePermission> findByOwnerAndStatus(String ownerUserId, ShareStatus status) {

        return repository.findByOwnerUserIdAndStatus(ownerUserId, status)
                .stream()
                .map(entity ->
                        new LocationSharePermission(
                                entity.getId(),
                                entity.getOwnerUserId(),
                                entity.getViewerUserId(),
                                entity.getStatus()
                        )
                )
                .toList();
    }

    @Override
    public boolean hasAcceptedPermission(String ownerUserId, String viewerUserId) {
        return repository.existsByOwnerUserIdAndViewerUserIdAndStatus(ownerUserId, viewerUserId, ShareStatus.ACCEPTED);
    }
}
