package com.example.proiectps1.repository;

import com.example.proiectps1.model.Hotel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends CrudRepository<Hotel,Long> {
    Hotel findFirstByName(String name);
    //Hotel findByName(String name);
    void deleteAllByName(String name);

    List<Hotel> findByOwnerId(Long ownerId);

    List<Hotel> findAllByLocation(String location);
}
