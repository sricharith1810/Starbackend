// src/main/java/com/example/Star/authentication/dto/AdminLoginRequest.java
package com.example.Star.authentication.dto;

import lombok.Data;

@Data
public class AdminLoginRequest {
    private String email;
    private String password;
}
