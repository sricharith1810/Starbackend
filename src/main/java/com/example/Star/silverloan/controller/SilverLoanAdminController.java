package com.example.Star.silverloan.controller;

import com.example.Star.authentication.model.User;
import com.example.Star.email.EmailService;
import com.example.Star.silverloan.model.SilverLoanApplication;
import com.example.Star.silverloan.repo.SilverLoanApplicationRepository;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@CrossOrigin
@RestController
@RequestMapping("/api/admin")
public class SilverLoanAdminController {
    
    @Autowired private SilverLoanApplicationRepository silverLoanApplicationRepository;
    @Autowired private EmailService emailService;
    
    @PostMapping("/silverloan/approve/{id}")
    public ResponseEntity<?> approveSilverLoan(@PathVariable Long id) {
        int offerAmount = ThreadLocalRandom.current().nextInt(20_000, 150_000);

//        SimpleJpaRepository<T, Long> silverLoanApplicationRepository;
        Optional<SilverLoanApplication> opt = silverLoanApplicationRepository.findById(id);
        if (opt.isEmpty()) return ResponseEntity.badRequest().body("Application not found");

        SilverLoanApplication app = opt.get();
        app.setStatus("APPROVED");
        silverLoanApplicationRepository.save(app);

        User user = app.getUser();
        String subject = "About your silver loan information";
        String body =

                "Dear " + user.getUsername() + ",\n\n" +

                        "Your silver loan application (Request ID: " + app.getRequestID() + ") has been successfully evaluated.\n\n" +



                        "✨ Application Summary:\n" +

                        "• KN Number            : " + app.getKnNumber() + "\n" +

                        "• Silver Type          : " + app.getSilverType() + "\n" +

                        "• Silver Weight        : " + app.getWeight() + " grams\n" +

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

                        "✅ Accept Offer — The loan amount will be disbursed to your registered bank account.\n" +

                        "❌ Reject Offer — You may collect your silver from the nearest Star Finance branch (₹500 processing fee applies).\n\n" +



                        "⚠ Important: Please respond within 3 working days. This offer will automatically expire thereafter.\n\n" +



                        "Thank you for trusting Star Finance.\n\n" +

                        "Regards,\n" +

                        "Star Finance Loan Department";

        emailService.sendSimpleMail(user.getEmail(), subject, body);

        return ResponseEntity.ok("Silver loan application approved and email sent.");
    }

}
