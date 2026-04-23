package com.example.appointment.error;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(err->fieldErrors.put(err.getField(),err.getDefaultMessage()));
        ApiError body=new ApiError(
                Instant.now(),
                400,
                "Bad Request",
                "Validation Field",
                request.getRequestURI(),
                fieldErrors
        );
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(NotFoundException ex, HttpServletRequest request) {
        ApiError body = new ApiError(
                Instant.now(),
                404,
                "Not Found",
                ex.getMessage(),
                request.getRequestURI(),
                Map.of()
        );
        return ResponseEntity.status(404).body(body);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgument(
            IllegalArgumentException ex,
            HttpServletRequest request
    ){
        ApiError body=new ApiError(
                Instant.now(),
                400,
                "Bad Request",
                ex.getMessage(),
                request.getRequestURI(),
                Map.of()
        );
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> handleAuthenticationException(AuthenticationException ex, HttpServletRequest request) {
        ApiError body = new ApiError(
                Instant.now(),
                401,
                "Unauthorized",
                ex.getMessage(),
                request.getRequestURI(),
                Map.of()
        );
        return ResponseEntity.status(401).body(body);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> handleBadRequest(BadRequestException ex, HttpServletRequest request) {
        ApiError body = new ApiError(
                Instant.now(),
                400,
                "Bad Request",
                ex.getMessage(),
                request.getRequestURI(),
                Map.of()
        );
        return ResponseEntity.badRequest().body(body);
    }

 /*   //catch-all (don’t reveal internals)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleUnknown(Exception ex, HttpServletRequest request) {
        ApiError body = new ApiError(
                Instant.now(),
                500,
                "Internal Server Error",
                "Something went wrong",
                request.getRequestURI(),
                Map.of()
        );
        return ResponseEntity.status(500).body(body);
    }*/

}