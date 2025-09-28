package com.example.Star.silverloan.repo;

import com.example.Star.silverloan.model.SilverLoanApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SilverLoanApplicationRepository extends JpaRepository<SilverLoanApplication, Long> {
    Optional<SilverLoanApplication> findByUser_Username(String username);
    long countByStatus(String status);

}
