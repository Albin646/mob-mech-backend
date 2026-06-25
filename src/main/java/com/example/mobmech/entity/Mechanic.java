package com.example.mobmech.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "mechanics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Mechanic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private int experience;

    private String specialization;

    private double rating = 0.0;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
