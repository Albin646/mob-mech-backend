package com.example.mobmech.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TrackingDTO {

    private double customerLat;
    private double customerLon;

    private double mechanicLat;
    private double mechanicLon;

    private String mechanicName;
    private String status;
}
