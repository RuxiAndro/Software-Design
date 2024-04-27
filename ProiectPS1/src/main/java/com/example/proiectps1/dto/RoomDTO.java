package com.example.proiectps1.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class RoomDTO {
    private Long id;
    private Long hotelId;
    private String type;
    private int capacity;
    private BigDecimal pricePerNight;
    private LocalDate availabilityStartDate;
    private LocalDate availabilityEndDate;
    private LocalDate currentReservationStartDate;
    private LocalDate currentReservationEndDate;
}
