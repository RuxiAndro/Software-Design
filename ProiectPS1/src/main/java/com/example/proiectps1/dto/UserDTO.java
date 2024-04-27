package com.example.proiectps1.dto;

import com.example.proiectps1.model.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class UserDTO {//is used for representing user data when interacting with the client
    private Long id;
    private String username;
    private String password;
    private User.RoleType role;
    private String phoneNumber;
    private String email;

}
