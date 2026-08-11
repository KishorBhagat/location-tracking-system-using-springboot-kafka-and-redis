package com.project.location_tracking_system.infrastructure.websocket;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

@Component
public class WebSocketEventListener {

    @EventListener
    public void handleConnected(SessionConnectedEvent event) {

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());

        System.out.println("========== CONNECTED ==========");
        System.out.println("Session: " + accessor.getSessionId());
        System.out.println("User   : " + accessor.getUser());
        System.out.println("Name   : " + (accessor.getUser() == null ? null : accessor.getUser().getName()));
        System.out.println("===============================");
    }
}