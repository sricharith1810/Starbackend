package com.example.Star.authentication.controlller;
import com.example.Star.authentication.dto.LoginResponse;
import com.example.Star.authentication.dto.LoginRequest;
import com.example.Star.authentication.dto.RegisterRequest;
import com.example.Star.authentication.service.AuthService;
//import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {
    @Autowired
    private AuthService authService;
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            LoginResponse response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("message", e.getMessage()));
        }
    }

//    @RestController
//    @RequestMapping("/api/protected")
//    @CrossOrigin(origins = "http://localhost:5173")
//    public class ProtectedController {
//
//        @GetMapping("/profile")
//        public ResponseEntity<String> getProfile(Authentication authentication) {
//            String username = authentication.getName();
//            return ResponseEntity.ok("Welcome, " + username + "! This is your protected profile.");
//        }
//    }

}
