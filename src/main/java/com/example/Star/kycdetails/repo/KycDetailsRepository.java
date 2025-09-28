package com.example.Star.kycdetails.repo;

import com.example.Star.kycdetails.model.KycDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KycDetailsRepository extends JpaRepository<KycDetails, Long> {
    Optional<KycDetails> findByUser_Username(String username);
}

