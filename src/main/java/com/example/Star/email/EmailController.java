package com.example.Star.email;

import com.example.Star.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
@CrossOrigin(origins = "http://localhost:5173")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/email")
    public String testEmail(
            @RequestParam String to,
            @RequestParam String username,
            @RequestParam String knNumber,
            @RequestParam String accountNumber,
            @RequestParam String ifscCode
    ) {
        emailService.sendBankDetailsEmail(to, username, knNumber, accountNumber, ifscCode);
        return "Email sent successfully to " + to;
    }
}
