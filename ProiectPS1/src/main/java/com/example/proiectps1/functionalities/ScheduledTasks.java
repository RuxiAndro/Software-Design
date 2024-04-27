package com.example.proiectps1.functionalities;

import com.example.proiectps1.dto.BookingDTO;
import com.example.proiectps1.model.User;
import com.example.proiectps1.service.BookingService;
import com.example.proiectps1.service.UserService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScheduledTasks {

   /* @Autowired
    private DemoService demoService;*/

    @Autowired
    private BookingService bookingService;

    @Autowired
    private EmailService emailService;

    private final UserService userService;

    public ScheduledTasks(UserService userService) {
        this.userService = userService;
    }

    @Scheduled(cron = "0 * * * * *") // Cron expression for running every minute
    public void sendConfirmationEmails() {

        List<BookingDTO> bookingsToConfirm = bookingService.getBookingsToConfirm();
        if (bookingsToConfirm.isEmpty()) {
           // System.out.println("Lista de rezervări de confirmat este goală.");
            return; // Dacă lista este goală, nu trimitem e-mailuri și ieșim din funcție
        }

        for (BookingDTO bookingDTO : bookingsToConfirm) {
            User user = userService.findUserById(bookingDTO.getUserId());
           // String to = user.getEmail(); // Adresa de e-mail a utilizatorului
            String to = "androruxi@gmail.com";
            String subject = "Confirmare rezervare la " + bookingDTO.getHotelId();
            String body = String.format(
                    "Bună ziua,\n\nAți făcut o rezervare la hotelul %s .\n\nDetalii rezervare:\n" +
                            "- Dată început: %s\n- Dată sfârșit: %s\n- Cost total: %.2f EUR\n\nMulțumim!",
                     bookingDTO.getHotelId(),
                    //bookingDTO.getRoomName(),
                    bookingDTO.getReservationStartDate(),
                    bookingDTO.getReservationEndDate(),
                    bookingDTO.getTotalCost()
            );

            emailService.sendEmail(to, subject, body);
        }

        bookingsToConfirm.clear();
    }
}