package com.example.proiectps1;

import com.example.proiectps1.dto.AuthDTO;
import com.example.proiectps1.dto.UserCreationDTO;
import com.example.proiectps1.dto.UserDTO;
import com.example.proiectps1.exceptions.ApiExceptionResponse;
import com.example.proiectps1.mapper.UserMapper;
import com.example.proiectps1.model.Booking;
import com.example.proiectps1.model.Hotel;
import com.example.proiectps1.model.Room;
import com.example.proiectps1.model.User;
import com.example.proiectps1.service.BookingService;
import com.example.proiectps1.service.HotelService;
import com.example.proiectps1.service.RoomService;
import com.example.proiectps1.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;


@SpringBootApplication
@EnableScheduling
public class ProiectPs1Application {

    public static void main(String[] args) {
        SpringApplication.run(ProiectPs1Application.class, args);
    }

    /*@Bean
    CommandLineRunner init(UserService userService, HotelService hotelService, RoomService roomService, BookingService bookingService)
    {
        return args -> {
           /* User user1=new User();
            user1.setUsername("username1");
            user1.setRole(User.RoleType.CLIENT);
            user1.setPassword("password1");
            user1=userService.saveUser(user1);

           User user2=new User();
            user2.setUsername("username2");
            user2.setRole(User.RoleType.OWNER);
            user2.setPassword("password2");
            userService.saveUser(user2);

            User user3=new User();
            user3.setUsername("username3");
            user3.setRole(User.RoleType.OWNER);
            user3.setPassword("password3");
            userService.saveUser(user3);

           System.out.println(userService.findUserByUsername("username2"));
           System.out.println(userService.findUserById(1L));


            //update
            if(user1!=null)
            {
                user1.setUsername("username7");
                User updatedUser=userService.updateUser(user1);
                if(updatedUser!=null)
                {
                    System.out.println("User updated successfully: "+updatedUser);
                }
                else {
                    System.out.println("Failed to update user");
                }
            }
            else {
                System.out.println("User not found");
            }

            //login
            AuthDTO a1=new AuthDTO();
            a1.setUsername("username3");
            a1.setPassword("password1");
            try {
                User loggedInUser=userService.login(a1);
                if(loggedInUser!=null)
                {
                    System.out.println("Login successful for user: " + loggedInUser.getUsername());
                    System.out.println("User Role: " + loggedInUser.getRole());
                }else {
                    System.out.println("Login failed. Invalid username or password.");
                }
            }catch (ApiExceptionResponse exception) {
                System.out.println("Login failed with errors: " + exception.getMessage());
                for (String error : exception.getErrors()) {
                    System.out.println(" - " + error);
                }
            }

            System.out.println(userService.findUserByUsername("username2"));
            System.out.println("-------------------------------------------");


            BigDecimal price = new BigDecimal("100");
            LocalDate availabilityStartDate = LocalDate.of(2024,3,23);
            LocalDate availabilityEndtDate = LocalDate.of(2024,3,28);
            Hotel hotel1 =new Hotel("service1","cazare",price,availabilityStartDate,availabilityEndtDate,"Magheru",4);
            hotelService.saveService(hotel1);

            BigDecimal price2 = new BigDecimal("999");
            LocalDate availabilityStartDate2 = LocalDate.of(2024,3,15);
            LocalDate availabilityEndtDate2 = LocalDate.of(2024,4,29);
            Hotel hotel2 =new Hotel("service2","cazare",price,availabilityStartDate2,availabilityEndtDate2,"Magheru",2);
            hotelService.saveService(hotel2);

            System.out.println(hotelService.findServiceById(1L));
            System.out.println(hotelService.findServiceByName("service1"));

            if(hotel1 !=null)
            {
                hotel1.setName("service3");
                hotel1.setCapacity(10);
                Hotel updatedHotel = hotelService.updateService(hotel1);
                if(updatedHotel !=null)
                {
                    System.out.println("Service updated successfully: "+ updatedHotel);
                }
                else {
                    System.out.println("Failed to update service");
                }
            }
            else {
                System.out.println("Service not found");
            }

          // serviceService.deleteService(2L);

            LocalDate bookingDate = LocalDate.of(2024,3,23);
            LocalTime bookingTime = LocalTime.of(14,30);
            Booking booking1=new Booking(user2, hotel1,bookingDate,bookingTime);
            bookingService.saveBooking(booking1);

            LocalDate bookingDate2 = LocalDate.of(2024,3,23);
            LocalTime bookingTime2 = LocalTime.of(15,26);
            Booking booking2=new Booking(user2, hotel2,bookingDate2,bookingTime2);
            bookingService.saveBooking(booking2);

            LocalDate bookingDate3 = LocalDate.of(2024,3,23);
            LocalTime bookingTime3 = LocalTime.of(15,26);
            Booking booking3=new Booking(user3, hotel2,bookingDate2,bookingTime2);
            bookingService.saveBooking(booking3);
           // userService.deleteUser(3L);

            //one to many - de la user la booking
            Set<Booking> userBookings = userService.findUserByUsername("username2").getBookings();
            System.out.println("Number of bookings for user2: " + userBookings.size());
            for (Booking booking : userBookings) {
                System.out.println("Booking: " + booking);
            }

           /* Set<Booking> serviceBookings = serviceService.findServiceByName("service1").getBookings();
            System.out.println("Number of bookings for service1: " + serviceBookings.size());
            for (Booking booking : serviceBookings) {
                System.out.println("Booking: " + booking);
            }*/

