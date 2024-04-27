package com.example.proiectps1.service.impl;

import com.example.proiectps1.dto.RoomCreationDTO;
import com.example.proiectps1.dto.RoomDTO;
import com.example.proiectps1.dto.UserCreationDTO;
import com.example.proiectps1.dto.UserDTO;
import com.example.proiectps1.mapper.RoomMapper;
import com.example.proiectps1.mapper.UserMapper;
import com.example.proiectps1.model.Hotel;
import com.example.proiectps1.model.Room;
import com.example.proiectps1.model.User;
import com.example.proiectps1.repository.HotelRepository;
import com.example.proiectps1.repository.RoomRepository;
import com.example.proiectps1.service.RoomService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;

    public RoomServiceImpl(RoomRepository roomRepository,HotelRepository hotelRepository) {
        this.roomRepository = roomRepository;
        this.hotelRepository = hotelRepository;
    }

    @Override
    public List<Room> findAll() {
        return (List<Room>) roomRepository.findAll();
    }

    @Override
    public RoomDTO saveRoom(RoomCreationDTO room) {
        Room entity = RoomMapper.toCreationEntity(room);
        Optional<Hotel> optionalHotel = hotelRepository.findById(room.getHotelId());
        if (optionalHotel.isPresent()) {
            Hotel hotel = optionalHotel.get();
            entity.setHotel(hotel);
            hotel.getRooms().add(entity);
            entity = roomRepository.save(entity);
            hotel=hotelRepository.save(hotel);
            return RoomMapper.toDto(entity);
        }
        else {
            throw new IllegalStateException("Hotelul cu ID-ul " + room.getHotelId() + " nu a putut fi găsit.");
        }
    }

   /* @Override
    public RoomDTO updateRoom(RoomDTO room)
    {
        boolean exists= roomRepository.findById(room.getId()).isPresent();//verifica daca contine o valoare
        if(exists) {
            Room entity = RoomMapper.toEntity(room);
            entity = roomRepository.save(entity);
            return RoomMapper.toDto(entity);

        }
        return null;
    }*/

    @Override
    public RoomDTO updateRoom(RoomDTO roomDTO) {
        Optional<Room> optionalRoom = roomRepository.findById(roomDTO.getId());
        if (optionalRoom.isPresent()) {
            Room room = optionalRoom.get();
            // Map the fields from the DTO to the entity
            room.setId(roomDTO.getId());
            room.setType(roomDTO.getType());
            room.setCapacity(roomDTO.getCapacity());
            room.setPricePerNight(roomDTO.getPricePerNight());
            // Convert LocalDate fields from String to LocalDate objects
            room.setAvailabilityStartDate(roomDTO.getAvailabilityStartDate());
            room.setAvailabilityEndDate(roomDTO.getAvailabilityEndDate());
            room.setCurrentReservationStartDate(roomDTO.getCurrentReservationStartDate() != null ? roomDTO.getCurrentReservationStartDate() : null);
            room.setCurrentReservationEndDate(roomDTO.getCurrentReservationEndDate() != null ? roomDTO.getCurrentReservationEndDate() : null);
            // Check if the hotel ID is provided in the DTO
            if (roomDTO.getHotelId() != null) {
                Optional<Hotel> optionalHotel = hotelRepository.findById(roomDTO.getHotelId());
                if (optionalHotel.isPresent()) {
                    Hotel hotel = optionalHotel.get();
                    room.setHotel(hotel);
                    // Add the room to the hotel's room list
                    hotel.getRooms().add(room);
                } else {
                    throw new IllegalStateException("Hotelul cu ID-ul " + roomDTO.getHotelId() + " nu a putut fi găsit.");
                }
            }
            // Save the updated room entity
            room = roomRepository.save(room);
            return RoomMapper.toDto(room);
        } else {
            throw new IllegalStateException("Room-ul cu ID-ul " + roomDTO.getId() + " nu a putut fi găsit.");
        }
    }




  /*  @Override
    public void deleteRoom(Long id)
    {
        roomRepository.deleteById(id);
    }*/

    @Override
    public boolean deleteRoom(Long id)
    {
        roomRepository.deleteById(id);
        return roomRepository.findById(id).isEmpty(); //ret true daca nu l-a mai gasit deci daca a fost sters
    }

    @Override
    public Room findRoomById(Long id)
    {
        return roomRepository.findById(id).orElseThrow();//face parte din clasa Optional ,va returna un obiect wrapped inside an Optional daca nu arunca exceptia NoSuchElementException
    }

    @Override
    public boolean isRoomAvailable(Room room, LocalDate startDate, LocalDate endDate) {
        LocalDate currentReservationStartDate = room.getCurrentReservationStartDate();
        LocalDate currentReservationEndDate = room.getCurrentReservationEndDate();

        return (currentReservationStartDate == null || endDate.isBefore(currentReservationStartDate)
                || startDate.isAfter(currentReservationEndDate));
    }



   /* @Override
    public boolean isRoomAvailable(Room room, Hotel hotel, LocalDate startDate, LocalDate endDate) {
        // Check if the room belongs to the specified hotel
        if (!room.getHotel().equals(hotel)) {
            throw new IllegalArgumentException("The room does not belong to the specified hotel.");
        }

        // Check if the requested period overlaps with the current reservation period
        LocalDate currentReservationStartDate = room.getCurrentReservationStartDate();
        LocalDate currentReservationEndDate = room.getCurrentReservationEndDate();

        if (currentReservationStartDate == null || currentReservationEndDate == null) {
            // If there is no current reservation, the room is available
            return true;
        }

        // Check if the requested period overlaps with the current reservation period
        boolean startDateBeforeReservationEnd = startDate.isBefore(currentReservationEndDate);
        boolean endDateAfterReservationStart = endDate.isAfter(currentReservationStartDate);

        return !startDateBeforeReservationEnd || !endDateAfterReservationStart;
    }*/
    @Override
    public Room findRoomIdByHotelIdAndRoomId(Long hotelId, Long roomId) {
        Room room = roomRepository.findByHotelIdAndId(hotelId, roomId);
        if (room != null) {
            return room;
        } else {
            throw new EntityNotFoundException("Camera cu ID-ul " + roomId + " nu a fost găsită în cadrul hotelului cu ID-ul " + hotelId);
        }
    }

}
