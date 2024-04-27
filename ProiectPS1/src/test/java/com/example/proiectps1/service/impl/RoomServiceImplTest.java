package com.example.proiectps1.service.impl;

import com.example.proiectps1.model.Room;
import com.example.proiectps1.repository.RoomRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RoomServiceImplTest {

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomServiceImpl roomService;

    @Test
    public void testRoomAvailability() {
        // Create a room with availability from 2024-03-01 to 2024-03-15
        Room room = new Room(
                null,
                "Single",
                1,
                BigDecimal.valueOf(100),
                LocalDate.of(2024, 3, 1),
                LocalDate.of(2024, 3, 15),
                null,
                null
        );

        // Set up the mock behavior for the roomRepository
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));

        // Test 1: Room is available for booking from 2024-03-16 to 2024-03-20
        assertTrue(roomService.isRoomAvailable(room, LocalDate.of(2024, 3, 16), LocalDate.of(2024, 3, 20)));

        // Test 2: Room is not available for booking from 2024-03-10 to 2024-03-20 (overlaps with existing reservation)
        assertFalse(roomService.isRoomAvailable(room, LocalDate.of(2024, 3, 10), LocalDate.of(2024, 3, 20)));

        // Test 3: Room is partially available for booking from 2024-03-10 to 2024-03-20 (partially overlaps with existing reservation)
        assertFalse(roomService.isRoomAvailable(room, LocalDate.of(2024, 3, 5), LocalDate.of(2024, 3, 10)));

        // Test 4: Room is available for booking from 2024-02-25 to 2024-03-01 (before existing reservation)
        assertTrue(roomService.isRoomAvailable(room, LocalDate.of(2024, 2, 25), LocalDate.of(2024, 3, 1)));

        // Test 5: Room is available for booking from 2024-03-15 to 2024-03-20 (after existing reservation)
        assertTrue(roomService.isRoomAvailable(room, LocalDate.of(2024, 3, 15), LocalDate.of(2024, 3, 20)));
    }
}
