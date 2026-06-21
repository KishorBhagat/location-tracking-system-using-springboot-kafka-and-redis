package com.project.location_tracking_system.infrastructure.kafka;

import com.project.location_tracking_system.domain.event.LocationUpdatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class LocationEventConsumer {

    @KafkaListener(
            topics = KafkaTopics.USER_LOCATION_EVENTS,
            groupId = "location-consumer-group"
    )
    public void consume(LocationUpdatedEvent event) {
        System.out.println("Consumed location event: " + event);
    }
}
