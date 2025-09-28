package com.example.Star.goldloan.repo;

import com.example.Star.goldloan.model.GoldLoanApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GoldLoanApplicationRepository extends JpaRepository<GoldLoanApplication, Long> {
    Optional<GoldLoanApplication> findByUser_UsernameAndStatus(String username, String status);
    long countByStatus(String status);

}
