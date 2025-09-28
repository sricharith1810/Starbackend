package com.example.Star.silverloan.controller;

import com.example.Star.authentication.model.User;
import com.example.Star.authentication.repo.UserRepository;
import com.example.Star.bankdetails.model.BankDetails;
import com.example.Star.bankdetails.repo.BankDetailsRepository;
import com.example.Star.email.EmailService;
import com.example.Star.silverloan.model.SilverLoanApplication;
import com.example.Star.silverloan.repo.SilverLoanApplicationRepository;
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
@RequestMapping("/api/protected/silverloan")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class SilverLoanController {

    private final UserRepository userRepository;
    private final BankDetailsRepository bankDetailsRepository;
    private final SilverLoanApplicationRepository silverLoanApplicationRepository;
    private final EmailService emailService;

    @PostMapping("/apply")
    public ResponseEntity<?> applySilverLoan(
            @RequestParam("username") String username,
            @RequestParam("knNumber") String knNumber,
            @RequestParam("silverType") String silverType,
            @RequestParam("weight") Double weight,
            @RequestParam("placeBoughtFrom") String placeBoughtFrom,
            @RequestParam("jewelersName") String jewelersName,
            @RequestParam("jewelersAddress") String jewelersAddress,
            @RequestParam("silverPhoto") MultipartFile silverPhoto,
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
        if (!bankDetails.getKnNumber().equals(knNumber)
                || !bankDetails.getAccountNumber().equals(accountNumber)
                || !bankDetails.getIfscCode().equalsIgnoreCase(ifscCode)) {
            return ResponseEntity.badRequest().body("Bank details do not match registered records");
        }

        SilverLoanApplication loan = new SilverLoanApplication();
        loan.setUser(user);
        loan.setKnNumber(knNumber);
        loan.setSilverType(silverType);
        loan.setWeight(weight);
        loan.setPlaceBoughtFrom(placeBoughtFrom);
        loan.setJewelersName(jewelersName);
        loan.setJewelersAddress(jewelersAddress);

        try {
            byte[] imageBytes = silverPhoto.getBytes();
            String base64image = Base64.getEncoder().encodeToString(imageBytes);
            loan.setSilverPhoto(base64image);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to read silver photo file.");
        }

        loan.setAccountHolderName(accountHolderName);
        loan.setAccountType(accountType);
        loan.setAccountNumber(accountNumber);
        loan.setIfscCode(ifscCode);
        loan.setBankName(bankName);
        loan.setBranchName(branchName);
        loan.setDeclaration(declaration);

        String RID = "SRID" + UUID.randomUUID().toString().replace("-", "").substring(0, 10).toUpperCase();
        loan.setRequestID(RID);
        loan.setStatus("PENDING");

        silverLoanApplicationRepository.save(loan);

        String sub = "Silver Loan Application Submitted";
        String message = "Dear " + user.getUsername() + ",\n\n" +
                "Your silver loan application has been received successfully.\n" +
                "Request ID: " + RID + "\n" +
                "KN Number: " + knNumber + "\n" +
                "Weight: " + weight + " grams\n" +
                "Silver Type: " + silverType + "\n" +
                "Account Number: " + accountNumber + "\n" +
                "IFSC Code: " + ifscCode + "\n\n" +
                "Our team will review your request and contact you soon within 2 working days.\n\n" +
                "Regards,\nStar Finance";

        emailService.sendSimpleMail(user.getEmail(), sub, message);

        return ResponseEntity.ok("Silver loan application submitted successfully");
    }

    @GetMapping("/status/{username}")
    public ResponseEntity<?> checkExisting(@PathVariable String username) {
        Optional<SilverLoanApplication> app = silverLoanApplicationRepository.findByUser_Username(username);
        return app.isPresent()
                ? ResponseEntity.status(202).body("You have already submitted a silver loan application.")
                : ResponseEntity.ok("No previous application found");
    }

    @GetMapping("/photo/{id}")
    public ResponseEntity<byte[]> getSilverPhoto(@PathVariable Long id) {
        Optional<SilverLoanApplication> opt = silverLoanApplicationRepository.findById(id);
        if (opt.isEmpty() || opt.get().getSilverPhoto() == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            byte[] imageBytes = Base64.getDecoder().decode(opt.get().getSilverPhoto());
            return ResponseEntity.ok()
                    .header("Content-Type", "image/jpeg")
                    .body(imageBytes);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
