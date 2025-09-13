package com.example.job_portal.repository;

import com.example.job_portal.model.Application;
import com.example.job_portal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByApplicant(User applicant);
    List<Application> findByJobId(Long jobId);
}