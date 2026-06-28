package com.project.location_tracking_system.api.contoller;

import com.project.location_tracking_system.api.dto.LocationRequest;
import com.project.location_tracking_system.application.service.LocationService;
import com.project.location_tracking_system.domain.event.LocationUpdatedEvent;
import com.project.location_tracking_system.domain.model.Location;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/locations")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping
    public ResponseEntity<Void> updateLocation(@Valid @RequestBody LocationRequest request) {
        LocationUpdatedEvent event = new LocationUpdatedEvent(
                request.userId(),
                request.latitude(),
                request.longitude(),
                System.currentTimeMillis()
        );
        locationService.updateLocation(event);

        return ResponseEntity.accepted().build();
    }

    @GetMapping("/{ownerUserId}")
    public ResponseEntity<Location> getLocation(@PathVariable String ownerUserId, @RequestParam String viewerUserId) {
        Location location = locationService.getLocation(ownerUserId, viewerUserId);

        return ResponseEntity.ok(location);
    }
}
