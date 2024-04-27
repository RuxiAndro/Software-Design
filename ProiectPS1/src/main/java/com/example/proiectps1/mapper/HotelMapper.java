package com.example.proiectps1.mapper;

import com.example.proiectps1.dto.HotelCreationDTO;
import com.example.proiectps1.dto.HotelDTO;
import com.example.proiectps1.model.Hotel;
import com.example.proiectps1.repository.HotelRepository;
import com.example.proiectps1.repository.UserRepository;

public class HotelMapper {

    public static Hotel toEntity(HotelDTO dto, UserRepository userRepository) {
        var owner = userRepository.findById(dto.getOwnerId());
        return Hotel.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .location(dto.getLocation())
                .numberOfRooms(dto.getNumberOfRooms())
                .id(dto.getId())
                .owner(owner.get())
                .image(dto.getImage())
                .build();
    }

    public static HotelDTO toDto(Hotel hotel) {
        return HotelDTO.builder()
                .name(hotel.getName())
                .description(hotel.getDescription())
                .location(hotel.getLocation())
                .numberOfRooms(hotel.getNumberOfRooms())
                .id(hotel.getId())
                .image(hotel.getImage())
                .build();
    }

    public static Hotel toCreationEntity(HotelCreationDTO dto) {
        return Hotel.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .location(dto.getLocation())
                .numberOfRooms(dto.getNumberOfRooms())
                .image(dto.getImage())
                .build();
    }
}
