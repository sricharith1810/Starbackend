package com.example.Star.bankdetails.repo;

import com.example.Star.bankdetails.model.BankDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BankDetailsRepository extends JpaRepository<BankDetails, Long> {
    boolean existsByAccountNumber(String accountNumber);
    boolean existsByKnNumber(String knNumber);
    Optional<BankDetails> findByUser_Username(String username);

}
