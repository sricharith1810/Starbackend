package com.example.Star.branch.repo;

import com.example.Star.branch.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BranchRepository extends JpaRepository<Branch, Long> {
    Optional<Branch> findByLocation(String location);
}
