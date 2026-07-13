package com.project.location_tracking_system.domain.ports;

import com.project.location_tracking_system.domain.model.User;

public interface UserRepository {

    User save(User user);

    User findByUserId(String userId);

    User findByUsername(String username);

    User findByEmail(String email);

    boolean existsByUserId(String userId);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
