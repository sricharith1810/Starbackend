package com.example.Star.branch.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "branches")
@Data
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String location; // e.g., "Chennai"

    @Column(unique = true)
    private String code; // e.g., "34009"
}
