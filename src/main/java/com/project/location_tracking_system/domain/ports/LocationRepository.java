package com.project.location_tracking_system.domain.ports;

import com.project.location_tracking_system.domain.model.Location;

public interface LocationRepository {

    void save(Location location);

    Location findByUserId(String userId);
}
