package com.example.Star.goldloan.model;

import com.example.Star.authentication.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class GoldLoanApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    private String knNumber;
    private String goldType;
    private Double weight;
    private String placeBoughtFrom;
    private String jewelersName;
    private String jewelersAddress;

//    @Lob
//    @Basic(fetch = FetchType.LAZY)
    @Column(name = "gold_photo", columnDefinition = "TEXT")
    private String goldPhoto;



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
