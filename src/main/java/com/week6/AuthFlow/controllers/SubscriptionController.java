package com.week6.AuthFlow.controllers;

import com.week6.AuthFlow.dtos.SubscriptionDTO;
import com.week6.AuthFlow.services.AuthService;
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
    public ResponseEntity<String> addSubscription(
            @PathVariable Long userId,
            @RequestBody SubscriptionDTO dto){
        log.info("Controller called");
        log.info(dto.getSubscription().name());
        authService.addSubscription(userId, dto.getSubscription());
        return ResponseEntity.ok("Subscription added.");
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> removeSubscription(
            @PathVariable Long userId,
            @RequestBody SubscriptionDTO dto){

        authService.removeSubscription(userId, dto.getSubscription());

        return ResponseEntity.ok("Subscription removed.");
    }

}