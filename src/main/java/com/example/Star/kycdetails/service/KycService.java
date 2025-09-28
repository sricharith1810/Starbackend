package com.example.Star.kycdetails.service;

import com.example.Star.authentication.model.User;
import com.example.Star.authentication.repo.UserRepository;
import com.example.Star.bankdetails.model.BankDetails;
import com.example.Star.bankdetails.service.BankDetailsService;
import com.example.Star.branch.repo.BranchRepository;
import com.example.Star.email.EmailService;
import com.example.Star.kycdetails.dto.KycRequest;
import com.example.Star.kycdetails.model.KycDetails;
import com.example.Star.kycdetails.model.KycVerification;
import com.example.Star.kycdetails.repo.KycDetailsRepository;
import com.example.Star.kycdetails.repo.KycVerificationRepository;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.Star.branch.model.Branch;

@Service
public class KycService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private KycVerificationRepository kycVerificationRepository;

    @Autowired
    private KycDetailsRepository kycDetailsRepository;

    @Autowired
    private BankDetailsService bankDetailsService;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired private EmailService emailService;


    public String submitKyc(KycRequest request) {
        KycVerification match = kycVerificationRepository
                .findByAadhaarAndPan(request.getAadhaar(), request.getPan())
                .orElse(null);

        if (match == null) {
            return "Invalid KYC details. Aadhar or PAN do not match.";
        }

        User user = userRepository.findByUsername(request.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (kycDetailsRepository.findByUser_Username(user.getUsername()).isPresent()) {
            return "KYC already completed.";
        }

        KycDetails kyc = new KycDetails();
        kyc.setUser(user);
        kyc.setAadhaar(request.getAadhaar());
        kyc.setPan(request.getPan());
//        kyc.setPhoneNumber(request.get());
        kyc.setKycVerified(true);

        kycDetailsRepository.save(kyc);

        String location = user.getBranchLoaction();
        String branchCode = branchRepository.findByLocation(location)
                .map(Branch::getCode)
                .orElseThrow(() -> new RuntimeException("Branch code not found for location: " + location));


        BankDetails details = bankDetailsService.generateBankDetails(user, branchCode);

        emailService.sendBankDetailsEmail(
                user.getEmail(),
                user.getUsername(),
                details.getKnNumber(),
                details.getAccountNumber(),
                details.getIfscCode()
        );
        return "KYC verified and saved successfully!";
    }

    public boolean checkKycStatus(String username) {
        return kycDetailsRepository.findByUser_Username(username).isPresent();
    }
}