            /*
            Number of bookings for user2: 2
            Booking: Booking{id=2, user=2, service=2, bookingDate=2024-03-23, bookingTime=15:26}
            Booking: Booking{id=1, user=2, service=1, bookingDate=2024-03-23, bookingTime=14:30}
            am user2 ,id-ul nu ar trebui sa fie acwelasi pt amandoi??
          */

           // user1.getBookings().add(booking1);
            //user2.getBookings().add(booking2);
            //service1.getBookings().add(booking1);
            //service2.getBookings().add(booking2);
            //am adaugat rezervarea si in user si in service
            //many-to-many indirect intre user si service prin tabela booking

           // userService.deleteUser(2L);*/

          /*  UserCreationDTO user1Dto = UserCreationDTO.builder()
                    .username("username1")
                    .password("password1")
                    .role(User.RoleType.CLIENT)
                    .build();
            UserDTO savedUser1Dto = userService.saveUser(user1Dto);
            System.out.println("Saved user 1: " + savedUser1Dto);

            UserCreationDTO user2Dto = UserCreationDTO.builder()
                    .username("username2")
                    .password("password2")
                    .role(User.RoleType.OWNER)
                    .build();
            UserDTO savedUser2Dto = userService.saveUser(user2Dto);
            System.out.println("Saved user 2: " + savedUser2Dto);

// Create user 3
            UserCreationDTO user3Dto = UserCreationDTO.builder()
                    .username("username3")
                    .password("password3")
                    .role(User.RoleType.OWNER)
                    .build();
            UserDTO savedUser3Dto = userService.saveUser(user3Dto);
            System.out.println("Saved user 3: " + savedUser3Dto);

            System.out.println(userService.findUserByUsername("username2"));
            System.out.println(userService.findUserById(1L));
*/

            //update
          /*  if(user1Dto!=null)
            {
                //user1Dto.setUsername("username4");
               // User updatedUser=userService.updateUser(user1Dto);
                User userToUpdate = UserMapper.toEntity(user1Dto);
                userToUpdate.setUsername("username4");
                if(updatedUser!=null)
                {
                    System.out.println("User updated successfully: "+updatedUser);
                }
                else {
                    System.out.println("Failed to update user");
                }
            }
            else {
                System.out.println("User not found");
            }*/

