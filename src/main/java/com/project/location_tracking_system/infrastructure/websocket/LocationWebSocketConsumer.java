package com.project.location_tracking_system.infrastructure.websocket;

import com.project.location_tracking_system.domain.event.LocationUpdatedEvent;
import com.project.location_tracking_system.infrastructure.kafka.KafkaTopics;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class LocationWebSocketConsumer {

    private final LocationWebSocketPublisher publisher;

    public LocationWebSocketConsumer(LocationWebSocketPublisher publisher) {
        this.publisher = publisher;
    }

    @KafkaListener(
            topics = KafkaTopics.USER_LOCATION_EVENTS,
            groupId = "websocket-consumer-group"
    )
    public void consume(LocationUpdatedEvent event) {
        System.out.println("WebSocket consumer received event: "+ event.userId());

        publisher.publish(event);
    }
}
