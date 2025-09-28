package com.example.Star.silverloan.model;

import com.example.Star.authentication.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.web.bind.annotation.CrossOrigin;

@Entity
@Data
@CrossOrigin(origins = "*")
public class SilverLoanApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    private String knNumber;
    private String silverType;
    private Double weight;
    private String placeBoughtFrom;
    private String jewelersName;
    private String jewelersAddress;

    @Column(name = "silver_photo", columnDefinition = "TEXT")
    private String silverPhoto;

    private String accountHolderName;
    private String accountType;
    private String accountNumber;
    private String ifscCode;
    private String bankName;
    private String branchName;

    private boolean declaration;
    private String status = "PENDING";
    private String requestID;
}
