package com.example.accounts_service.entities;

import lombok.Data;

@Data
public class TransactionRequest {
    private String destinyAccount;
    private Double amount;
}
