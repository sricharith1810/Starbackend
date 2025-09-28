package com.example.Star.kycdetails.repo;

import com.example.Star.kycdetails.model.KycVerification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KycVerificationRepository extends JpaRepository<KycVerification, Long> {
    Optional<KycVerification> findByAadhaarAndPan(String aadhaar, String pan);
}
