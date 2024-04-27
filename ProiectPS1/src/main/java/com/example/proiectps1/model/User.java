package com.example.proiectps1.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.*;

import java.util.Set;

@AllArgsConstructor //imi face un constructor cu toti parametrii
@NoArgsConstructor //constructor gol
@Getter
@Setter
@Entity
@ToString
@Builder
//@ToString(exclude = "bookings")
@Table(name="user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;
    private RoleType role;

    private String phoneNumber;

    private String email;
    public enum RoleType{
        OWNER, //0
        ADMIN, //1
        CLIENT //2
    }

    public User(Long id, String username, String password, RoleType role, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
    }

    public User(Long id) {
        this.id = id;
    }


    @JsonManagedReference
    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Set<Booking> bookings;

    //  relație de OneToMany cu entitatea Hotel pentru a defini hotelurile deținute de utilizator
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Hotel> ownedHotels;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }

}

