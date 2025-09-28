package com.example.Star.dashboard.controller;

import com.example.Star.dashboard.dto.ApplicationStatsDTO;
import com.example.Star.goldloan.repo.GoldLoanApplicationRepository;
import com.example.Star.silverloan.repo.SilverLoanApplicationRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AdminDashboardController {

    private final GoldLoanApplicationRepository goldLoanRepo;
    private final SilverLoanApplicationRepository silverLoanRepo;

    @GetMapping("/overview-stats")
    public ApplicationStatsDTO getOverviewStats() {
        long totalGold = goldLoanRepo.count();
        long totalSilver = silverLoanRepo.count();

        long goldPending = goldLoanRepo.countByStatus("PENDING");
        long silverPending = silverLoanRepo.countByStatus("PENDING");

        long goldApproved = goldLoanRepo.countByStatus("APPROVED");
        long silverApproved = silverLoanRepo.countByStatus("APPROVED");

        long totalApplications = totalGold + totalSilver;
        long pendingReview = goldPending + silverPending;
        long approved = goldApproved + silverApproved;

        return new ApplicationStatsDTO(totalApplications, pendingReview, approved);
    }
}
