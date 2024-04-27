package com.example.proiectps1.dto;

import com.example.proiectps1.model.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
public class UserCreationDTO { //is used for creating or registering new users
    private String username;
    private String password;
    private User.RoleType role;
    private String phoneNumber;
    private String email;

}
