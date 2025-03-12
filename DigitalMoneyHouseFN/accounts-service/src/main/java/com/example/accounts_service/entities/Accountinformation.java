package com.example.accounts_service.entities;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Accountinformation {
    private Long id;
    private Long userId;
    private double balance;
    private String cvu;
    private String alias;
}
