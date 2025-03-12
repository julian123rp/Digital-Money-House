package com.example.DigitalMoneyHouseJR.usersservice.controller;

import com.example.DigitalMoneyHouseJR.usersservice.entities.AccessKeycloak;
import com.example.DigitalMoneyHouseJR.usersservice.entities.Login;
import com.example.DigitalMoneyHouseJR.usersservice.entities.dto.*;
import com.example.DigitalMoneyHouseJR.usersservice.exceptions.BadRequestException;
import com.example.DigitalMoneyHouseJR.usersservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/hola")
    public String saludar() {
        return "Hola desde Colombia!";
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(id));
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody UserRegistrationDTO userRegistrationDTO) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.createUser(userRegistrationDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Login loginData) throws Exception{
        AccessKeycloak credentials = userService.login(loginData);

        if (credentials != null) {
            return ResponseEntity.ok(credentials);
        } else if (userService.findByEmail(loginData.getEmail()).isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        System.out.println(userId);

        if (userId.isEmpty()) {
            ResponseEntity.notFound().build();
        }

        userService.logout(userId);

        return ResponseEntity.ok("Succesfully logged out");
    }

    @PutMapping("/{username}/forgot-password")
    public void forgotPassword(@PathVariable String username) {
        userService.forgotPassword(username);
    }

    @PatchMapping("/update-alias")
    public ResponseEntity<?> updateAlias(@RequestBody NewAliasRequest newAlias) throws BadRequestException {
        String kcId = SecurityContextHolder.getContext().getAuthentication().getName();
        userService.updateAlias(kcId,newAlias);
        return ResponseEntity.ok("Alias updated succesfully");
    }

    @PatchMapping("/update-user")
    public ResponseEntity<?> updateUser(@RequestBody UpdateUserRequest userUpdateRequest) throws Exception {
        String kcId = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDTO userUpdated = userService.updateUser(kcId, userUpdateRequest);

        if(userUpdated == null) {
            ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(userUpdated);
    }

    @PatchMapping("/update-password")
    public ResponseEntity<?> updatePassword(@RequestBody NewPasswordRequest passwordRequest) throws BadRequestException {
        String kcId = SecurityContextHolder.getContext().getAuthentication().getName();
        userService.updatePassword(kcId, passwordRequest);

        return ResponseEntity.ok("Password updated succesfully");
    }

    @GetMapping("/keycloak-id/{kcId}")
    public ResponseEntity<?> getUserByKeycloakId(@PathVariable String kcId) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserIdByKcId(kcId));
    }

    @GetMapping("/alias/{alias}")
    public ResponseEntity<?> getUserIdByAlias(@PathVariable String alias) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserIdByAlias(alias));
    }

    @GetMapping("/cvu/{cvu}")
    public ResponseEntity<?> getUserIdByCvu(@PathVariable String cvu) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserIdByCvu(cvu));
    }
}


