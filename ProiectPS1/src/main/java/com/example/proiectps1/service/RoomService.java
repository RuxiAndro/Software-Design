package com.example.proiectps1.service;

import com.example.proiectps1.dto.RoomCreationDTO;
import com.example.proiectps1.dto.RoomDTO;
import com.example.proiectps1.model.Hotel;
import com.example.proiectps1.model.Room;
import com.example.proiectps1.model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public interface RoomService {
   // Room saveRoom(Room room);
   List<Room> findAll();
    RoomDTO saveRoom(RoomCreationDTO room);

   // Room updateRoom(Room room);
    RoomDTO updateRoom(RoomDTO room);

    boolean deleteRoom(Long id);

    Room findRoomById(Long id);

   // boolean isRoomAvailable(Room room, Hotel hotel, LocalDate startDate, LocalDate endDate);

    boolean isRoomAvailable(Room room, LocalDate startDate, LocalDate endDate);
   // boolean isRoomAvailable(Hotel hotel, List<Room> rooms, LocalDate startDate, LocalDate endDate);

    Room findRoomIdByHotelIdAndRoomId(Long hotelId, Long roomId);
}
