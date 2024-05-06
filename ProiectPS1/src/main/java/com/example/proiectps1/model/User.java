package com.example.proiectps1.model;

import com.example.proiectps1.listeners.UserEventListener;
import com.example.proiectps1.validators.RoleTypeSubset;
import com.example.proiectps1.validators.UniqueUsername;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.*;
import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@ToString
@Table(name = "user")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD) //Această adnotare specifică modul de acces al JAXB la clasa User. În acest caz, am optat pentru XmlAccessType.FIELD, ceea ce înseamnă că JAXB va accesa direct câmpurile clasei.
@EntityListeners(UserEventListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlElement
    private Long id;

    @XmlElement
    private String username;

    @XmlElement
    private String password;

    @XmlElement
   // @RoleTypeSubset(anyOf = {RoleType.OWNER, RoleType.ADMIN, RoleType.CLIENT}, message = "Invalid role type")
    private RoleType role;


    @XmlElement
    private String phoneNumber;

    @XmlElement
    private String email;

   public enum RoleType {
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
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @XmlElement
    private Set<Booking> bookings;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @XmlElement
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
