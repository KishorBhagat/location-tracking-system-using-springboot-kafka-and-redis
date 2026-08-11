package com.project.location_tracking_system.infrastructure.websocket;

import com.project.location_tracking_system.domain.event.LocationUpdatedEvent;
import com.project.location_tracking_system.domain.model.LocationSharePermission;
import com.project.location_tracking_system.domain.ports.LocationSharePermissionRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LocationWebSocketPublisher {

    private final SimpMessagingTemplate messagingTemplate;
    private final LocationSharePermissionRepository permissionRepository;

    public LocationWebSocketPublisher(
            SimpMessagingTemplate messagingTemplate,
            LocationSharePermissionRepository permissionRepository) {

        this.messagingTemplate = messagingTemplate;
        this.permissionRepository = permissionRepository;
    }

    public void publish(LocationUpdatedEvent event) {
        System.out.println("Publishing WebSocket event for " + event.userId());
        List<LocationSharePermission> viewers =
                permissionRepository.findAcceptedViewers(event.userId());

        for (LocationSharePermission permission : viewers) {

            System.out.println(
                    "Sending location of " + event.userId() +
                            " to " + permission.viewerUserId()
            );

            System.out.println("Sending to user: " + permission.viewerUserId());

            messagingTemplate.convertAndSendToUser(
                    permission.viewerUserId(),
                    "/queue/location",
                    event
            );

            System.out.println("convertAndSendToUser() executed");
        }
    }
}