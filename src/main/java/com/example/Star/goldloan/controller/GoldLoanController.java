package com.example.Star.goldloan.controller;

import com.example.Star.authentication.model.User;
import com.example.Star.authentication.repo.UserRepository;
import com.example.Star.bankdetails.model.BankDetails;
import com.example.Star.bankdetails.repo.BankDetailsRepository;
import com.example.Star.email.EmailService;
import com.example.Star.goldloan.model.GoldLoanApplication;
import com.example.Star.goldloan.repo.GoldLoanApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/protected/goldloan")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class GoldLoanController {

    private final UserRepository userRepository;
    private final BankDetailsRepository bankDetailsRepository;
    private final GoldLoanApplicationRepository goldLoanApplicationRepository;
    private final EmailService emailService;

    @PostMapping("/apply")
    public ResponseEntity<?> applyLoan(
            @RequestParam("username") String username,
            @RequestParam("knNumber") String knNumber,
            @RequestParam("goldType") String goldType,
            @RequestParam("weight") Double weight,
            @RequestParam("placeBoughtFrom") String placeBoughtFrom,
            @RequestParam("jewelersName") String jewelersName,
            @RequestParam("jewelersAddress") String jewelersAddress,

            @RequestParam("goldPhoto") MultipartFile goldPhoto,

            @RequestParam("accountHolderName") String accountHolderName,
            @RequestParam("accountType") String accountType,
            @RequestParam("accountNumber") String accountNumber,
            @RequestParam("ifscCode") String ifscCode,
            @RequestParam("bankName") String bankName,
            @RequestParam("branchName") String branchName,
            @RequestParam("declaration") boolean declaration
    ) throws IOException {

        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) return ResponseEntity.badRequest().body("User not found");
        User user = userOpt.get();

        Optional<BankDetails> bankDetailsOpt = bankDetailsRepository.findByUser_Username(username);
        if (bankDetailsOpt.isEmpty()) return ResponseEntity.badRequest().body("No bank details found");

        BankDetails bankDetails = bankDetailsOpt.get();

        if (!bankDetails.getKnNumber().equals(knNumber) || !bankDetails.getAccountNumber().equals(accountNumber)
                || !bankDetails.getIfscCode().equalsIgnoreCase(ifscCode)) {
            return ResponseEntity.badRequest().body("Bank details do not match registered records");
        }

        // Save loan
        GoldLoanApplication loan = new GoldLoanApplication();

        loan.setUser(user);
        loan.setKnNumber(knNumber);
        loan.setGoldType(goldType);
        loan.setWeight(weight);
        loan.setPlaceBoughtFrom(placeBoughtFrom);
        loan.setJewelersName(jewelersName);
        loan.setJewelersAddress(jewelersAddress);

        try {
            byte[] imageBytes = goldPhoto.getBytes();
            String base64image = Base64.getEncoder().encodeToString(imageBytes);
            loan.setGoldPhoto(base64image);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to read gold photo file.");
        }

        loan.setAccountHolderName(accountHolderName);
        loan.setAccountType(accountType);
        loan.setAccountNumber(accountNumber);
        loan.setIfscCode(ifscCode);
        loan.setBankName(bankName);
        loan.setBranchName(branchName);
        loan.setDeclaration(declaration);

        loan.setStatus("PENDING");
        String RID = "RID"+ UUID.randomUUID().toString().replace("-","").substring(0,10).toUpperCase();
        loan.setRequestID(RID);

        goldLoanApplicationRepository.save(loan);

        String sub = "Gold Loan Application Submitted";
        String message = "Dear "+ user.getUsername() + ",\n\n" +
                "Your gold loan application has been received successfully.\n" +
                "Here are your submission details:\n" +
                "Request ID: " + RID+ "\n" +
                "KN Number: " + knNumber + "\n" +
                "Weight: " + weight + " grams\n" +
                "Gold Type: " + goldType + "\n" +
                "Account Number: " + accountNumber + "\n" +
                "IFSC Code: " + ifscCode + "\n\n" +
                "Our team will review your request and contact you soon within 2 working days.\n\n" +
                "Regards,\nStar Finance";

        emailService.sendSimpleMail(user.getEmail(), sub, message);
        return ResponseEntity.ok("Gold loan application submitted successfully");
    }

    // Optional: Endpoint to serve image from DB
    @GetMapping("/photo/{id}")
    public ResponseEntity<byte[]> getGoldPhoto(@PathVariable Long id) {
        Optional<GoldLoanApplication> opt = goldLoanApplicationRepository.findById(id);
        if (opt.isEmpty() || opt.get().getGoldPhoto() == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            // Decode Base64 string to byte array
            byte[] imageBytes = Base64.getDecoder().decode(opt.get().getGoldPhoto());

            return ResponseEntity.ok()
                    .header("Content-Type", "image/jpeg") // Change to "image/png" if necessary
                    .body(imageBytes);
        } catch (IllegalArgumentException e) {
            // Base64 decoding failed
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
