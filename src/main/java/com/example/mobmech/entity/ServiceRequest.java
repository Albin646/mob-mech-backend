package com.example.mobmech.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "service_requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private String vehicleType;

    private String problemDescription;

    private String location;

    private String status;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User customer;

    @ManyToOne
    @JoinColumn(name = "mechanic_id")
    private Mechanic mechanic;
}
