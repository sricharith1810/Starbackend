package com.example.Star.kycdetails.model;

import com.example.Star.authentication.model.User;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "kyc_details")
@Data
public class KycDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private String phoneNumber;

    private String aadhaar;

    private String pan;

    private boolean kycVerified = true;

    // Getters and setters
}
