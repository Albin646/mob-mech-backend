package com.example.mobmech.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MechanicDashboardDTO {

    private long assignedJobs;
    private long completedJobs;
    private double rating;
}
