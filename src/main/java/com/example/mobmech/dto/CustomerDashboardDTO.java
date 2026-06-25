package com.example.mobmech.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerDashboardDTO {

    private long totalRequests;
    private long pending;
    private long accepted;
    private long completed;
}
