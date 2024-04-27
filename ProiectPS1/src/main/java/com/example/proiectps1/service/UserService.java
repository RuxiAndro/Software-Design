package com.example.proiectps1.service;
import com.example.proiectps1.dto.AuthDTO;
import com.example.proiectps1.dto.SuccessfulLoginDTO;
import com.example.proiectps1.dto.UserCreationDTO;
import com.example.proiectps1.dto.UserDTO;
import com.example.proiectps1.exceptions.ApiExceptionResponse;
import com.example.proiectps1.model.Hotel;
import com.example.proiectps1.model.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserService {

    List<User> findAll();
    UserDTO saveUser(UserCreationDTO user);

    UserDTO updateUser(UserDTO user);
   // UserDTO updateUser(String username, UserDTO userDTO) throws ApiExceptionResponse;
    boolean deleteUser(Long id);

    User findUserById(Long id);

    // User findUserByName(String name) throws ApiExceptionResponse;
    //User findUserByName(String name);
    User findUserByUsername(String username) throws ApiExceptionResponse;
   // User findUserByUsername(String username);

   // User login(AuthDTO dto) throws ApiExceptionResponse;
   SuccessfulLoginDTO login(AuthDTO dto) throws ApiExceptionResponse;

    void addOwnedHotel(User user, Hotel hotel);


}

