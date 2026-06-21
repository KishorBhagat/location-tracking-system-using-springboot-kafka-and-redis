package com.project.location_tracking_system.domain.ports;

import com.project.location_tracking_system.domain.event.LocationUpdatedEvent;

public interface EventPublisher {

    void publish(LocationUpdatedEvent event);
}
