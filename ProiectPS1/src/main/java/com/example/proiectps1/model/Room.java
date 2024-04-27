package com.example.proiectps1.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Builder
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private int capacity;

    @Column(nullable = false)
    private BigDecimal pricePerNight;

    @Column(nullable = false)
    private LocalDate availabilityStartDate;

    @Column(nullable = false)
    private LocalDate availabilityEndDate;

    // Additional fields to represent the current reservation
    @Column(name = "current_reservation_start_date")
    private LocalDate currentReservationStartDate;

    @Column(name = "current_reservation_end_date")
    private LocalDate currentReservationEndDate;


    public Room(Hotel hotel, String type, int capacity, BigDecimal pricePerNight,
                LocalDate availabilityStartDate, LocalDate availabilityEndDate,
                LocalDate currentReservationStartDate, LocalDate currentReservationEndDate) {
      //  this.hotel = hotel;
        this.type = type;
        this.capacity = capacity;
        this.pricePerNight = pricePerNight;
        this.availabilityStartDate = availabilityStartDate;
        this.availabilityEndDate = availabilityEndDate;
        this.currentReservationStartDate = currentReservationStartDate;
        this.currentReservationEndDate = currentReservationEndDate;
    }

    public Room(Hotel hotel, String type, int capacity, BigDecimal pricePerNight,
                LocalDate availabilityStartDate, LocalDate availabilityEndDate
               ) {
        this.hotel = hotel;
        this.type = type;
        this.capacity = capacity;
        this.pricePerNight = pricePerNight;
        this.availabilityStartDate = availabilityStartDate;
        this.availabilityEndDate = availabilityEndDate;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", hotel=" + hotel +
                ", type='" + type + '\'' +
                ", capacity=" + capacity +
                ", pricePerNight=" + pricePerNight +
                ", availabilityStartDate=" + availabilityStartDate +
                ", availabilityEndDate=" + availabilityEndDate +
                ", currentReservationStartDate=" + currentReservationStartDate +
                ", currentReservationEndDate=" + currentReservationEndDate +
                '}';
    }
}