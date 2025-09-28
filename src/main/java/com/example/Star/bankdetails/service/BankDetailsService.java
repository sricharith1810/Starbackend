package com.example.Star.bankdetails.service;

import com.example.Star.authentication.model.User;
import com.example.Star.bankdetails.model.BankDetails;
import com.example.Star.bankdetails.repo.BankDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
public class BankDetailsService {

    private static final String BANK_CODE = "10203"; // Example bank code

    @Autowired
    private BankDetailsRepository bankDetailsRepository;

    public BankDetails generateBankDetails(User user, String branchCode) {
        BankDetails bankDetails = new BankDetails();

        String bankCode = "10203"; // Fixed
        String accountPrefix = bankCode + branchCode;

        String randomPart = String.format("%05d", new Random().nextInt(100000));
        String accountNumber = accountPrefix + randomPart;

        String knNumber = "KN" + UUID.randomUUID().toString().replace("-", "").substring(0, 10).toUpperCase();
        String ifscCode = "STAR" + branchCode;

        bankDetails.setUser(user);
        bankDetails.setAccountNumber(accountNumber);
        bankDetails.setKnNumber(knNumber);
        bankDetails.setIfscCode(ifscCode);

        return bankDetailsRepository.save(bankDetails); // 👈 returns saved entity
    }

    public BankDetails getBankDetailsByUsername(String username)
    {
        return bankDetailsRepository.findByUser_Username(username).orElse(null);
    }
}
