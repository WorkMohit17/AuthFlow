package com.week6.AuthFlow.advices;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws IOException {

        APIError error = APIError.builder()
                .message("You don't have permission to access this resource")
                .status(HttpStatus.FORBIDDEN)
                .build();

        APIResponse<?> apiResponse = new APIResponse<>(error);

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json");

        response.getWriter()
                .write(objectMapper.writeValueAsString(apiResponse));
    }
}