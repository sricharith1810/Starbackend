package com.example.Star.kycdetails.controller;

import com.example.Star.kycdetails.dto.KycRequest;
import com.example.Star.kycdetails.service.KycService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/protected/kyc")
@CrossOrigin(origins = "http://localhost:5173")
public class KycController {

    @Autowired
    private KycService kycService;

    @PostMapping("/submit")
    public ResponseEntity<String> submitKyc(@RequestBody KycRequest request) {
        String result = kycService.submitKyc(request);

        if (result.contains("Invalid")) {
            return ResponseEntity.status(400).body(result);
        } else if (result.contains("already")) {
            return ResponseEntity.status(409).body(result);
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping("/status/{username}")
    public ResponseEntity<String> checkStatus(@PathVariable String username) {
        boolean exists = kycService.checkKycStatus(username);
        return ResponseEntity.ok(exists ? "KYC done" : "KYC not done");
    }
}
