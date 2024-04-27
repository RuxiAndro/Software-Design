package com.example.proiectps1.mapper;

import com.example.proiectps1.dto.BookingCreationDTO;
import com.example.proiectps1.dto.BookingDTO;
import com.example.proiectps1.model.Booking;
import com.example.proiectps1.model.Hotel;
import com.example.proiectps1.model.Room;
import com.example.proiectps1.model.User;
import com.example.proiectps1.repository.HotelRepository;
import com.example.proiectps1.repository.RoomRepository;
import com.example.proiectps1.repository.UserRepository;
import com.example.proiectps1.service.BookingService;
import com.example.proiectps1.service.HotelService;
import com.example.proiectps1.service.RoomService;
import com.example.proiectps1.service.UserService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class BookingMapper {
    public static Booking toEntity(BookingDTO dto, UserRepository userRepository, HotelRepository hotelRepository, RoomRepository roomRepository) {
        Booking booking = new Booking();
        booking.setId(dto.getId());
        booking.setReservationStartDate(dto.getReservationStartDate());
        booking.setReservationEndDate(dto.getReservationEndDate());
        booking.setBookingDate(dto.getBookingDate());
        booking.setBookingTime(dto.getBookingTime());
        booking.setTotalCost(dto.getTotalCost());

        var user = userRepository.findById(dto.getUserId());
        booking.setUser(user.get());

        var hotel = hotelRepository.findById(dto.getHotelId());
        booking.setHotel(hotel.get());

        var room = roomRepository.findById(dto.getRoomId());
        booking.setRoom(room.get());

        return booking;
    }

    public static BookingDTO toDto(Booking booking) {
        BookingDTO dto = new BookingDTO();
        dto.setId(booking.getId());
        dto.setReservationStartDate(booking.getReservationStartDate());
        dto.setReservationEndDate(booking.getReservationEndDate());
        dto.setBookingDate(booking.getBookingDate());
        dto.setBookingTime(booking.getBookingTime());
        dto.setTotalCost(booking.getTotalCost());

        if (booking.getHotel() != null) {
            dto.setHotelId(booking.getHotel().getId());
        } else {
            dto.setHotelId(null);
        }

        if (booking.getRoom() != null) {
            dto.setRoomId(booking.getRoom().getId());
        } else {
            dto.setRoomId(null);
        }

        if (booking.getUser() != null) {
            dto.setUserId(booking.getUser().getId());
        } else {
            dto.setUserId(null);
        }

        return dto;
    }

    public static Booking toCreationEntity(BookingCreationDTO dto, UserRepository userRepository, HotelRepository hotelRepository, RoomRepository roomRepository) {
        Booking booking = new Booking();
        booking.setReservationStartDate(dto.getReservationStartDate());
        booking.setReservationEndDate(dto.getReservationEndDate());
        booking.setBookingDate(dto.getBookingDate());
        booking.setBookingTime(dto.getBookingTime());
        booking.setTotalCost(dto.getTotalCost());

        var user = userRepository.findById(dto.getUserId());
        booking.setUser(user.get());

        var hotel = hotelRepository.findById(dto.getHotelId());
        booking.setHotel(hotel.get());

        var room = roomRepository.findById(dto.getRoomId());
        booking.setRoom(room.get());


        return booking;
    }

}
