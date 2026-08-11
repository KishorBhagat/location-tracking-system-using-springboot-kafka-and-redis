package com.project.location_tracking_system.api.contoller;

import com.project.location_tracking_system.api.dto.LocationRequest;
import com.project.location_tracking_system.application.service.LocationService;
import com.project.location_tracking_system.domain.event.LocationUpdatedEvent;
import com.project.location_tracking_system.domain.model.Location;
import com.project.location_tracking_system.infrastructure.security.AuthenticatedUserProvider;
import com.project.location_tracking_system.infrastructure.security.UserPrincipal;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/locations")
public class LocationController {

    private final LocationService locationService;

    private final AuthenticatedUserProvider authenticatedUserProvider;

    public LocationController(LocationService locationService, AuthenticatedUserProvider authenticatedUserProvider) {
        this.locationService = locationService;
        this.authenticatedUserProvider = authenticatedUserProvider;
    }

    @PostMapping
    public ResponseEntity<Void> updateLocation(@Valid @RequestBody LocationRequest request) {
        UserPrincipal currentUser = authenticatedUserProvider.getCurrentUser();
        LocationUpdatedEvent event = new LocationUpdatedEvent(
                currentUser.getUserId(),
                request.latitude(),
                request.longitude(),
                System.currentTimeMillis()
        );
        locationService.updateLocation(event);

        return ResponseEntity.accepted().build();
    }

    @GetMapping("/{ownerUserId}")
    public ResponseEntity<Location> getLocation(@PathVariable String ownerUserId) {
        UserPrincipal currentUser = authenticatedUserProvider.getCurrentUser();
        Location location = locationService.getLocation(ownerUserId, currentUser.getUserId());

        return ResponseEntity.ok(location);
    }
}
