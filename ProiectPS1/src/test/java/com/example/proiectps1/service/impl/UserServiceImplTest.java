/*
package com.example.proiectps1.service.impl;

import com.example.proiectps1.model.User;
import com.example.proiectps1.repository.BookingRepository;
import com.example.proiectps1.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.internal.matchers.NotNull;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserServiceImplTest {

    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private BookingRepository bookingRepository;
    //these mocks are used to simulate the behavior of real instances of these classes

    @BeforeEach
    void setUp()
    {
        initMocks(this);
        userService = new UserServiceImpl(userRepository,bookingRepository);
    }
*/ /*
    @Test
    void testSaveUser() //create
    {
        //given
        User user=new User();
        when(userRepository.save(user)).thenReturn(user);

        //when
        User savedUser=userService.saveUser(user);

        //then
        assertNotNull(savedUser);
        assertEquals(user,savedUser);
        verify(userRepository,times(1)).save(user);
    }

   @Test
    void testUpdateUser()
    {
        //given
        User.RoleType role=User.RoleType.ADMIN;
        User user=new User(1L,"usernameTest","passwordTest",role);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        //Optional is a container object that may or may not contain a non-null value. It is commonly used to represent situations where a value may be present or absent,
        when(userRepository.save(user)).thenReturn(user);

        //when
        User updatedUser=userService.updateUser(user);

        //then
        assertNotNull(updatedUser);
        assertEquals(user,updatedUser);
        verify(userRepository,times(2)).findById(1L);
        verify(userRepository,times(1)).save(user);
    }

    @Test
    void testDeleteUser()
    {
        //given
        Long userId=1L;
        //User user = new User(userId, "username", "password", User.RoleType.CLIENT);

       // when(userRepository.findById(userId)).thenReturn(Optional.of(user));
       // doNothing().when(bookingRepository).deleteByUser(user);

        //when
        userService.deleteUser(userId);

        //then
       // verify(bookingRepository, times(1)).deleteByUser(user);
        verify(userRepository,times(1)).deleteById(userId);


    }
}

*/