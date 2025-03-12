package com.example.DigitalMoneyHouseJR.usersservice.entities.dto.mapper;

import com.example.DigitalMoneyHouseJR.usersservice.entities.User;
import com.example.DigitalMoneyHouseJR.usersservice.entities.dto.UserKeycloak;

import java.util.function.Function;

public class UserKeycloakMapper implements Function<User, UserKeycloak> {
    @Override
    public UserKeycloak apply(User user) {
        return new UserKeycloak(
                user.getName(),
                user.getLastName(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword()
        );
    }
}