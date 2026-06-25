package com.project.location_tracking_system.infrastructure.persistence.repository;

import com.project.location_tracking_system.domain.model.ShareStatus;
import com.project.location_tracking_system.infrastructure.persistence.entity.LocationSharePermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaLocationSharePermissionRepository extends JpaRepository<LocationSharePermissionEntity, Long> {

    List<LocationSharePermissionEntity> findByOwnerUserId(String ownerUserId);

    Optional<LocationSharePermissionEntity> findByOwnerUserIdAndViewerUserId(String ownerUserId, String viewerUserId);

    List<LocationSharePermissionEntity> findByOwnerUserIdAndStatus(String ownerUserId, ShareStatus status);
}
