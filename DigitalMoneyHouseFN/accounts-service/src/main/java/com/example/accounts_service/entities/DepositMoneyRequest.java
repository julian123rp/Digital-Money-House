package com.example.accounts_service.entities;

import lombok.Data;

@Data
public class DepositMoneyRequest {
    private String cardNumber;
    private double amount;
}
