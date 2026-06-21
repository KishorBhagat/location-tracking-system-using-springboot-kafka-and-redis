package com.project.location_tracking_system.infrastructure.kafka;

import com.project.location_tracking_system.domain.event.LocationUpdatedEvent;
import com.project.location_tracking_system.domain.ports.EventPublisher;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaEventPublisher implements EventPublisher {

    private final KafkaTemplate<String, LocationUpdatedEvent> kafkaTemplate;

    public KafkaEventPublisher(KafkaTemplate<String, LocationUpdatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publish(LocationUpdatedEvent event) {
        kafkaTemplate.send(
           KafkaTopics.USER_LOCATION_EVENTS,
           event.userId(),
           event
        );

        System.out.println(
                "Published location event: " + event
        );
    }
}
