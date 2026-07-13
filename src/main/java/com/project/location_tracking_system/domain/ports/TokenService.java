package com.project.location_tracking_system.domain.ports;

import com.project.location_tracking_system.domain.model.User;

public interface TokenService {
    String generateToken(User user);

    String extractUserId(String token);

    String extractUsername(String token);

    boolean isValid(String token);
}
