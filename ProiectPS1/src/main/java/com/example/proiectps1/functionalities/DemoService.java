package com.example.proiectps1.functionalities;

import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class DemoService {

    public void dummyMethod() {
        // Your task logic goes here
        System.out.println("Scheduled task executed at: " + new Date());
    }
}