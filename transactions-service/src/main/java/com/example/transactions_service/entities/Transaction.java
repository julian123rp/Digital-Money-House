package com.example.transactions_service.entities;

import org.springframework.http.HttpStatusCode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Entity
@Data
@Table(name = "transactions")
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int senderId;
    private int receiverId;

    @Column(name = "Balance")
    private Double amountOfMoney;
    private LocalDateTime date;

    public Transaction(int senderId, int receiverId, Double amountOfMoney,LocalDateTime date){
     this.senderId = senderId;
     this.receiverId = receiverId;
     this.amountOfMoney = amountOfMoney;
     this.date = date;
    }
}
