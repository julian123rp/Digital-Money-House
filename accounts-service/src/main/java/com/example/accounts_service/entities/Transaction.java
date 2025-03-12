package com.example.accounts_service.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Transaction {

    private Long id;
    private int senderId;
    private int receiverId;
    private double amountOfMoney;
    private LocalDateTime data;

    public Transaction(int senderId, int receiverId, double amountOfMoney, LocalDateTime data){
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.amountOfMoney = amountOfMoney;
        this.data = data;
    }
}
