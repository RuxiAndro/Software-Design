package com.example.proiectps1.controller;

import com.example.proiectps1.dto.RoomCreationDTO;
import com.example.proiectps1.dto.RoomDTO;
import com.example.proiectps1.dto.UserCreationDTO;
import com.example.proiectps1.dto.UserDTO;
import com.example.proiectps1.model.Room;
import com.example.proiectps1.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/rooms")
@Tag(name = "Room", description = "Room API")
@CrossOrigin
public class RoomController {
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @Operation(
            summary = "Fetch all rooms",
            description = "Fetches all room entities"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation")
    })
    @GetMapping("/findAllRooms")
    public ResponseEntity findAllRooms() {
        return ResponseEntity.status(HttpStatus.OK).body(roomService.findAll());
    }

    @Operation(
            summary = "Find room by id",
            description = "Finds the room based on the id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room found"),
            @ApiResponse(responseCode = "404", description = "Room not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity findRoomById( //sa il hasesc dupa numarul camerei id ul sa zic ca e si numarul camerei
            @Parameter(required = true, description = "The id of the room") @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(roomService.findRoomById(id));

    }

    @Operation(
            summary = "Create new room",
            description = "Creates a new room"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room created successfully")
    })
    @PostMapping("/create")
    public ResponseEntity createRoom(@RequestBody RoomCreationDTO room) {
        return ResponseEntity.status(HttpStatus.OK).body(roomService.saveRoom(room));
    }

    @Operation(
            summary = "Delete room",
            description = "Deletes a room based on the id, room number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteRoomById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(roomService.deleteRoom(id));
    }
    //apare ca face delete in postman  adica returneaza true dar nu mi se sterge din tabela camera si nici din lista de camere a hotelului

    @Operation(
            summary = "Update room",
            description = "Updates a room based on the id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })

    @PutMapping("/update")
    public ResponseEntity<RoomDTO> updateRoom(@RequestBody RoomDTO room) {
        RoomDTO updateRoom = roomService.updateRoom(room);
        return ResponseEntity.ok(updateRoom);
    }

    @Operation(
            summary = "Check room availability",
            description = "Checks if a room is available for a specified date range"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room availability checked successfully"),
            @ApiResponse(responseCode = "404", description = "Room not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input parameters")
    })
    @GetMapping("/isRoomAvailable")
    public ResponseEntity isRoomAvailable(
            @Parameter(required = true, description = "The ID of the room") @RequestParam Long roomId,
            @Parameter(required = true, description = "Start date in yyyy-MM-dd format") @RequestParam String startDate,
            @Parameter(required = true, description = "End date in yyyy-MM-dd format") @RequestParam String endDate) {

        try {
            // Parse the start date and end date from the request parameters
            LocalDate startLocalDate = LocalDate.parse(startDate);
            LocalDate endLocalDate = LocalDate.parse(endDate);

            // Find the room by ID
            Room room = roomService.findRoomById(roomId);

            // Check if the room is available for the specified date range
            boolean available = roomService.isRoomAvailable(room, startLocalDate, endLocalDate);

            // Return the availability status as a boolean
            return ResponseEntity.status(HttpStatus.OK).body(available);
        } catch (EntityNotFoundException e) {
            // Return a 404 status if the room is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Room not found");
        } catch (Exception e) {
            // Return a 400 status for invalid input parameters or other errors
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input parameters or an error occurred");
        }
    }

}
