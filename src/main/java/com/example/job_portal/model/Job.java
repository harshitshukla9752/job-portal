package com.example.job_portal.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "jobs")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;
    
    @Lob // For longer text fields
    private String description;
    
    private String companyName;
    private String location;

    @ManyToOne(fetch = FetchType.LAZY)
    // CHANGE THIS LINE: The database column is 'posted_by_id', not 'employer_id'.
    @JoinColumn(name = "posted_by_id", nullable = false)
    private User employer;
}