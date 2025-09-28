package com.example.Star.authentication.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
    private String branchLoaction;
    private String bankName;
}

