package com.example.job_portal.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                          Authentication authentication) throws IOException, ServletException {

        // Get the user's roles (authorities)
        for (GrantedAuthority auth : authentication.getAuthorities()) {
            // If the user has the 'ROLE_EMPLOYER', redirect them to the job posting page
            if ("ROLE_EMPLOYER".equals(auth.getAuthority())) {
                response.sendRedirect("/jobs/new");
                return; // Exit after redirecting
            }
        }

        // For any other role (like 'ROLE_APPLICANT'), redirect to the main job listings page
        response.sendRedirect("/jobs");
    }
}