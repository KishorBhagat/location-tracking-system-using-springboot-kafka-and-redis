package com.project.location_tracking_system.application.service;

import com.project.location_tracking_system.domain.event.LocationUpdatedEvent;
import com.project.location_tracking_system.domain.model.Location;
import com.project.location_tracking_system.domain.ports.EventPublisher;
import com.project.location_tracking_system.domain.ports.LocationRepository;
import com.project.location_tracking_system.exception.LocationNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LocationService {

    private final EventPublisher eventPublisher;
    private final LocationRepository locationRepository;

    public LocationService(EventPublisher eventPublisher, LocationRepository locationRepository) {
        this.eventPublisher = eventPublisher;
        this.locationRepository = locationRepository;
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
}
