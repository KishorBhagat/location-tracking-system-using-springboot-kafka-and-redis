package com.project.location_tracking_system.api.contoller;

import com.project.location_tracking_system.api.dto.LoginRequest;
import com.project.location_tracking_system.api.dto.LoginResponse;
import com.project.location_tracking_system.api.dto.RegisterRequest;
import com.project.location_tracking_system.api.dto.UserResponse;
import com.project.location_tracking_system.application.service.UserService;
import com.project.location_tracking_system.domain.model.User;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request) {

        User user = userService.register(request.username(), request.email(), request.password());

        UserResponse response = new UserResponse(
                user.userId(),
                user.username(),
                user.email()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {

        LoginResponse response = userService.login(request.username(), request.password());

        return ResponseEntity.ok(response);
    }
}
