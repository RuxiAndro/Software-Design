package com.example.proiectps1.controller;

import com.example.proiectps1.dto.UserCreationDTO;
import com.example.proiectps1.dto.UserDTO;
import com.example.proiectps1.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.example.proiectps1.exceptions.ApiExceptionResponse;

@RestController //indica ca clasa este un controller responsabil pt HTTP requests
@RequestMapping("/users")
@Tag(name = "User", description = "User API")
@CrossOrigin
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation( //ofera metadate pt documentatia Swagger
            summary = "Fetch all users",
            description = "Fetches all user entities")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation")
    })
    @GetMapping("/Users")
    public ResponseEntity findAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

   /* @Operation(
            summary = "Find user by username",
            description = "Finds the user based on the username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{username}")
    public ResponseEntity findUserByUsername( //represents the entire HTTP response, including status code, headers, and body
            @Parameter(required = true, description = "The username of the user") @PathVariable String username) throws ApiExceptionResponse {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findUserByUsername(username)); //returns the user information in the HTTP response body
    }*/

   /* @PostMapping("/create")
    public ResponseEntity saveNewUser(@RequestBody UserCreationDTO user) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.saveUser(user));
    }*/

    @PostMapping("/create")
    public ResponseEntity createUser(@RequestBody UserCreationDTO user) {
//        UserDTO newUser = userService.saveUser(user);
//        return ResponseEntity.ok(newUser);
        return ResponseEntity.status(HttpStatus.OK).body(userService.saveUser(user));
    }

    @Operation(
            summary = "Delete user",
            description = "Deletes a user based on the id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteUserById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.deleteUser(id));
    }

    @Operation(
            summary = "Update user",
            description = "Updates a user based on the id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })

    @PutMapping("/update") //ar fi mai bine sa fac update dupa username in loc de id?????
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO user) {
        UserDTO updateUser = userService.updateUser(user);
        return ResponseEntity.ok(updateUser);
    }

    /*@PutMapping("/update/{id}")
    public ResponseEntity updateUserById(
            @Parameter(required = true, description = "The id of the user") @PathVariable Long id,
            @RequestBody UserDTO userUpdateDTO) {
            UserDTO updatedUser = userService.updateUser(userUpdateDTO);
            return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }*/



    @GetMapping("/{id}")
    public ResponseEntity findUserById(
            @Parameter(required = true, description = "The ID of the user") @PathVariable Long id) throws ApiExceptionResponse {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findUserById(id));
    }


}