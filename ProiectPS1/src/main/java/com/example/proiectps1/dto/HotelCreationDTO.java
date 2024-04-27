package com.example.proiectps1.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class HotelCreationDTO {
    private String name;
    private String description;
    private String location;
    private int numberOfRooms;
    private Long ownerId;
    private String image;
}
