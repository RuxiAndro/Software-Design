package com.example.proiectps1.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@RestController
@RequestMapping("/api/i18n")
public class I18nController {

    @GetMapping("/{locale}")
    public ResponseEntity<Map<String, String>> getTranslations(@PathVariable String locale) {
        // Calea către fișierul de proprietăți
        String resourcePath = "messages_" + locale + ".properties";
        if (locale.equals("en")) {
            resourcePath = "messages.properties";
        }

        Resource resource = new ClassPathResource(resourcePath);
        Properties properties = new Properties();
        Map<String, String> translations = new HashMap<>();

        try {
            // Încărcați proprietățile din fișier
            properties.load(resource.getInputStream());

            // Convertiți proprietățile într-o hartă
            for (String key : properties.stringPropertyNames()) {
                translations.put(key, properties.getProperty(key));
            }

            // Returnați răspunsul cu antetul Content-Type setat la application/json
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(translations);
        } catch (IOException e) {
            // În cazul unei erori, returnați cod de stare HTTP 500
            System.err.println("Error loading properties file: " + e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }


}
