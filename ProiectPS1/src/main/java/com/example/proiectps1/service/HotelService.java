package com.example.proiectps1.service;

import com.example.proiectps1.dto.HotelCreationDTO;
import com.example.proiectps1.dto.HotelDTO;
import com.example.proiectps1.dto.RoomCreationDTO;
import com.example.proiectps1.dto.RoomDTO;
import com.example.proiectps1.exceptions.ApiExceptionResponse;
import com.example.proiectps1.model.Hotel;
import com.example.proiectps1.model.Room;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public interface HotelService {
    List<Hotel> findAll();

   // HotelDTO saveHotel(HotelCreationDTO hotel);

    // Room updateRoom(Room room);
    HotelDTO updateHotel(HotelDTO hotel);

    HotelDTO updateHotelByName(String name, HotelDTO hotelDTO);

   // HotelDTO updateHotelByName(String name, HotelDTO hotelDTO);

    boolean deleteHotel(Long id);
   // boolean deleteHotel(String name);

    Hotel findHotelById(Long id);

    Hotel findHotelByName(String name) throws ApiExceptionResponse;

    HotelDTO saveHotel(HotelCreationDTO hotel, Long ownerId);

    List<Hotel> findHotelsByOwnerId(Long ownerId);
   // List<Hotel> findHotelsByLocation(String loction);
   List<Hotel> findHotelsByLocation(String location, LocalDate startDate, LocalDate endDate, int guests);
}
