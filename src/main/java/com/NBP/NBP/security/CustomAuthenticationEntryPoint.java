package com.NBP.NBP.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        String message = "Unauthorized";

        Throwable cause = authException.getCause();
        if (cause != null) {
            String causeName = cause.getClass().getSimpleName();
            switch (causeName) {
                case "ExpiredJwtException":
                    message = "JWT token expired";
                    break;
                case "MalformedJwtException":
                    message = "Malformed JWT token";
                    break;
                case "SignatureException":
                    message = "Invalid JWT signature";
                    break;
                case "UnsupportedJwtException":
                    message = "Unsupported JWT token";
                    break;
                case "IllegalArgumentException":
                    message = "JWT claims string is empty";
                    break;
                default:
                    message = authException.getMessage() != null ? authException.getMessage() : message;
            }
        } else if (authException.getMessage() != null) {
            message = authException.getMessage();
        }

        Map<String, Object> errorResponse = Map.of(
                "error", message,
                "status", HttpServletResponse.SC_UNAUTHORIZED);

        objectMapper.writeValue(response.getWriter(), errorResponse);
    }
}
