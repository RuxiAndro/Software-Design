package com.example.proiectps1.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class RoomCreationDTO {
    private Long hotelId;
    private String type;
    private int capacity;
    private BigDecimal pricePerNight;
    private LocalDate availabilityStartDate;
    private LocalDate availabilityEndDate;
}
