package com.project.location_tracking_system.api.contoller;

import com.project.location_tracking_system.api.dto.UserProfileResponse;
import com.project.location_tracking_system.application.service.UserService;
import com.project.location_tracking_system.domain.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserProfileResponse> getUser(
            @PathVariable String userId) {

        User user = userService.getByUserId(userId);

        return ResponseEntity.ok(
                new UserProfileResponse(
                        user.userId(),
                        user.username()
                )
        );
    }
}