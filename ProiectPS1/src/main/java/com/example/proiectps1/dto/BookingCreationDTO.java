package com.example.proiectps1.dto;

import com.example.proiectps1.model.Booking;
import com.example.proiectps1.model.Room;
import com.example.proiectps1.model.User;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BookingCreationDTO {
    private Long userId;
    private Long hotelId;
    private Long roomId;
    private LocalDate reservationStartDate;
    private LocalDate reservationEndDate;
    private LocalDate bookingDate;
    private LocalTime bookingTime;
    private BigDecimal totalCost;
}
