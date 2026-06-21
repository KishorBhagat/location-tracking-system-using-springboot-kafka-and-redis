package com.project.location_tracking_system.infrastructure.kafka;

import com.project.location_tracking_system.domain.event.LocationUpdatedEvent;
import com.project.location_tracking_system.domain.model.Location;
import com.project.location_tracking_system.domain.ports.LocationRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class RedisLocationConsumer {

    private final LocationRepository locationRepository;

    public RedisLocationConsumer(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @KafkaListener(
            topics = KafkaTopics.USER_LOCATION_EVENTS,
            groupId = "redis-consumer-group"
    )
    public void consume(LocationUpdatedEvent event) {
        Location location = new Location(
                event.userId(),
                event.latitude(),
                event.longitude(),
                event.timestamp()
        );

        locationRepository.save(location);

        System.out.println("Saved location to Redis: " + location.userId());
    }
}
