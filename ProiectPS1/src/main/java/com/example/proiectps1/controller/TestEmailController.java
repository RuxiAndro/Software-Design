package com.example.proiectps1.controller;

import com.example.proiectps1.functionalities.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test-email")
public class TestEmailController {

    private final EmailService emailService;

    @Autowired
    public TestEmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping
    public ResponseEntity<String> sendTestEmail() {
        emailService.sendTestEmail();
        return ResponseEntity.ok("Test email sent successfully.");
    }
}