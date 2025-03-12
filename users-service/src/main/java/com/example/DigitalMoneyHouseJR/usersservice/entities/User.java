package com.example.DigitalMoneyHouseJR.usersservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.keycloak.representations.idm.UserRepresentation;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Users")
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Name",nullable = false)
    private String name;

    @Column(name = "Last Name",nullable = false)
    private String lastName;

    @Column(name= "Username", nullable = false, unique = true)
    private String username;

    @Column(name = "Email",nullable = false, unique = true)
    private String email;

    @Column(name = "Phone Number",nullable = false)
    private String phoneNumber;

    @Column(name = "CVU",nullable = false, unique = true)
    private String cvu;

    @Column(name = "Alias",nullable = false, unique = true)
    private String alias;

    @Column(name = "Password",nullable = false)
    private String password;

    @JsonIgnore
    private String keycloakId;

    public User(String name, String lastName, String username, String email, String phoneNumber, String cvu, String alias, String password) {
        this.name = name;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.cvu = cvu;
        this.alias = alias;
        this.password = password;
    }

    public static User toUser(UserRepresentation userRepresentation) {
        User user = new User();
        user.setKeycloakId(userRepresentation.getId());
        user.setName(userRepresentation.getUsername());
        user.setName(userRepresentation.getFirstName());
        user.setLastName(userRepresentation.getLastName());
        user.setEmail(userRepresentation.getEmail());
        return user;
    }
}

