package com.example.proiectps1.repository;

import com.example.proiectps1.model.Hotel;
import com.example.proiectps1.model.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends CrudRepository<Room,Long> {
    Room findByHotelIdAndId(Long hotelId, Long roomId);
}
