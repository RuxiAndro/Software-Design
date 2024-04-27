package com.example.proiectps1.controller;

import com.example.proiectps1.dto.BookingCreationDTO;
import com.example.proiectps1.dto.BookingDTO;
import com.example.proiectps1.exceptions.ApiExceptionResponse;
import com.example.proiectps1.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookings")
@Tag(name = "Booking", description = "Booking API")
@CrossOrigin
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Operation(
            summary = "Fetch all bookings",
            description = "Fetches all bookings entities"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation")
    })
    @GetMapping
    public ResponseEntity findAllBookings() {
        return ResponseEntity.status(HttpStatus.OK).body(bookingService.findAll());
    }


    @Operation(
            summary = "Create new booking",
            description = "Creates a new booking"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking created successfully")
    })
    @PostMapping("/create")
    public ResponseEntity createBooking(@RequestBody BookingCreationDTO booking) {
        return ResponseEntity.status(HttpStatus.OK).body(bookingService.saveBooking(booking));
    }

    @Operation(
            summary = "Find booking by ID",
            description = "Finds a booking based on the ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking found"),
            @ApiResponse(responseCode = "404", description = "Booking not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity findBookingById(
            @Parameter(required = true, description = "The ID of the booking") @PathVariable Long id)
            throws ApiExceptionResponse {
        return ResponseEntity.status(HttpStatus.OK).body(bookingService.findBookingById(id));
    }

    @Operation(
            summary = "Delete booking",
            description = "Deletes a booking based on the ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Booking not found")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteBookingById(
            @Parameter(required = true, description = "The ID of the booking") @PathVariable Long id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(
            summary = "Update booking",
            description = "Updates a booking based on the ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking updated successfully"),
            @ApiResponse(responseCode = "404", description = "Booking not found")
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<BookingDTO> updateBooking(
            @Parameter(required = true, description = "The ID of the booking") @PathVariable Long id,
            @RequestBody BookingDTO bookingDTO) throws ApiExceptionResponse {
        bookingDTO.setId(id);
        BookingDTO updatedBooking = bookingService.updateBooking(bookingDTO);
        return ResponseEntity.ok(updatedBooking);
    }


    @Operation(
    summary = "Create a new reservation",
    description = "Creates a new reservation in a specified hotel and room for a specified user."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reservation created successfully"),
        @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PostMapping("/reservation")
    public ResponseEntity<BookingDTO> createReservation(
        @RequestParam Long hotelId,
        @RequestParam Long roomId,
        @RequestParam Long userId,
        @RequestBody BookingCreationDTO bookingDetails
    ) {
        BookingDTO bookingDTO = bookingService.makeReservation(hotelId, roomId, userId, bookingDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingDTO);
    }


}
