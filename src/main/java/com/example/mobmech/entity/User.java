package com.example.mobmech.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String phone;

//    private String password;

    @JsonIgnore
    private String password;

    public enum Role{
        CUSTOMER,
        MECHANIC
    }
    @Enumerated(EnumType.STRING)
    private Role role;

    private double longitude;
    private double latitude;

}