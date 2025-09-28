package com.example.Star.bankdetails.controller;

import com.example.Star.bankdetails.model.BankDetails;
import com.example.Star.bankdetails.service.BankDetailsService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bank")
@CrossOrigin(origins = "http://localhost:5173")
public class BankController {
    @Autowired
    private BankDetailsService bankDetailsService;

    @GetMapping("/details/{username}")
    public ResponseEntity<BankDetails> getBankdetails(@PathVariable String username){
            BankDetails details =   bankDetailsService.getBankDetailsByUsername(username);
            if (details != null) {
            return ResponseEntity.ok(details);
            } else {
            return ResponseEntity.notFound().build();
            }
    }
}
