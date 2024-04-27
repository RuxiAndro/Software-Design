package com.example.proiectps1.service.impl;

import com.example.proiectps1.dto.HotelCreationDTO;
import com.example.proiectps1.dto.HotelDTO;
import com.example.proiectps1.exceptions.ApiExceptionResponse;
import com.example.proiectps1.mapper.HotelMapper;
import com.example.proiectps1.model.Booking;
import com.example.proiectps1.model.Hotel;
import com.example.proiectps1.model.Room;
import com.example.proiectps1.model.User;
import com.example.proiectps1.repository.BookingRepository;
import com.example.proiectps1.repository.HotelRepository;
import com.example.proiectps1.repository.UserRepository;
import com.example.proiectps1.service.HotelService;
import jakarta.persistence.EntityNotFoundException;
import com.example.proiectps1.service.RoomService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


//@org.springframework.stereotype.Service
@Service
@Transactional
@AllArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final UserRepository userRepository;

    private final BookingRepository bookingRepository;
    private final RoomService roomService;

    /*public HotelServiceImpl(RoomService roomService,HotelRepository hotelRepository,UserRepository userRepository,BookingRepository bookingRepository) {
        this.roomService = roomService;
        this.hotelRepository=hotelRepository;
        this.userRepository=userRepository;
        this.bookingRepository=bookingRepository;
    }*/
    @Override
    public List<Hotel> findAll() {
        return (List<Hotel>) hotelRepository.findAll();
    }

   /* @Override
    public HotelDTO saveHotel(HotelCreationDTO hotel)
    {
        Hotel entity= HotelMapper.toCreationEntity(hotel);//transforma in entitatea Hotel
        entity=hotelRepository.save(entity);
        return HotelMapper.toDto(entity);
    }*/

    @Override
    public HotelDTO updateHotel(HotelDTO hotel)
    {
        boolean exists= hotelRepository.findById(hotel.getId()).isPresent();//verifica daca contine o valoare
        if(exists)
        {
            Hotel entity=HotelMapper.toEntity(hotel,userRepository);
            entity=hotelRepository.save(entity);
            return HotelMapper.toDto(entity);
        }
        return null;
    }

    @Override
    public HotelDTO updateHotelByName(String name, HotelDTO hotelDTO) {
        Hotel hotel = hotelRepository.findFirstByName(name);
        if (hotel != null) {
            // Actualizăm detaliile hotelului cu cele din DTO
            hotel.setName(hotelDTO.getName());
            hotel.setDescription(hotelDTO.getDescription());
            hotel.setLocation(hotelDTO.getLocation());
            hotel.setNumberOfRooms(hotelDTO.getNumberOfRooms());
            hotel.setId(hotelDTO.getId());

            // Salvăm hotelul actualizat în baza de date
            hotel = hotelRepository.save(hotel);

            // Returnăm DTO-ul actualizat
            return HotelMapper.toDto(hotel);
        }
        return null; // Returnăm null dacă hotelul nu a fost găsit
    }

   /* @Override
    public boolean deleteHotel(String name)
    {
        hotelRepository.deleteAllByName(name);
        return hotelRepository.findFirstByName(name)==null; //ret true daca nu l-a mai gasit deci daca a fost sters
    }*/

  /*  @Override
   public boolean deleteHotel(Long id)
    { //sa sterg bookings manual inaite de delete
        Optional<Hotel> hotelOptional = hotelRepository.findById(id);
        if (hotelOptional.isPresent()) {
            Hotel hotel = hotelOptional.get();
            bookingRepository.deleteAllByHotel(hotel);
            hotelRepository.deleteById(id);
            return hotelRepository.findById(id).isEmpty(); //ret true daca nu l-a mai gasit deci daca a fost sters
        }else {
            return false;
        }
    }*/

    @Override
    public boolean deleteHotel(Long id) {
        // Găsiți hotelul după ID
        Optional<Hotel> hotelOptional = hotelRepository.findById(id);

        // Dacă hotelul este prezent, efectuați operațiile dorite
        if (hotelOptional.isPresent()) {
            Hotel hotel = hotelOptional.get();

            // Obțineți rezervările asociate hotelului
            List<Booking> bookings = bookingRepository.findAllByHotel(hotel);

            // Ștergeți fiecare rezervare asociată hotelului
            for (Booking booking : bookings) {
                bookingRepository.delete(booking);
            }

            // Ștergeți hotelul
            hotelRepository.delete(hotel);
        }

        // Returnați dacă hotelul a fost șters cu succes
        return !hotelRepository.existsById(id);
    }


    @Override
    public Hotel findHotelById(Long id)
    {
        return hotelRepository.findById(id).orElseThrow();//face parte din clasa Optional ,va returna un obiect wrapped inside an Optional daca nu arunca exceptia NoSuchElementException
    }

    @Override
    public Hotel findHotelByName(String name) throws ApiExceptionResponse
    {
        Hotel hotel = hotelRepository.findFirstByName(name);
        if(hotel ==null)
        {
            ArrayList<String> errors=new ArrayList<>();
            errors.add("No service with name "+name);
            throw ApiExceptionResponse.builder()
                    .errors(errors)
                    .message("Service not found")
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
        return hotel;
    }


   /* public Hotel saveHotel(Hotel hotel, Long ownerId) {
        Optional<User> ownerOptional = userRepository.findById(ownerId);
        if (ownerOptional.isPresent()) {
            User owner = ownerOptional.get();
            owner.getOwnedHotels().add(hotel);
            userRepository.save(owner);
            return hotelRepository.save(hotel);
        } else {
            return null; // Proprietarul nu a putut fi găsit, deci returnăm null
        }
    }*/
   @Override
   public HotelDTO saveHotel(HotelCreationDTO hotel, Long ownerId) {
       // Transformă DTO-ul în entitatea Hotel
       Hotel entity = HotelMapper.toCreationEntity(hotel);

       // Găsește proprietarul pe baza ID-ului
       Optional<User> ownerOptional = userRepository.findById(ownerId);

       if (ownerOptional.isPresent()) {
           // Asociază hotelul cu proprietarul
           User owner = ownerOptional.get();
           entity.setOwner(owner);
           owner.getOwnedHotels().add(entity); // Adaugă hotelul în lista de hoteluri deținute de proprietar
       } else {
           throw new IllegalStateException("Proprietarul cu ID-ul " + ownerId + " nu a putut fi găsit.");
       }
       entity = hotelRepository.save(entity);

       return HotelMapper.toDto(entity);
   }

    @Override
    public List<Hotel> findHotelsByOwnerId(Long ownerId) {
        Optional<User> owner = userRepository.findById(ownerId);
        if (owner.isPresent()) {
            return hotelRepository.findByOwnerId(ownerId);
        } else {
            throw new EntityNotFoundException("Owner with ID " + ownerId + " not found");
        }
    }

    @Override
    public List<Hotel> findHotelsByLocation(String location, LocalDate startDate, LocalDate endDate, int guests) {

        List<Hotel> hotels = hotelRepository.findAllByLocation(location);

        List<Hotel> availableHotels = new ArrayList<>();

        for (Hotel hotel : hotels) {
            int totalAvailableCapacity = 0;

            for (Room room : hotel.getRooms()) {
                boolean isAvailable = roomService.isRoomAvailable(room, startDate, endDate);

                if (isAvailable) {
                    totalAvailableCapacity += room.getCapacity();
                }

                if (totalAvailableCapacity >= guests) {
                    availableHotels.add(hotel);
                    break;
                }
            }
        }

        return availableHotels;
    }


   /* public List<Hotel> findHotelsByLocation(String location){
        return hotelRepository.findAllByLocation(location);
    }*/

}
