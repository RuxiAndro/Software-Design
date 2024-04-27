package com.example.proiectps1.dto;

import com.example.proiectps1.model.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SuccessfulLoginDTO {
    private Long id;
    private User.RoleType role;
}
