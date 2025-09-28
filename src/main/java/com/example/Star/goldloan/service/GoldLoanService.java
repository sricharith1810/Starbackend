package com.example.Star.goldloan.service;

import com.example.Star.goldloan.model.GoldLoanApplication;
import com.example.Star.goldloan.repo.GoldLoanApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoldLoanService {

    @Autowired
    private GoldLoanApplicationRepository goldLoanApplicationRepository;

    public List<GoldLoanApplication> getAllApplications() {
        return goldLoanApplicationRepository.findAll();
    }
}
