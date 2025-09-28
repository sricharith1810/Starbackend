package com.example.Star.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
    private String username;
    private String email;
    private String phonenumber;
    private String address;
    private String state;
    private String city;
    private String dob;
    private String postalcode;
}
