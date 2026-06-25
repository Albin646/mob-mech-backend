package com.example.mobmech.dto;


import lombok.Data;

@Data
public class ServiceRequestDTO {

    private Long customerId;

    private String vehicleType;

    private String problemDescription;

    private String location;

}
