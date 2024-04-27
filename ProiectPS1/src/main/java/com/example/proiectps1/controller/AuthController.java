package com.example.proiectps1.controller;

import com.example.proiectps1.dto.AuthDTO;
import com.example.proiectps1.dto.SuccessfulLoginDTO;
import com.example.proiectps1.exceptions.ApiExceptionResponse;
import com.example.proiectps1.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/login")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService){
        this.userService=userService;
    }

   /* @PostMapping
    public ResponseEntity login(@RequestBody AuthDTO auth) throws ApiExceptionResponse {
        return ResponseEntity.status(HttpStatus.OK).body(userService.login(auth));
    }*/

    @PostMapping
    public ResponseEntity login(@RequestBody AuthDTO auth) throws ApiExceptionResponse {
        SuccessfulLoginDTO loginResponse = userService.login(auth);
        Long userId = loginResponse.getId(); // Obțineți ID-ul utilizatorului din răspunsul serviciului

        // Adăugați ID-ul utilizatorului în răspunsul JSON
        Map<String, Object> response = new HashMap<>();
        response.put("userId", userId);
        response.put("loginResponse", loginResponse);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
