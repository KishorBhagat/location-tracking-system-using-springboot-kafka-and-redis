package com.project.location_tracking_system.api.contoller;

import com.project.location_tracking_system.api.dto.LocationSharePermissionRequest;
import com.project.location_tracking_system.api.dto.PermissionActionRequest;
import com.project.location_tracking_system.application.service.LocationSharePermissionService;
import com.project.location_tracking_system.domain.model.LocationSharePermission;
import com.project.location_tracking_system.infrastructure.security.AuthenticatedUserProvider;
import com.project.location_tracking_system.infrastructure.security.UserPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permissions")
public class LocationSharePermissionController {

    private final LocationSharePermissionService service;

    private final AuthenticatedUserProvider authenticatedUserProvider;

    public LocationSharePermissionController(LocationSharePermissionService service, AuthenticatedUserProvider authenticatedUserProvider) {
        this.service = service;
        this.authenticatedUserProvider = authenticatedUserProvider;
    }

    @PostMapping("/request")
    public LocationSharePermission create(@RequestBody LocationSharePermissionRequest request) {
        UserPrincipal currentUser = authenticatedUserProvider.getCurrentUser();
        return service.createPermission(request.ownerUserId(), currentUser.getUserId());
    }

    @GetMapping("/{ownerUserId}")
    public List<LocationSharePermission> getPermissions(@PathVariable String ownerUserId) {

        return service.getPermissions(ownerUserId);
    }

    @PatchMapping("/accept")
    public ResponseEntity<LocationSharePermission> acceptPermission(@RequestBody PermissionActionRequest request) {
        UserPrincipal currentUser = authenticatedUserProvider.getCurrentUser();
        return ResponseEntity.ok(service.acceptPermission(currentUser.getUserId(), request.viewerUserId()));
    }

    @PatchMapping("/reject")
    public ResponseEntity<LocationSharePermission> rejectPermission(@RequestBody PermissionActionRequest request) {
        UserPrincipal currentUser = authenticatedUserProvider.getCurrentUser();
        return ResponseEntity.ok(service.rejectPermission(currentUser.getUserId(), request.viewerUserId()));
    }

    @PatchMapping("/revoke")
    public ResponseEntity<LocationSharePermission> revokePermission(@RequestBody PermissionActionRequest request) {
        UserPrincipal currentUser = authenticatedUserProvider.getCurrentUser();
        return ResponseEntity.ok(service.revokePermission(currentUser.getUserId(), request.viewerUserId()));
    }
}