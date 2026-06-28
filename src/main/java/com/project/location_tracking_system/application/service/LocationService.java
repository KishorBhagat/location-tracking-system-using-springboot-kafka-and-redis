package com.project.location_tracking_system.application.service;

import com.project.location_tracking_system.domain.event.LocationUpdatedEvent;
import com.project.location_tracking_system.domain.model.Location;
import com.project.location_tracking_system.domain.ports.EventPublisher;
import com.project.location_tracking_system.domain.ports.LocationRepository;
import com.project.location_tracking_system.domain.ports.LocationSharePermissionRepository;
import com.project.location_tracking_system.exception.LocationNotFoundException;
import com.project.location_tracking_system.exception.PermissionDeniedException;
import org.springframework.stereotype.Service;

@Service
public class LocationService {

    private final EventPublisher eventPublisher;
    private final LocationRepository locationRepository;
    private final LocationSharePermissionRepository permissionRepository;

    public LocationService(EventPublisher eventPublisher, LocationRepository locationRepository, LocationSharePermissionRepository permissionRepository) {
        this.eventPublisher = eventPublisher;
        this.locationRepository = locationRepository;
        this.permissionRepository = permissionRepository;
    }

    public void updateLocation(LocationUpdatedEvent event) {
        eventPublisher.publish(event);
    }

    public Location getLocation(String userId) {
        Location location = locationRepository.findByUserId(userId);

        if(location == null) {
            throw new LocationNotFoundException(userId);
        }

        return location;
    }

    public Location getLocation(String ownerUserId, String viewerUserId) {

        if(!permissionRepository.hasAcceptedPermission(ownerUserId, viewerUserId)) {
            throw new PermissionDeniedException("Location sharing permission not granted");
        }

        Location location = locationRepository.findByUserId(ownerUserId);

        if(location == null) {
            throw new LocationNotFoundException(ownerUserId);
        }

        return location;
    }
}
