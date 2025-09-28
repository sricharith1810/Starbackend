package com.example.Star.authentication.controlller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/protected")
@CrossOrigin
public class ProtectedController {

    @GetMapping("/profile")
    public ResponseEntity<String> getProfile(Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok("Welcome, " + username + "! This is your protected profile.");
    }
}