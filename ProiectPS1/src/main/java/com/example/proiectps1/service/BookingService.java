package com.example.proiectps1.service;

import com.example.proiectps1.dto.BookingCreationDTO;
import com.example.proiectps1.dto.BookingDTO;
import com.example.proiectps1.dto.RoomDTO;
import com.example.proiectps1.exceptions.ApiExceptionResponse;
import com.example.proiectps1.model.Booking;
import com.example.proiectps1.model.Booking;
import com.example.proiectps1.model.Hotel;
import com.example.proiectps1.model.Room;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
public interface BookingService {

    List<Booking> findAll();

    BookingDTO saveBooking(BookingCreationDTO booking);

    //Booking updateBooking(Booking booking);
    BookingDTO updateBooking(BookingDTO booking);

    boolean deleteBooking(Long id);

    Booking findBookingById(Long id);

    List<Room> findBookedRooms();

    BigDecimal calculateTotalCost(LocalDate startDate, LocalDate endDate, BigDecimal pricePerNight);

    BookingDTO makeReservation(Long hotelId, Long roomId, Long userId, BookingCreationDTO bookingDetails);

    List<BookingDTO> getBookingsToConfirm();
}
