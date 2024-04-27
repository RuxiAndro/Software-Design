package com.example.proiectps1.functionalities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender)
    {
        this.mailSender=mailSender;
    }

    @Async
    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

    public void sendTestEmail() {
        String to = "androruxi@gmail.com"; // Adresa de email către care vei trimite testul
        String subject = "Test Email";
        String body = "Acesta este un email de test.";

        sendEmail(to, subject, body);
    }
}
