package com.example.job_portal.service;

import com.example.job_portal.model.Application;
import com.example.job_portal.model.Job;
import com.example.job_portal.model.User;
import com.example.job_portal.repository.ApplicationRepository;
import com.example.job_portal.repository.JobRepository;
import com.example.job_portal.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional; // Yeh import add karein


@Service
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final JobRepository jobRepository;

    public ApplicationService(ApplicationRepository applicationRepository, UserRepository userRepository, JobRepository jobRepository) {
        this.applicationRepository = applicationRepository;
        this.userRepository = userRepository;
        this.jobRepository = jobRepository;
    }

    public void applyForJob(Long jobId, String username) {
        User applicant = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        Application application = new Application();
        application.setApplicant(applicant);
        application.setJob(job);
        application.setAppliedAt(LocalDateTime.now());
        application.setStatus("Applied"); 
        applicationRepository.save(application);
    }

    public List<Application> findApplicationsByUser(String username) {
        User applicant = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return applicationRepository.findByApplicant(applicant);
    }

    public List<Application> findApplicationsByJobId(Long jobId) {
        return applicationRepository.findByJobId(jobId);
    }
    
    public Application updateStatus(Long applicationId, String newStatus) {
        Application application = applicationRepository.findById(applicationId)
            .orElseThrow(() -> new RuntimeException("Application not found"));
        application.setStatus(newStatus);
        return applicationRepository.save(application);
    }
}
