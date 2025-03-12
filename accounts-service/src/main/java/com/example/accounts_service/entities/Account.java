package com.example.accounts_service.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "UserID", nullable = false)
    private Long userId;

    @Column(name = "Balance", nullable = false)
    private double balance;

    @Transient
    private List<Transaction> transactions;

    public Account(Long userId){
        this.userId = userId;
        this.balance = 0.0;
    }

    public Account(Long userId, double balance){
        this.userId = userId;
        this.balance = balance;
    }
}
