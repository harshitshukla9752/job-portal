package com.example.job_portal.repository;

import com.example.job_portal.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByTitleContainingIgnoreCase(String keyword);
    List<Job> findByTitleContainingIgnoreCaseAndLocationContainingIgnoreCase(String keyword, String location);

    // Methods for when only one filter is provided
    List<Job> findByLocationContainingIgnoreCase(String location);
}