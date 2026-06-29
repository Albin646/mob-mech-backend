package com.example.mobmech.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NearbyMechanicDTO {

    private Long mechanicId;
    private String name;
    private String specialization;
    private double distance;
    private double latitude;
    private double longitude;

}
