package com.week6.AuthFlow.controllers;

import com.week6.AuthFlow.advices.APIResponse;
import com.week6.AuthFlow.dtos.SubscriptionDTO;
import com.week6.AuthFlow.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subscriptions")
@RequiredArgsConstructor
@Slf4j
public class SubscriptionController {

    private final AuthService authService;

    @PostMapping("/{userId}")
    public ResponseEntity<APIResponse<String>> addSubscription(
            @PathVariable Long userId,
            @Valid @RequestBody SubscriptionDTO dto) {

        authService.addSubscription(userId, dto.getSubscription());

        return ResponseEntity.ok(new APIResponse<>("Subscription added."));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<APIResponse<String>> removeSubscription(
            @PathVariable Long userId,
            @Valid @RequestBody SubscriptionDTO dto){

        authService.removeSubscription(userId, dto.getSubscription());

        return ResponseEntity.ok(new APIResponse<>("Subscription removed."));
    }

}