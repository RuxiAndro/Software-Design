package com.example.proiectps1.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Builder
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)//foreign key se cheama user_id in tabela asta
    //nu pot avea foreign key pe null ,adica fiecare rezervare are neaparat un user asociat
    private User user; //user_id este cheie straina in tabela Booking pt cheia primara din tabela User

    @JsonManagedReference

    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)//foreign key este hotel_id
    private Hotel hotel;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    // Fields to represent the reservation interval
    @Column(name = "reservation_start_date", nullable = false)
    private LocalDate reservationStartDate;

    @Column(name = "reservation_end_date", nullable = false)
    private LocalDate reservationEndDate;

    //data la care am facut rezervarea
    @Column(name = "booking_date", nullable = false)
    private LocalDate bookingDate;

    //ora
    @Column(name = "booking_time", nullable = false)
    private LocalTime bookingTime;

    @Column(name = "total_cost", nullable = false)
    private BigDecimal totalCost;

    public Booking(User user, Hotel hotel, LocalDate bookingDate, LocalTime bookingTime, LocalDate reservationStartDate, LocalDate reservationEndDate, BigDecimal totalCost) {
        this.user = user;
        this.hotel = hotel;
        this.bookingDate = bookingDate;
        this.bookingTime = bookingTime;
        this.reservationStartDate = reservationStartDate;
        this.reservationEndDate = reservationEndDate;
        this.totalCost = totalCost;
    }

    public Booking(User user, Hotel hotel, LocalDate bookingDate, LocalTime bookingTime, BigDecimal totalCost) {
        this.user = user;
        this.hotel = hotel;
        this.bookingDate = bookingDate;
        this.bookingTime = bookingTime;
        this.totalCost = totalCost;
    }


    public Room getRoom() {
        if (hotel != null) {
            return hotel.getRooms().stream()
                    .filter(room -> room.getId().equals(this.getHotel().getId())) // Comparăm cu ID-ul hotelului
                    .findFirst()
                    .orElse(null);
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", user=" + (user != null ? user.getId() : null) +
                ", hotel=" + (hotel != null ? hotel.getId() : null) +
                ", reservationStartDate=" + reservationStartDate +
                ", reservationEndDate=" + reservationEndDate +
                ", bookingDate=" + bookingDate +
                ", bookingTime=" + bookingTime +
                '}';
    }

   /* public BigDecimal totalCost(LocalDate startDate, LocalDate endDate, BigDecimal pricePerNight) {
        long numberOfDays = ChronoUnit.DAYS.between(startDate, endDate);
        if (numberOfDays == 0) {
            numberOfDays = 1;
        } else {
            numberOfDays++;
        }
        return pricePerNight.multiply(BigDecimal.valueOf(numberOfDays));
    }*/

    public Long getUserId() {
        return user != null ? user.getId() : null;
    }

    public Long getHotelId() {
        return hotel != null ? hotel.getId() : null;
    }

    public Long getRoomId() {
        return room != null ? room.getId() : null;
    }

}
