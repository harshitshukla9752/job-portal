package com.example.job_portal.service;

import com.example.job_portal.model.Job;
import com.example.job_portal.model.User;
import com.example.job_portal.repository.JobRepository;
import com.example.job_portal.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class JobService {

    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    public JobService(JobRepository jobRepository, UserRepository userRepository) {
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
    }

    public List<Job> findAll() {
        return jobRepository.findAll();
    }

    public List<Job> searchByKeyword(String keyword) {
        return jobRepository.findByTitleContainingIgnoreCase(keyword);
    }

    public void saveJob(Job job, String username) {
        User employer = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Employer not found with username: " + username));
        job.setEmployer(employer);
        jobRepository.save(job);
    }
    public List<Job> searchAndFilter(String keyword, String location) {
        boolean hasKeyword = keyword != null && !keyword.isEmpty();
        boolean hasLocation = location != null && !location.isEmpty();

        if (hasKeyword && hasLocation) {
            return jobRepository.findByTitleContainingIgnoreCaseAndLocationContainingIgnoreCase(keyword, location);
        } else if (hasKeyword) {
            return jobRepository.findByTitleContainingIgnoreCase(keyword);
        } else if (hasLocation) {
            return jobRepository.findByLocationContainingIgnoreCase(location);
        } else {
            return jobRepository.findAll();
        }
    }
    // YEH NEW METHOD HAI JISKI WAJAH SE ERROR AA RAHA THA
    public Job findById(Long jobId) {
        return jobRepository.findById(jobId)
            .orElseThrow(() -> new RuntimeException("Job not found with id: " + jobId));
    }
}