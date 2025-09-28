package com.example.Star.kycdetails.dto;

import lombok.Data;

@Data
public class KycRequest {
    private String name;
    private String aadhaar;
    private String pan;
    private String branchCode;
}
