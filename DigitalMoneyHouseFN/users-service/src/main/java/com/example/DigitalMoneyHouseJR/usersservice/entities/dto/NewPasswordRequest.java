package com.example.DigitalMoneyHouseJR.usersservice.entities.dto;

import lombok.Data;

@Data
public class NewPasswordRequest {
    private String password;
    private String passwordRepeated;
}
