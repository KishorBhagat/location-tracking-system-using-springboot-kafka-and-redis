package com.project.location_tracking_system.infrastructure.websocket;

import com.project.location_tracking_system.domain.ports.TokenService;
import com.project.location_tracking_system.infrastructure.security.UserPrincipal;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;

@Component
public class JwtHandshakeInterceptor implements HandshakeInterceptor {

    private final TokenService tokenService;

    public JwtHandshakeInterceptor(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public boolean beforeHandshake(
            ServerHttpRequest request,
            ServerHttpResponse response,
            WebSocketHandler wsHandler,
            Map<String, Object> attributes) {

        HttpServletRequest servletRequest =
                ((ServletServerHttpRequest) request).getServletRequest();

        String token = servletRequest.getParameter("token");

        if (token == null) {
            return false;
        }

        if (!tokenService.isValid(token)) {
            return false;
        }

        String userId =
                tokenService.extractUserId(token);

        String username =
                tokenService.extractUsername(token);

        attributes.put(
                "principal",
                new UserPrincipal(userId, username)
        );

        return true;
    }

    @Override
    public void afterHandshake(
            ServerHttpRequest request,
            ServerHttpResponse response,
            WebSocketHandler wsHandler,
            Exception exception) {

    }
}