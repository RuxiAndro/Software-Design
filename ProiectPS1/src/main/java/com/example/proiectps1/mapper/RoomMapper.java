package com.example.proiectps1.mapper;

import com.example.proiectps1.dto.RoomCreationDTO;
import com.example.proiectps1.dto.RoomDTO;
import com.example.proiectps1.model.Room;

public class RoomMapper {
    public static Room toEntity(RoomDTO dto) {
        return Room.builder()
                .id(dto.getId())
                .type(dto.getType())
                .capacity(dto.getCapacity())
                .pricePerNight(dto.getPricePerNight())
                .availabilityStartDate(dto.getAvailabilityStartDate())
                .availabilityEndDate(dto.getAvailabilityEndDate())
                .currentReservationStartDate(dto.getCurrentReservationStartDate())
                .currentReservationEndDate(dto.getCurrentReservationEndDate())
                .build();
    }

    public static RoomDTO toDto(Room room) {
        return RoomDTO.builder()
                .id(room.getId())
                .type(room.getType())
                .capacity(room.getCapacity())
                .pricePerNight(room.getPricePerNight())
                .availabilityStartDate(room.getAvailabilityStartDate())
                .availabilityEndDate(room.getAvailabilityEndDate())
                .currentReservationStartDate(room.getCurrentReservationStartDate())
                .currentReservationEndDate(room.getCurrentReservationEndDate())
                .build();
    }

    public static Room toCreationEntity(RoomCreationDTO dto) {
        return Room.builder()
                .type(dto.getType())
                .capacity(dto.getCapacity())
                .pricePerNight(dto.getPricePerNight())
                .availabilityStartDate(dto.getAvailabilityStartDate())
                .availabilityEndDate(dto.getAvailabilityEndDate())
                .build();
    }
}
