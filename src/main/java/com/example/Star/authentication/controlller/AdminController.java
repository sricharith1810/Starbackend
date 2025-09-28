package com.example.Star.authentication.controlller;

import com.example.Star.authentication.dto.AdminLoginRequest;
import com.example.Star.authentication.dto.AdminLoginResponse;
import com.example.Star.authentication.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<AdminLoginResponse> login(@RequestBody AdminLoginRequest request){
        String email = request.getEmail();
        String password = request.getPassword();
        if ("admin@starfinance.com".equalsIgnoreCase(email) && "secret123".equals(password)) {
//            UserDetails admin = User.withUsername(email).password("").roles("ADMIN").build();
            String token = jwtService.generateToken(email);
            return ResponseEntity.ok(new AdminLoginResponse("Login successful", true,token));
        }
        else {
            return ResponseEntity.status(401)
                    .body(new AdminLoginResponse("Invalid credentials", false,null));
        }
    }

}
