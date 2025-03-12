package com.example.DigitalMoneyHouseJR.usersservice.entities.dto;

public record UpdateUserRequest (String name,
                                 String lastName,
                                 String username,
                                 String email,
                                 String phoneNumber) {

}
