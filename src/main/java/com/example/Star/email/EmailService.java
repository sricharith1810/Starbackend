package com.example.Star.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendBankDetailsEmail(String toEmail, String name, String knNumber, String accountNumber, String ifscCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Your Star Bank Account Details");
        message.setText(
                "Dear " + name + ",\n\n" +
                        "Your KYC has been successfully verified.\n" +
                        "Here are your bank account details:\n\n" +
                        "KN Number: " + knNumber + "\n" +
                        "Account Number: " + accountNumber + "\n" +
                        "IFSC Code: " + ifscCode + "\n\n" +
                        "Please keep this information secure.\n\n" +
                        "Regards,\nStar Finance"
        );
        mailSender.send(message);
    }

    public void sendSimpleMail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("yourbank@example.com"); // Replace with actual sender email
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }
}
