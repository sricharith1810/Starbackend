package com.example.Star.goldloan.controller;

import com.example.Star.goldloan.model.GoldLoanApplication;
import com.example.Star.goldloan.repo.GoldLoanApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/protected/gold-loan")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class GoldLoanStatusController {

    private final GoldLoanApplicationRepository goldLoanApplicationRepository;

    @GetMapping("/status/{username}")
    public ResponseEntity<?> checkGoldLoanStatus(@PathVariable String username) {
        Optional<GoldLoanApplication> pendingLoan = goldLoanApplicationRepository
                .findByUser_UsernameAndStatus(username, "PENDING");

        if (pendingLoan.isPresent()) {
            return ResponseEntity.status(202).body("You have already submitted a gold loan application. Please wait until it is processed.");
        } else {
            return ResponseEntity.ok("No pending gold loan applications found.");
        }
    }
//    @CrossOrigin(origins = "http://localhost:5173")
//    @GetMapping("/all")
//    public ResponseEntity<List<GoldLoanApplication>> getAllApplications() {
//        List<GoldLoanApplication> applications = goldLoanApplicationRepository.findAll();
//        return ResponseEntity.ok(applications);
//    }
}