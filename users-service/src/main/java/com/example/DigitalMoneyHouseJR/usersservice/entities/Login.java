package com.example.DigitalMoneyHouseJR.usersservice.entities;


import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Login {

    @Column(name = "Email", nullable = false)
    private String email;


    @Column(name = "password", nullable = false)
    private String password;

}
