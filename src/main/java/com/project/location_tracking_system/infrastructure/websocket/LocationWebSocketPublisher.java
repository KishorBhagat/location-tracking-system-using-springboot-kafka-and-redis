package com.project.location_tracking_system.infrastructure.websocket;

import com.project.location_tracking_system.domain.event.LocationUpdatedEvent;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class LocationWebSocketPublisher {

    private final SimpMessagingTemplate messagingTemplate;

    public LocationWebSocketPublisher(SimpMessagingTemplate messagingTemplate) {

        this.messagingTemplate = messagingTemplate;
    }

    public void publish(LocationUpdatedEvent event) {

        messagingTemplate.convertAndSend(
                "/topic/location/" + event.userId(),
                event
        );

        System.out.println("Published location to websocket topic: /topic/location/" + event.userId());
    }
}