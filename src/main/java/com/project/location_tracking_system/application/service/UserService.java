package com.project.location_tracking_system.application.service;

import com.project.location_tracking_system.api.dto.LoginResponse;
import com.project.location_tracking_system.domain.model.User;
import com.project.location_tracking_system.domain.ports.TokenService;
import com.project.location_tracking_system.domain.ports.UserRepository;
import com.project.location_tracking_system.exception.InvalidCredentialsException;
import com.project.location_tracking_system.exception.ResourceAlreadyExistsException;
import com.project.location_tracking_system.exception.ResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    public User register(String username, String email, String password) {

        if(repository.existsByUsername(username)) {
            throw new ResourceAlreadyExistsException("Username already exists");
        }

        if (repository.existsByEmail(email)) {
            throw new ResourceAlreadyExistsException("Email already exists");
        }

        String encodedPassword = passwordEncoder.encode(password);

        User user = repository.save(
                new User(
                        null,
                        null,
                        username,
                        email,
                        encodedPassword
                )
        );

        User updatedUser = new User(
                user.id(),
                "user" + user.id(),
                user.username(),
                user.email(),
                user.password()
        );

        return repository.save(updatedUser);
    }

    public LoginResponse login(String username, String password) {

        User user = repository.findByUsername(username);

        if (user == null) {
            throw new InvalidCredentialsException("Invalid username or password");
        }

        if (!passwordEncoder.matches(password, user.password())) {
            throw new InvalidCredentialsException("Invalid username or password");
        }

        String token = tokenService.generateToken(user);
        return new LoginResponse(
                user.userId(),
                user.username(),
                token
        );
    }

    public User getByUserId(String userId) {

        User user = repository.findByUserId(userId);

        if(user == null) {
            throw new ResourceNotFoundException("User not found");
        }

        return user;
    }
}

