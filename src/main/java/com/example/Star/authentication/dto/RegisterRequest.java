package com.example.Star.authentication.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RegisterRequest {
    private String username;
    private String dob;
    private String email;
    private String phonenumber;
    private String password;
    private String address;
    private String city;
    private String state;
    private String postalcode;
    private String accountType;
    private String branchLocation;
    private String bankName;

}

