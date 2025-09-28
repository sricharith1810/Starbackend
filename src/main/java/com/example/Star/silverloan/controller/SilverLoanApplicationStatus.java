package com.example.Star.silverloan.controller;

import com.example.Star.goldloan.model.GoldLoanApplication;
import com.example.Star.goldloan.service.GoldLoanService;
import com.example.Star.silverloan.model.SilverLoanApplication;
import com.example.Star.silverloan.repo.SilverLoanApplicationRepository;
import com.example.Star.silverloan.service.SilverLoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/silverloan")
@CrossOrigin(origins = "*")
public class SilverLoanApplicationStatus {

    @Autowired
    private SilverLoanService silverLoanService;

    @GetMapping("/all")
    public ResponseEntity<List<SilverLoanApplication>> getAllApplications() {
        return ResponseEntity.ok(silverLoanService.getAllApplications());
    }
}
