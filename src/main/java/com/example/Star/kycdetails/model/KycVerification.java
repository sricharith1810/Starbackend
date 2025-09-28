package com.example.Star.kycdetails.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "kyc_verification")
@Data
public class KycVerification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")      // Important
    private String name;

    @Column(name = "aadhaar")   // Must match lowercase column
    private String aadhaar;

    @Column(name = "pan")       // Must match lowercase column
    private String pan;
}