package com.example.proiectps1.mapper;

import com.example.proiectps1.dto.UserCreationDTO;
import com.example.proiectps1.dto.UserDTO;
import com.example.proiectps1.model.User;

public class UserMapper {
    public static User toEntity(UserDTO dto) {
        return User.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .role(dto.getRole())
                .id(dto.getId())
                .phoneNumber(dto.getPhoneNumber())
                .email(dto.getEmail())
                .build();
    }

    public static UserDTO toDto(User user) {
        return UserDTO.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRole())
                .id(user.getId())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .build();
    }

    public static User toCreationEntity(UserCreationDTO dto) {
        return User.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .role(dto.getRole())
                .phoneNumber(dto.getPhoneNumber())
                .email(dto.getEmail())
                .build();
    }
}
