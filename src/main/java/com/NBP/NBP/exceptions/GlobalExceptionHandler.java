package com.NBP.NBP.exceptions;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException e) {
        Map<String, Object> errorResponse = Map.of(
                "error", e.getMessage(),
                "status", HttpServletResponse.SC_BAD_REQUEST);
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessDenied(AccessDeniedException e) {
        Map<String, Object> errorResponse = Map.of(
                "error", "Access denied",
                "status", HttpServletResponse.SC_FORBIDDEN);
        return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).body(errorResponse);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Map<String, Object>> handleNullPointer(NullPointerException e) {
        Map<String, Object> errorResponse = Map.of(
                "error", "Unexpected server error",
                "status", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler({ MethodArgumentNotValidException.class, BindException.class })
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(Exception e) {
        String message = "Validation failed";
        if (e instanceof MethodArgumentNotValidException manvEx) {
            message = manvEx.getBindingResult().getFieldErrors().stream()
                    .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                    .findFirst()
                    .orElse(message);
        } else if (e instanceof BindException bindEx) {
            message = bindEx.getBindingResult().getFieldErrors().stream()
                    .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                    .findFirst()
                    .orElse(message);
        }

        Map<String, Object> errorResponse = Map.of(
                "error", message,
                "status", HttpServletResponse.SC_BAD_REQUEST);
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleResponseStatusException(ResponseStatusException e) {
        Map<String, Object> errorResponse = Map.of(
                "error", e.getReason(),
                "status", e.getStatusCode().value());
        return ResponseEntity.status(e.getStatusCode()).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException(Exception e) {
        Map<String, Object> errorResponse = Map.of(
                "error", "An unexpected error occurred",
                "status", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
