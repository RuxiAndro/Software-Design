package com.example.proiectps1.model;

import com.example.proiectps1.dto.RoomDTO;
import com.example.proiectps1.mapper.RoomMapper;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Hotel { //clasa Hotel ,mai trebuie sa fac clasa Camera si sa fac o lista de camere

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private int numberOfRooms;

    private String image;

    private String hotelName;


    @JsonBackReference
   // @JsonManagedReference
    @OneToMany(mappedBy = "hotel",cascade = CascadeType.ALL, fetch = FetchType.EAGER)//mappedBy = "hotel",
    private Set<Room> rooms;

    @JsonBackReference //ar fi trebuit sa mearga
    //@OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER) //the field in the Booking entity that owns the relationship
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, fetch = FetchType.EAGER) //the field in the Booking entity that owns the relationship
    private Set<Booking> bookings; //ca sa asigut unicitatea fiecarei rezervari

    @JsonBackReference
    @ManyToOne         //owner_id e cheie straina in Hotel pt User
    @JoinColumn(name = "owner_id") // Numele coloanei care va face legătura cu id-ul proprietarului din tabela User
    private User owner; //cine are hotelul

    public Hotel(String name, String description, String location, int numberOfRooms) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.numberOfRooms = numberOfRooms;
    }

    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", numberOfRooms=" + numberOfRooms +
                '}';
    }

    public List<RoomDTO> getRoomDTOs() {
        return rooms.stream()
                .map(RoomMapper::toDto)
                .collect(Collectors.toList());
    }
}

