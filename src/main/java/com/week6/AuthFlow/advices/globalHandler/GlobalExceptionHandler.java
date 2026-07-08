package com.week6.AuthFlow.advices.globalHandler;

import com.week6.AuthFlow.advices.APIError;
import com.week6.AuthFlow.advices.APIResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIResponse<?>> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex) {

        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse("Validation failed");

        return buildErrorResponse(message, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<APIResponse<?>> handleConstraintViolation(
            ConstraintViolationException ex) {

        String message = ex.getConstraintViolations()
                .stream()
                .findFirst()
                .map(ConstraintViolation::getMessage)
                .orElse("Validation failed");

        return buildErrorResponse(message, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<APIResponse<?>> handleBadCredentials(
            BadCredentialsException ex) {

        return buildErrorResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(AuthenticationServiceException.class)
    public ResponseEntity<APIResponse<?>> handleAuthenticationService(
            AuthenticationServiceException ex) {

        return buildErrorResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(SessionAuthenticationException.class)
    public ResponseEntity<APIResponse<?>> handleSession(
            SessionAuthenticationException ex) {

        return buildErrorResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<APIResponse<?>> handleUsernameNotFound(
            UsernameNotFoundException ex) {

        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<APIResponse<?>> handleNoSuchElement(
            NoSuchElementException ex) {

        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<APIResponse<?>> handleIllegalArgument(
            IllegalArgumentException ex) {

        return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<APIResponse<?>> handleExpiredJwt(
            ExpiredJwtException ex) {

        return buildErrorResponse(
                "JWT Token has expired",
                HttpStatus.UNAUTHORIZED
        );
    }


    @ExceptionHandler(JwtException.class)
    public ResponseEntity<APIResponse<?>> handleJwt(
            JwtException ex) {

        return buildErrorResponse(
                "Invalid JWT token",
                HttpStatus.UNAUTHORIZED
        );
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponse<?>> handleException(
            Exception ex) {

        return buildErrorResponse(
                ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }


    private ResponseEntity<APIResponse<?>> buildErrorResponse(
            String message,
            HttpStatus status) {

        APIError error = APIError.builder()
                .message(message)
                .status(status)
                .build();

        return ResponseEntity
                .status(status)
                .body(new APIResponse<>(error));
    }
}