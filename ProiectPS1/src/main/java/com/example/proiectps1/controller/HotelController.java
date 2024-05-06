package com.example.proiectps1.controller;

import com.example.proiectps1.dto.HotelCreationDTO;
import com.example.proiectps1.dto.HotelDTO;
import com.example.proiectps1.dto.RoomCreationDTO;
import com.example.proiectps1.dto.RoomDTO;
import com.example.proiectps1.exceptions.ApiExceptionResponse;
import com.example.proiectps1.model.Booking;
import com.example.proiectps1.model.Hotel;
import com.example.proiectps1.repository.BookingRepository;
import com.example.proiectps1.service.BookingService;
import com.example.proiectps1.service.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/hotels")
@Tag(name = "Hotel", description = "Hotel API")
@CrossOrigin
public class HotelController {
    private final HotelService hotelService;
    private final BookingService bookingService;
    private final BookingRepository bookingRepository;


    public HotelController(HotelService hotelService,BookingService bookingService,BookingRepository bookingRepository) {
        this.hotelService = hotelService;
        this.bookingService=bookingService;
        this.bookingRepository=bookingRepository;
    }

    @Operation(
            summary = "Fetch all hotels",
            description = "Fetches all hotels entities"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation")
    })
    @GetMapping
    public ResponseEntity findAllHotels() {
        return ResponseEntity.status(HttpStatus.OK).body(hotelService.findAll());
    }

    @Operation(
            summary = "Find hotel by name",
            description = "Finds the hotel based on the name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hotel found"),
            @ApiResponse(responseCode = "404", description = "Hotel not found")
    })
    @GetMapping("/{name}")
    public ResponseEntity findHotelByUsername( //represents the entire HTTP response, including status code, headers, and body
                                              @Parameter(required = true, description = "The username of the user") @PathVariable String name) throws ApiExceptionResponse {
        return ResponseEntity.status(HttpStatus.OK).body(hotelService.findHotelByName(name)); //returns the user information in the HTTP response body
    }

    @Operation(
            summary = "Create new hotel",
            description = "Creates a new hotel"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hotel created successfully")
    })
    @PostMapping("/create")
    public ResponseEntity createHotel(@RequestBody HotelCreationDTO hotel, @RequestParam Long ownerId) {
        return ResponseEntity.status(HttpStatus.OK).body(hotelService.saveHotel(hotel,ownerId));
    }

    @Operation(
            summary = "Delete hotel",
            description = "Deletes a hotel based on the id, room number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation")
    })
    @DeleteMapping("/delete/{id}") //merge deletul dupa nume si merge daca sterg hotelul mi se sterge si camera
    public ResponseEntity deleteHotelByName(@PathVariable Long id) {
        bookingService.deleteBookingByHotelId(id);

        boolean ok = hotelService.deleteHotel(id);
        ResponseEntity response = ok == true
                ? ResponseEntity.status(HttpStatus.OK).body(ok)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(ok);
        return response;
    }
  /*  @DeleteMapping("/delete/{name}") //merge deletul dupa nume si merge daca sterg hotelul mi se sterge si camera
    public ResponseEntity deleteHotelByName(@PathVariable String name) {
        return ResponseEntity.status(HttpStatus.OK).body(hotelService.deleteHotel(name));
    }*/

  /*  @Operation(
            summary = "Update hotel",
            description = "Updates a hotel based on the name"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Hotel not found")
    })

    @PutMapping("/update{name}")
    public ResponseEntity<HotelDTO> updateHotel(@PathVariable String name,@RequestBody HotelDTO hotel) {
        HotelDTO updateHotel = hotelService.updateHotelByName(name,hotel);
        return ResponseEntity.ok(updateHotel);
    }*/

    @Operation(
            summary = "Update hotel",
            description = "Updates a hotel based on the id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })

    @PutMapping("/update")
    public ResponseEntity<HotelDTO> updateHotel(@RequestBody HotelDTO hotel) {
        HotelDTO updateHotel = hotelService.updateHotel(hotel);
        return ResponseEntity.ok(updateHotel);
    }

    @Operation(
            summary = "Find hotel by ID",
            description = "Finds the hotel based on the ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hotel found"),
            @ApiResponse(responseCode = "404", description = "Hotel not found")
    })
    @GetMapping("/id/{id}")
    public ResponseEntity findHotelById(
            @Parameter(required = true, description = "The ID of the hotel") @PathVariable Long id) throws ApiExceptionResponse {
        return ResponseEntity.status(HttpStatus.OK).body(hotelService.findHotelById(id));
    }

    @Operation( //pt owner exclusib=v sa afisez lista cu hoteluri detinute
            summary = "Find hotels by owner ID",
            description = "Finds the hotels owned by the user based on owner ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hotels found"),
            @ApiResponse(responseCode = "404", description = "Hotels not found")
    })
    @GetMapping("/hotels/{ownerId}")
    public ResponseEntity findHotelsByOwnerId(
            @Parameter(required = true, description = "The ID of the owner") @PathVariable Long ownerId) {
        return ResponseEntity.status(HttpStatus.OK).body(hotelService.findHotelsByOwnerId(ownerId));
    }

    @Operation(
            summary = "Find hotels by location",
            description = "Finds hotels based on the location"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hotels found"),
            @ApiResponse(responseCode = "404", description = "Hotels not found")
    })
   /* @GetMapping("/location/{location}")
    public ResponseEntity findHotelsByLocation(
            @Parameter(required = true, description = "The location of the hotels") @PathVariable String location) {
        List<Hotel> hotels = hotelService.findHotelsByLocation(location);
        if (hotels.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hotels found in the specified location");
        }
        return ResponseEntity.status(HttpStatus.OK).body(hotels);
    }*/
    @GetMapping("/location/{location}")
    public ResponseEntity findHotelsByLocation(
            @Parameter(required = true, description = "The location of the hotels") @PathVariable String location,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @RequestParam int guests) {
        List<Hotel> hotels = hotelService.findHotelsByLocation(location, startDate, endDate, guests);
        if (hotels.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hotels found in the specified location for the selected date range and guest count.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(hotels);
    }


}
