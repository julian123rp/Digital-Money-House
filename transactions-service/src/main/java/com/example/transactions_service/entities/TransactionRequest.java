package com.example.transactions_service.entities;


import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class TransactionRequest {
    private int senderId;
    private int receiverId;
    private Double amountOfMoney;
    private LocalDateTime date;
}
