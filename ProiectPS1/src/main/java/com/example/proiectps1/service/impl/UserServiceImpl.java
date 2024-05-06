package com.example.proiectps1.service.impl;

import com.example.proiectps1.dto.AuthDTO;
import com.example.proiectps1.dto.SuccessfulLoginDTO;
import com.example.proiectps1.dto.UserCreationDTO;
import com.example.proiectps1.dto.UserDTO;
import com.example.proiectps1.exceptions.ApiExceptionResponse;
import com.example.proiectps1.mapper.UserMapper;
import com.example.proiectps1.model.Hotel;
import com.example.proiectps1.model.User;
import com.example.proiectps1.repository.BookingRepository;
import com.example.proiectps1.repository.HotelRepository;
import com.example.proiectps1.repository.UserRepository;
import com.example.proiectps1.service.UserService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Clasa `UserServiceImpl` implementează serviciul `UserService` și oferă funcționalități pentru gestionarea utilizatorilor.
 * Aceasta include operațiuni precum salvarea, actualizarea și ștergerea utilizatorilor, precum și adăugarea hotelurilor deținute
 * de utilizatori de tip proprietar.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Override
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    private final UserRepository userRepository; //dependency injection
    //interactiune cu baza de date, initialized via constructor injection

    private final BookingRepository bookingRepository;

    private final HotelRepository hotelRepository;

    public UserServiceImpl(UserRepository userRepository, BookingRepository bookingRepository, HotelRepository hotelRepository) {
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
        this.hotelRepository = hotelRepository;
    }

    /**
     * Salvează un nou utilizator în baza de date.
     *
     * @param user Un obiect de tip `UserCreationDTO` care conține detaliile utilizatorului de salvat.
     * @return Un obiect de tip `UserDTO` care reprezintă utilizatorul salvat.
     */
    @Override
    public UserDTO saveUser(UserCreationDTO user) {
        User entity = UserMapper.toCreationEntity(user);
        entity = userRepository.save(entity);
        return UserMapper.toDto(entity);
    }

    public UserDTO updateUser(UserDTO user){
        boolean exists=userRepository.findById(user.getId()).isPresent();//verifica daca contine o valoare
        User user1=userRepository.findById(user.getId()).get();
        if(exists) {
            User entity = UserMapper.toEntity(user);
            if(entity.getUsername()==null)
                entity.setUsername(user1.getUsername());
            entity = userRepository.save(entity);
            return UserMapper.toDto(entity);
            //return UserMapper.toDto(userRepository.save(entity));
        }
        return null;
    }

    @Override
    public boolean deleteUser(Long id)
    {
        userRepository.deleteById(id);
        return userRepository.findById(id).isEmpty(); //ret true daca nu l-a mai gasit deci daca a fost sters
    }

    @Override
    public User findUserById(Long id)
    {
        return userRepository.findById(id).orElseThrow();//face parte din clasa Optional ,va returna un obiect wrapped inside an Optional daca nu arunca exceptia NoSuchElementException
    }

    @Override
    public User findUserByUsername(String username) throws ApiExceptionResponse
    {
        User user=userRepository.findFirstByUsername(username);
        if(user==null)
        {
            ArrayList<String> errors=new ArrayList<>();
            errors.add("No user with username "+username);
            throw ApiExceptionResponse.builder()
                    .errors(errors)
                    .message("Entity not found")
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
        return user;
    }
    /*@Override
    public User findUserByUsername(String username)
    {
        User user = userRepository.findFirstByUsername(username);
        if(user==null)
            return null;
        return user;
    }*/

    @Override
    public SuccessfulLoginDTO login(AuthDTO dto) throws ApiExceptionResponse {
        User user=userRepository.findFirstByUsernameAndPassword(dto.getUsername(), dto.getPassword());
        if(user==null)
        {
            ArrayList<String> errors=new ArrayList<>();
            errors.add("Username "+ dto.getUsername()+" might not exist");
            errors.add("Username and password might not match");

            throw ApiExceptionResponse.builder()
                    .errors(errors)
                    .message("Entity not found")
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
        return SuccessfulLoginDTO.builder().role(user.getRole()).id(user.getId()).build();
    }


 /*   public  User login(AuthDTO dto) throws ApiExceptionResponse {
        User user=userRepository.findFirstByUsernameAndPassword(dto.getUsername(), dto.getPassword());
        if(user==null)
        {
            ArrayList<String> errors=new ArrayList<>();
            errors.add("Username "+ dto.getUsername()+" might not exist");
            errors.add("Username and password might not match");

            throw ApiExceptionResponse.builder()
                    .errors(errors)
                    .message("Entity not found")
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
        return user;
    }*/

  /*  @Transactional
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }*/

    public void addOwnedHotel(User user, Hotel hotel) {
        if (user.getRole() == User.RoleType.OWNER) {
            user.getOwnedHotels().add(hotel);
            hotel.setOwner(user);
        } else {
            // Tratează cazul în care utilizatorul nu are drepturi de proprietar
            throw new IllegalStateException("Utilizatorul nu are drepturi de proprietar pentru a adăuga hoteluri.");
        }
    }



 /*   public void sendSms(String phoneNumber)
    {
        Twilio.init(SmsModel.SID, SmsModel.TOKEN);

        Message.creator(
                new PhoneNumber(phoneNumber),
                new PhoneNumber(SmsModel.phoneNumber),
                "Hello from Twilio 📞"
        ).create();
    }*/
}