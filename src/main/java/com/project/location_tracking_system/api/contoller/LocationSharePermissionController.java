package com.project.location_tracking_system.api.contoller;

import com.project.location_tracking_system.api.dto.LocationSharePermissionRequest;
import com.project.location_tracking_system.api.dto.PermissionActionRequest;
import com.project.location_tracking_system.application.service.LocationSharePermissionService;
import com.project.location_tracking_system.domain.model.LocationSharePermission;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permissions")
public class LocationSharePermissionController {

    private final LocationSharePermissionService service;

    public LocationSharePermissionController(LocationSharePermissionService service) {
        this.service = service;
    }

    @PostMapping
    public LocationSharePermission create(@RequestBody LocationSharePermissionRequest request) {

        return service.createPermission(request.ownerUserId(), request.viewerUserId());
    }

    @GetMapping("/{ownerUserId}")
    public List<LocationSharePermission> getPermissions(@PathVariable String ownerUserId) {

        return service.getPermissions(ownerUserId);
    }

    @PatchMapping("/accept")
    public ResponseEntity<LocationSharePermission> acceptPermission(@RequestBody PermissionActionRequest request) {

        return ResponseEntity.ok(service.acceptPermission(request.ownerUserId(), request.viewerUserId()));
    }
    @PatchMapping("/reject")
    public ResponseEntity<LocationSharePermission> rejectPermission(@RequestBody PermissionActionRequest request) {

        return ResponseEntity.ok(service.rejectPermission(request.ownerUserId(), request.viewerUserId()));
    }

    @PatchMapping("/revoke")
    public ResponseEntity<LocationSharePermission> revokePermission(@RequestBody PermissionActionRequest request) {

        return ResponseEntity.ok(service.revokePermission(request.ownerUserId(), request.viewerUserId()));
    }
}