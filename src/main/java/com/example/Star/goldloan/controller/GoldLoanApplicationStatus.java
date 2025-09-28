package com.example.Star.goldloan.controller;

import com.example.Star.goldloan.model.GoldLoanApplication;
import com.example.Star.goldloan.service.GoldLoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/goldloan")
@CrossOrigin(origins = "*")
public class GoldLoanApplicationStatus {

    @Autowired
    private GoldLoanService goldLoanService;

    @GetMapping("/all")
    public ResponseEntity<List<GoldLoanApplication>> getAllApplications() {
        return ResponseEntity.ok(goldLoanService.getAllApplications());
    }
}
