package com.example.proiectps1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/international")
    public String getInternationalPage() {
        // Returnează numele șablonului (fără extensie)
        return "international";
    }
}
