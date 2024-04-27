package com.example.proiectps1.service.impl;

import com.example.proiectps1.dto.*;
import com.example.proiectps1.functionalities.EmailService;
import com.example.proiectps1.mapper.BookingMapper;
import com.example.proiectps1.mapper.RoomMapper;
import com.example.proiectps1.mapper.UserMapper;
import com.example.proiectps1.model.Booking;
import com.example.proiectps1.model.Hotel;
import com.example.proiectps1.model.Room;
import com.example.proiectps1.model.User;
import com.example.proiectps1.repository.BookingRepository;
import com.example.proiectps1.repository.HotelRepository;
import com.example.proiectps1.repository.RoomRepository;
import com.example.proiectps1.repository.UserRepository;
import com.example.proiectps1.service.BookingService;
import com.example.proiectps1.service.HotelService;
import com.example.proiectps1.service.RoomService;
import com.example.proiectps1.service.UserService;
import jakarta.transaction.Transactional;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@Transactional
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final RoomService roomService;
    private final UserService userService;
    private final HotelService hotelService;
    private final HotelRepository hotelRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    /**
     * O listă de rezervări care necesită confirmare.
     */
    private final List<BookingDTO> bookingsToConfirm = new ArrayList<>(); // Listă pentru rezervări de confirmat

    @Autowired
    private EmailService emailService;

    public BookingServiceImpl(BookingRepository bookingRepository, RoomService roomService,HotelRepository hotelRepository,UserService userService,UserRepository userRepository,RoomRepository roomRepository,HotelService hotelService) {
        this.bookingRepository = bookingRepository;
        this.roomService = roomService;
        this.hotelRepository=hotelRepository;
        this.userService=userService;
        this.userRepository=userRepository;
        this.roomRepository=roomRepository;
        this.hotelService=hotelService;
    }

    @Override
    public List<Booking> findAll() {
        return (List<Booking>) bookingRepository.findAll();
    }

    /*@Override
    public Booking saveBooking(Booking booking)
    {
        return bookingRepository.save(booking);
    }*/
    /*@Override
    public BookingDTO saveBooking(BookingCreationDTO booking) {
        Booking entity = BookingMapper.toCreationEntity(booking);
        Room room = roomService.findRoomById(booking.getRoomId());
        entity.setRoom(room);
        BigDecimal totalCost=calculateTotalCost(booking.getReservationStartDate(),booking.getReservationEndDate(),room.getPricePerNight());
        entity.setTotalCost(totalCost);
        entity = bookingRepository.save(entity);
        return BookingMapper.toDto(entity);
    }*/
    @Override
    public BookingDTO saveBooking(BookingCreationDTO bookingDetails) {

        Booking entity = BookingMapper.toCreationEntity(bookingDetails,userRepository,hotelRepository,roomRepository);
        Room room = roomService.findRoomById(bookingDetails.getRoomId());


        boolean isRoomAvailable = roomService.isRoomAvailable(room, bookingDetails.getReservationStartDate(), bookingDetails.getReservationEndDate());
        if (!isRoomAvailable) {
            throw new IllegalStateException("Camera nu este disponibilă în perioada solicitată.");
        }


        BigDecimal totalCost = calculateTotalCost(bookingDetails.getReservationStartDate(), bookingDetails.getReservationEndDate(), room.getPricePerNight());
        entity.setTotalCost(totalCost);


        entity = bookingRepository.save(entity);


        room.setCurrentReservationStartDate(bookingDetails.getReservationStartDate());
        room.setCurrentReservationEndDate(bookingDetails.getReservationEndDate());
        roomService.updateRoom(RoomMapper.toDto(room));


        BookingDTO bookingDTO = BookingMapper.toDto(entity);

        synchronized (this) {
            bookingsToConfirm.add(bookingDTO);
        }


        return bookingDTO;
    }

    @Override
    public List<BookingDTO> getBookingsToConfirm() {
        synchronized (this) {
            System.out.println("Lista de rezervări de confirmat: " + bookingsToConfirm);
            return bookingsToConfirm;
        }
    }

   /* @Override
    public Booking updateBooking(Booking booking)
    {
        boolean exists=bookingRepository.findById(booking.getId()).isPresent();//verifica daca contine o valoare
        if(exists)
            return bookingRepository.save(booking);
        return null;
    }*/
   @Override
   public BookingDTO updateBooking(BookingDTO bookingDTO) {
       Optional<Booking> optionalBooking = bookingRepository.findById(bookingDTO.getId());
       if (optionalBooking.isPresent()) {
           Booking booking = optionalBooking.get();

           booking.setReservationStartDate(bookingDTO.getReservationStartDate());
           booking.setReservationEndDate(bookingDTO.getReservationEndDate());
           booking.setBookingDate(bookingDTO.getBookingDate());
           booking.setBookingTime(bookingDTO.getBookingTime());
           booking.setTotalCost(bookingDTO.getTotalCost());

           Optional<User> user = userRepository.findById(bookingDTO.getUserId());
           if (user.isPresent()) {
               booking.setUser(user.get());
           } else {
               throw new IllegalStateException("User-ul cu ID-ul " + bookingDTO.getUserId() + " nu a putut fi găsit.");
           }

           Optional<Hotel> hotel = hotelRepository.findById(bookingDTO.getHotelId());
           if (hotel.isPresent()) {
               booking.setHotel(hotel.get());
           } else {
               throw new IllegalStateException("Hotelul cu ID-ul " + bookingDTO.getHotelId() + " nu a putut fi găsit.");
           }

           Optional<Room> room = roomRepository.findById(bookingDTO.getRoomId());
           if (room.isPresent()) {
               booking.setRoom(room.get());
           } else {
               throw new IllegalStateException("Camera cu ID-ul " + bookingDTO.getRoomId() + " nu a putut fi găsită.");
           }

           BigDecimal totalCost = calculateTotalCost(bookingDTO.getReservationStartDate(), bookingDTO.getReservationEndDate(), room.get().getPricePerNight());
           booking.setTotalCost(totalCost);

           booking = bookingRepository.save(booking);

           return BookingMapper.toDto(booking);
       } else {
           throw new IllegalStateException("Rezervarea cu ID-ul " + bookingDTO.getId() + " nu a putut fi găsită.");
       }
   }


    @Override
    public boolean deleteBooking(Long id)
    {
        bookingRepository.deleteById(id);
        return bookingRepository.findById(id).isEmpty(); //ret true daca nu l-a mai gasit deci daca a fost sters
    }

    @Override
    public Booking findBookingById(Long id)
    {
        return bookingRepository.findById(id).orElseThrow();//face parte din clasa Optional ,va returna un obiect wrapped inside an Optional daca nu arunca exceptia NoSuchElementException
    }

    @Override
    public List<Room> findBookedRooms() {
        Iterable<Booking> bookingsIterable = bookingRepository.findAll();
        List<Booking> bookingsList = new ArrayList<>();

        // Convert Iterable to List
        bookingsIterable.forEach(bookingsList::add);

        Set<Room> bookedRooms = new HashSet<>();

        for (Booking booking : bookingsList) {
            bookedRooms.add(booking.getRoom());
        }

        return new ArrayList<>(bookedRooms);
    }

    /**
     * Calculează costul total al unei rezervări.
     *
     * @param startDate Data de început a rezervării.
     * @param endDate Data de sfârșit a rezervării.
     * @param pricePerNight Prețul pe noapte pentru o cameră.
     * @return Costul total al rezervării.
     */
   @Override
    public BigDecimal calculateTotalCost(LocalDate startDate, LocalDate endDate, BigDecimal pricePerNight) {
        long numberOfDays = ChronoUnit.DAYS.between(startDate, endDate);
        if (numberOfDays == 0) {
            numberOfDays = 1;
        } else {
            numberOfDays++;
        }
        return pricePerNight.multiply(BigDecimal.valueOf(numberOfDays));
    }

    @Override
    public BookingDTO makeReservation(Long hotelId, Long roomId, Long userId, BookingCreationDTO bookingDetails) {
        // Găsim hotelul după ID
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() ->
                new IllegalStateException("Hotelul cu ID-ul " + hotelId + " nu a putut fi găsit.")
        );

        // Găsim camera după ID și hotel
        Room room = roomService.findRoomIdByHotelIdAndRoomId(hotelId, roomId);

        // Verificăm disponibilitatea camerei în perioada dată
        boolean isRoomAvailable = roomService.isRoomAvailable(room, bookingDetails.getReservationStartDate(), bookingDetails.getReservationEndDate());

        if (!isRoomAvailable) {
            throw new IllegalStateException("Camera nu este disponibilă în perioada solicitată.");
        }

        // Găsim utilizatorul după ID
        User user = userService.findUserById(userId);

        // Cream entitatea Booking
        Booking booking = BookingMapper.toCreationEntity(bookingDetails,userRepository,hotelRepository,roomRepository);
        booking.setHotel(hotel);
        booking.setRoom(room);
        booking.setUser(user);
        booking.setBookingDate(LocalDate.now());  // Setăm data rezervării curente

        // Calculăm costul total al rezervării
        BigDecimal totalCost = calculateTotalCost(bookingDetails.getReservationStartDate(), bookingDetails.getReservationEndDate(), room.getPricePerNight());
        booking.setTotalCost(totalCost);

        // Salvăm rezervarea în baza de date
        booking = bookingRepository.save(booking);

        // Actualizăm datele camerei pentru a reflecta rezervarea
        room.setCurrentReservationStartDate(bookingDetails.getReservationStartDate());
        room.setCurrentReservationEndDate(bookingDetails.getReservationEndDate());
        roomService.updateRoom(RoomMapper.toDto(room));

        // Returnăm DTO-ul rezervării
        return BookingMapper.toDto(booking);
    }


}
