package com.example.accounts_service.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTransaction {
    private Long id;
    private int senderId;
    private int receiverId;
    private double amountOfMoney;
    private LocalDateTime data;

    public CreateTransaction(int senderId, int receiverId, double amountOfMoney, LocalDateTime data) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.amountOfMoney = amountOfMoney;
        this.data = data;
    }
}
