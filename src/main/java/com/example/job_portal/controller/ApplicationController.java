package com.example.job_portal.controller;

import com.example.job_portal.model.Application;
import com.example.job_portal.service.ApplicationService;
import com.example.job_portal.service.JobService;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ApplicationController {

    private final ApplicationService applicationService;
    private final JobService jobService;


    public ApplicationController(ApplicationService applicationService, JobService jobService) {
        this.applicationService = applicationService;
        this.jobService = jobService;
    }

    @PostMapping("/apply/{jobId}")
    public String applyForJob(@PathVariable Long jobId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        applicationService.applyForJob(jobId, username);
        // REDIRECT KO CHANGE KIYA GAYA HAI
        return "redirect:/my-applications?applied_success";
    }

    @GetMapping("/my-applications")
    public String getUserApplications(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();

        List<Application> applications = applicationService.findApplicationsByUser(username);
        model.addAttribute("applications", applications);
        return "my-applications";
    }
    
    // (Employer ke liye baki methods waise hi rahenge)
    @GetMapping("/jobs/{jobId}/applications")
    public String showApplicationsForJob(@PathVariable Long jobId, Model model) {
        model.addAttribute("job", jobService.findById(jobId));
        model.addAttribute("applications", applicationService.findApplicationsByJobId(jobId));
        return "view-applications";
    }

    @PostMapping("/applications/{id}/update")
    public String updateApplicationStatus(@PathVariable Long id, @RequestParam String status) {
        Application updatedApplication = applicationService.updateStatus(id, status);
        return "redirect:/jobs/" + updatedApplication.getJob().getId() + "/applications";
    }
}