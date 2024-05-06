package com.example.proiectps1.repository;

import com.example.proiectps1.model.Booking;
import com.example.proiectps1.model.Hotel;
import com.example.proiectps1.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface BookingRepository extends CrudRepository<Booking,Long> {
    //Set<Booking> findByUser(User user);
    void deleteByUser(User user);

    @Modifying
    @Transactional
    @Query("DELETE FROM Booking b WHERE b.hotel = :hotel")
    void deleteAllByHotel(Hotel hotel);



    List<Booking> findAllByHotel(Hotel hotel);


}
