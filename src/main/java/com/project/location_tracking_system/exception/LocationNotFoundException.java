package com.project.location_tracking_system.exception;

public class LocationNotFoundException extends RuntimeException{
    public LocationNotFoundException(String userId) {
        super("Location not found for userId: " + userId);
    }
}
