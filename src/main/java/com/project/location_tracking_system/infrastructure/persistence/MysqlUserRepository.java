package com.project.location_tracking_system.infrastructure.persistence;

import com.project.location_tracking_system.domain.model.User;
import com.project.location_tracking_system.domain.ports.UserRepository;
import com.project.location_tracking_system.infrastructure.persistence.entity.UserEntity;
import com.project.location_tracking_system.infrastructure.persistence.repository.JpaUserRepository;
import org.springframework.stereotype.Repository;

@Repository
public class MysqlUserRepository implements UserRepository {

    private final JpaUserRepository repository;

    public MysqlUserRepository(JpaUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User save(User user) {

        UserEntity entity = user.id() != null
                ? repository.findById(user.id()).orElse(new UserEntity())
                : new UserEntity();

        entity.setId(user.id());
        entity.setUserId(user.userId());
        entity.setUsername(user.username());
        entity.setEmail(user.email());
        entity.setPassword(user.password());

        entity = repository.save(entity);

        return new User(
                entity.getId(),
                entity.getUserId(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getPassword()
        );
    }

    @Override
    public User findByUserId(String userId) {
        return repository.findByUserId(userId)
                .map(entity -> new User(
                        entity.getId(),
                        entity.getUserId(),
                        entity.getUsername(),
                        entity.getEmail(),
                        entity.getPassword()
                ))
                .orElse(null);
    }

    @Override
    public User findByUsername(String username) {
        return repository.findByUsername(username)
                .map(entity -> new User(
                        entity.getId(),
                        entity.getUserId(),
                        entity.getUsername(),
                        entity.getEmail(),
                        entity.getPassword()
                ))
                .orElse(null);
    }

    @Override
    public User findByEmail(String email) {
        return repository.findByEmail(email)
                .map(entity -> new User(
                        entity.getId(),
                        entity.getUserId(),
                        entity.getUsername(),
                        entity.getEmail(),
                        entity.getPassword()
                ))
                .orElse(null);
    }

    @Override
    public boolean existsByUserId(String userId) {
        return repository.existsByUserId(userId);
    }

    @Override
    public boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }
}