package com.example.proiectps1.dto;

import com.example.proiectps1.model.User;
import com.example.proiectps1.validators.UniqueUsername;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
public class UserCreationDTO { //is used for creating or registering new users
      @NotBlank(message = "Username is required")
      @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters long")
      @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username can only contain letters, numbers, and underscores")
      @UniqueUsername(message = "Username already exists")
    private String username;

    private String password;
    private User.RoleType role;
    private String phoneNumber;
    private String email;

}
