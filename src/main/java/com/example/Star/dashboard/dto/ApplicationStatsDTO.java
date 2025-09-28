package com.example.Star.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApplicationStatsDTO {
    private long totalApplications;
    private long pendingReview;
    private long approved;
}
