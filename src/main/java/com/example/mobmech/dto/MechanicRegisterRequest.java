package com.example.mobmech.dto;

import lombok.Data;

@Data
public class MechanicRegisterRequest {
    private String name;
    private String email;
    private String phone;
    private String password;

    private int experience;
    private String specialization;

}
