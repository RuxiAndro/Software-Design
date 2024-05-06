package com.example.proiectps1.repository;

import com.example.proiectps1.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository //marks the interface as a Spring bean          //the type of the entity's primary key
public interface UserRepository extends CrudRepository<User,Long> {
    User findFirstByUsername(String username);

    User findFirstByUsernameAndPassword(String username,String password);

    boolean existsByUsername(String username);
}

