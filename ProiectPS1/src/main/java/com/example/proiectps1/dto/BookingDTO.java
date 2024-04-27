package com.example.proiectps1.dto;

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
@ToString
public class BookingDTO {
    private Long id;
    private Long userId;
    private Long hotelId;
    private Long roomId;
    private LocalDate reservationStartDate;
    private LocalDate reservationEndDate;
    private LocalDate bookingDate;
    private LocalTime bookingTime;
    private BigDecimal totalCost;

    @Override
    public String toString() {
        return "BookingDTO{" +
                "id=" + id +
                ", userId=" + userId +
                ", hotelId=" + hotelId +
                ", roomId=" + roomId +
                ", reservationStartDate=" + reservationStartDate +
                ", reservationEndDate=" + reservationEndDate +
                ", bookingDate=" + bookingDate +
                ", bookingTime=" + bookingTime +
                ", totalCost=" + totalCost +
                '}';
    }
}
