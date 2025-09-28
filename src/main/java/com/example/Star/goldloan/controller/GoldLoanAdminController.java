package com.example.Star.goldloan.controller;

import com.example.Star.authentication.model.User;
import com.example.Star.email.EmailService;
import com.example.Star.goldloan.model.GoldLoanApplication;
import com.example.Star.goldloan.repo.GoldLoanApplicationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/admin")
public class GoldLoanAdminController {

    @Autowired
    private GoldLoanApplicationRepository goldLoanApplicationRepository;
    @Autowired private EmailService emailService;

    @PostMapping("/goldloan/approve/{id}")
    public ResponseEntity<?> approveGoldLoan(@PathVariable Long id) {
        int offerAmount = ThreadLocalRandom.current().nextInt(100_000, 300_001);
//        SimpleJpaRepository<T, Long> goldLoanApplicationRepository;
        Optional<GoldLoanApplication> opt = goldLoanApplicationRepository.findById(id);
        if (opt.isEmpty()) return ResponseEntity.badRequest().body("Application not found");

        GoldLoanApplication app = opt.get();
        app.setStatus("APPROVED");
        goldLoanApplicationRepository.save(app);

        User user = app.getUser();
        String subject = "About your gold loan information";
        String body =
                "Dear " + user.getUsername() + ",\n\n" +
                        "Your gold loan application (Request ID: " + app.getRequestID() + ") has been successfully evaluated.\n\n" +

                        "✨ Application Summary:\n" +
                        "• KN Number            : " + app.getKnNumber() + "\n" +
                        "• Gold Type            : " + app.getGoldType() + "\n" +
                        "• Gold Weight          : " + app.getWeight() + " grams\n" +
                        "• Purchased From       : " + app.getPlaceBoughtFrom() + "\n" +
                        "• Jeweler's Name       : " + app.getJewelersName() + "\n" +
                        "• Jeweler's Address    : " + app.getJewelersAddress() + "\n\n" +

                        "🏦 Registered Bank Details:\n" +
                        "• Account Holder Name  : " + app.getAccountHolderName() + "\n" +
                        "• Account Number       : " + app.getAccountNumber() + "\n" +
                        "• Account Type         : " + app.getAccountType() + "\n" +
                        "• IFSC Code            : " + app.getIfscCode() + "\n" +
                        "• Bank Name            : " + app.getBankName() + "\n" +
                        "• Branch Name          : " + app.getBranchName() + "\n\n" +

                        "💰 Based on our evaluation, we are offering you a loan amount of ₹" + offerAmount + ".\n\n" +

                        "Please choose one of the following options:\n\n" +
                        "✅ *Accept Offer* — The loan amount will be disbursed to your registered bank account.\n" +
                        "❌ *Reject Offer* — You may collect your gold from the nearest Star Finance branch (₹500 processing fee applies).\n\n" +

                        "⚠️ *Important*: Please respond within 3 working days. This offer will automatically expire thereafter.\n\n" +

                        "Thank you for trusting Star Finance.\n\n" +
                        "Regards,\n" +
                        "Star Finance Loan Department";


        emailService.sendSimpleMail(user.getEmail(), subject, body);

        return ResponseEntity.ok("Gold loan application approved and email sent.");
    }

}
