package com.example.Star.authentication.controller;

import com.example.Star.authentication.dto.UserDTO;
import com.example.Star.authentication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/details/{username}")
    public ResponseEntity<?> getUserDetails(@PathVariable String username) {
        return userService.getUserDTOByUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
