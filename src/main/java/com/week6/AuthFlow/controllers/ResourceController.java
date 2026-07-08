package com.week6.AuthFlow.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/resources")
public class ResourceController {

    private final List<String> freeData = List.of(
            "Welcome to AuthFlow!",
            "Today's quote: Consistency beats motivation.",
            "Free Article: Introduction to Spring Boot",
            "Free Video: Java Basics",
            "Free Template: Resume.pdf"
    );

    private final List<String> basicData = List.of(
            "Basic Course: Spring Boot REST APIs",
            "Basic Course: Spring Security",
            "Download: JWT Cheat Sheet",
            "Practice Project: Todo Application",
            "API Collection: Postman Workspace"
    );

    private final List<String> premiumData = List.of(
            "Premium Course: Microservices",
            "Premium Course: System Design",
            "Exclusive Interview Questions",
            "Source Code: E-Commerce Backend",
            "1-on-1 Mentor Session Access"
    );

    @GetMapping("/free")
    @PreAuthorize("hasAuthority('FREE')")
    public List<String> getFreeResources() {
        return freeData;
    }

    @GetMapping("/basic")
    @PreAuthorize("hasAuthority('BASIC')")
    public List<String> getBasicResources() {
        return basicData;
    }

    @GetMapping("/premium")
    @PreAuthorize("hasAuthority('PREMIUM')")
    public List<String> getPremiumResources() {
        return premiumData;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('FREE', 'BASIC', 'PREMIUM')")
    public String allResources() {
        return "Authenticated successfully! You can access protected resources.";
    }
}