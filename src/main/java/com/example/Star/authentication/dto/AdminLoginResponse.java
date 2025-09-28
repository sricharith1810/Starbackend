// src/main/java/com/example/Star/authentication/dto/AdminLoginResponse.java
package com.example.Star.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdminLoginResponse {
    private String message;
    private boolean success;
    private String token;
}
