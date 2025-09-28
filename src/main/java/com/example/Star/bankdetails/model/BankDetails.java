package com.example.Star.bankdetails.model;

import com.example.Star.authentication.model.User;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "bank_details")
@Data
public class BankDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private String accountNumber;

    private String ifscCode;

    private String knNumber;

    private String branchCode; // for reference
}
