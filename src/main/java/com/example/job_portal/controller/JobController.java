package com.example.job_portal.controller;

import com.example.job_portal.model.Job;
import com.example.job_portal.service.JobService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping
    public String listJobs(Model model, 
                           @RequestParam(required = false) String keyword,
                           @RequestParam(required = false) String location) {
        model.addAttribute("jobs", jobService.searchAndFilter(keyword, location));
        model.addAttribute("keyword", keyword);
        model.addAttribute("location", location);
        return "jobs";
    }
    
    @GetMapping("/new")
    public String createJobForm(Model model) {
        model.addAttribute("job", new Job());
        return "job-form";
    }

    @PostMapping
    public String saveJob(@ModelAttribute("job") Job job) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        jobService.saveJob(job, username);
        return "redirect:/jobs";
    }
}