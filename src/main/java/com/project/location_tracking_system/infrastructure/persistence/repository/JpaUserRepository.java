package com.project.location_tracking_system.infrastructure.persistence.repository;

import com.project.location_tracking_system.infrastructure.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUserId(String userId);

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByEmail(String email);

    boolean existsByUserId(String userId);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