            //login */
          /*  AuthDTO a1=new AuthDTO();
            a1.setUsername("username3");
            a1.setPassword("password1");
            try {
                User loggedInUser=userService.login(a1);
                if(loggedInUser!=null)
                {
                    System.out.println("Login successful for user: " + loggedInUser.getUsername());
                    System.out.println("User Role: " + loggedInUser.getRole());
                }else {
                    System.out.println("Login failed. Invalid username or password.");
                }
            }catch (ApiExceptionResponse exception) {
                System.out.println("Login failed with errors: " + exception.getMessage());
                for (String error : exception.getErrors()) {
                    System.out.println(" - " + error);
                }
            }

            System.out.println(userService.findUserByUsername("username2"));
            System.out.println("-------------------------------------------");

            // Create hotels
            Hotel hotel1 = new Hotel("hotel1", "Hotel description 1", "Location 1", 10);
            hotelService.saveHotel(hotel1);
            System.out.println("Saved hotel1: " + hotel1);

            Hotel hotel2 = new Hotel("hotel2", "Hotel description 2", "Location 2", 20);
            hotelService.saveHotel(hotel2);
            System.out.println("Saved hotel2: " + hotel2);

            // Create rooms
            Room room1 = new Room(hotel1, "Single", 1, BigDecimal.valueOf(100), LocalDate.of(2024, 3, 19), LocalDate.of(2024, 3, 28),LocalDate.of(2024,3,20),LocalDate.of(2024,3,25));
            roomService.saveRoom(room1);
            System.out.println("Saved room1: " + room1);

            Room room2 = new Room(hotel2, "Double", 2, BigDecimal.valueOf(200), LocalDate.of(2024, 3, 23), LocalDate.of(2024, 3, 28));
            roomService.saveRoom(room2);
            System.out.println("Saved room2: " + room2);

            LocalDate startDate = LocalDate.of(2024, 3, 26);
            LocalDate endDate = LocalDate.of(2024, 3, 27);

            Room room = roomService.findRoomById(1L);
            boolean isAvailable = roomService.isRoomAvailable(room, startDate, endDate);

            if (isAvailable) {
                System.out.println("Room is available.");
            } else {
                System.out.println("Room is not available.");
            }
*/
            ////    SA MODIFIC SI IN BOOKING SA ACCEPTE USERdTO
            /*  // Create bookings
            LocalDate bookingDate1 = LocalDate.of(2024, 3, 23);
            LocalTime bookingTime1 = LocalTime.of(14, 30);
            Booking booking1 = new Booking(user1Dto, hotel1, bookingDate1, bookingTime1,LocalDate.of(2024,3,20),LocalDate.of(2024,3,25));
            bookingService.saveBooking(booking1);
            System.out.println("Saved booking1: " + booking1);

            ////    SA MODIFIC SI IN BOOKING SA ACCEPTE USERdTO
           LocalDate bookingDate2 = LocalDate.of(2024, 3, 24);
            LocalTime bookingTime2 = LocalTime.of(15, 30);
            Booking booking2 = new Booking(user2Dto, hotel2, bookingDate2, bookingTime2,LocalDate.of(2024,3,20),LocalDate.of(2024,3,25));
            bookingService.saveBooking(booking2);
            System.out.println("Saved booking2: " + booking2);

            // Retrieve booked rooms
            List<Room> bookedRooms = bookingService.findBookedRooms();

// Now check if a booked room is available for a new booking
            for (Room roomm : bookedRooms) {
                boolean isRoomAvailable = roomService.isRoomAvailable(roomm, startDate, endDate);
                if (isRoomAvailable) {
                    System.out.println("Room " + roomm.getId() + " is available for booking.");
                } else {
                    System.out.println("Room " + roomm.getId() + " is not available for booking.");
                }
            }*/

          /*  // Verifying relationships
            // Retrieve rooms for a hotel
            Set<Room> hotel1Rooms = hotelService.findHotelById(hotel1.getId()).getRooms();
            System.out.println("Number of rooms for hotel1: " + hotel1Rooms.size());
            for (Room room : hotel1Rooms) {
                System.out.println("Room: " + room);
            }

            // Retrieve bookings for a hotel
            Set<Booking> hotel1Bookings = hotelService.findHotelById(hotel1.getId()).getBookings();
            System.out.println("Number of bookings for hotel1: " + hotel1Bookings.size());
            for (Booking booking : hotel1Bookings) {
                System.out.println("Booking: " + booking);
            }

            // Retrieve bookings for a user
            Set<Booking> user1Bookings = userService.findUserById(user1.getId()).getBookings();
            System.out.println("Number of bookings for user1: " + user1Bookings.size());
            for (Booking booking : user1Bookings) {
                System.out.println("Booking: " + booking);
            }*/
       // };
   // }
}
