package com.example.proiectps1.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.proiectps1.repository.UserRepository;

@Component
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void initialize(UniqueUsername constraintAnnotation) {
        // Nu este necesar să faci nimic aici, dar poți adăuga inițializări specifice dacă este necesar
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        // Verifică dacă username există deja în baza de date
        return !userRepository.existsByUsername(username);
    }
}

