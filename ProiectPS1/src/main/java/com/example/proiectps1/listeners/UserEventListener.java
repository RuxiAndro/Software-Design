package com.example.proiectps1.listeners;

import com.example.proiectps1.functionalities.EmailService;
import com.example.proiectps1.model.User;
import jakarta.persistence.PostPersist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserEventListener {
    private EmailService emailService;

    public UserEventListener() {
    }

    @Autowired
    public UserEventListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostPersist
    public void sendWelcomeEmail(User user) {

        //String to = user.getEmail();
        String to = "androruxi@gmail.com";


        String subject = "Welcome to Our Booking Application!";


        String body = "Hi " + user.getUsername() + ",\n\nWelcome to our booking application!";


        emailService.sendEmail(to, subject, body);
    }
}
