package com.example.Star.silverloan.service;

import com.example.Star.goldloan.model.GoldLoanApplication;
import com.example.Star.goldloan.repo.GoldLoanApplicationRepository;
import com.example.Star.silverloan.model.SilverLoanApplication;
import com.example.Star.silverloan.repo.SilverLoanApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SilverLoanService {

    @Autowired
    private SilverLoanApplicationRepository silverLoanApplicationRepository;

    public List<SilverLoanApplication> getAllApplications() {
        return silverLoanApplicationRepository.findAll();
    }
}
