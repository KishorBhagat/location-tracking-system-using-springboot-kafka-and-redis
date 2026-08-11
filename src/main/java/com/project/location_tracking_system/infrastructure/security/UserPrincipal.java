package com.project.location_tracking_system.infrastructure.security;

import java.security.Principal;

public class UserPrincipal implements Principal {

    private final String userId;

    private final String username;

    public UserPrincipal(String userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String getName() {
        return userId;
    }
}