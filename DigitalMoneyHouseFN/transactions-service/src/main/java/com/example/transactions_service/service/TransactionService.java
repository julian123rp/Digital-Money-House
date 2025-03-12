package com.example.transactions_service.service;

import com.example.transactions_service.entities.Account;
import com.example.transactions_service.entities.Transaction;
import com.example.transactions_service.entities.TransactionRequest;
import com.example.transactions_service.exceptions.ResourceNotFoundException;
import com.example.transactions_service.repository.FeignAccountRepository;
import com.example.transactions_service.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    private TransactionRepository transactionRepository;
    private FeignAccountRepository feignAccountRepository;
    @Autowired
    public TransactionService(TransactionRepository transactionRepository, FeignAccountRepository feignAccountRepository) {
        this.transactionRepository = transactionRepository;
        this.feignAccountRepository = feignAccountRepository;
    }

    public Transaction createTransaction(TransactionRequest transaction) {
        return transactionRepository.save(new Transaction(transaction.getSenderId(), transaction.getReceiverId(), transaction.getAmountOfMoney(), transaction.getDate()));
    }

    public Optional<Account> getAccount(Long userId) {
        try{
            Account account = feignAccountRepository.getAccountById(userId);
            return Optional.of(account);
        } catch (Exception e) {
            return Optional.empty();
        }

    }

    public Optional<List<Transaction>> getLastFiveTransactionsByUserId(Long userId) throws ResourceNotFoundException {
        List<Transaction> lastFiveTransactions = transactionRepository.getLastFiveTransactionsByUserId(userId, Pageable.ofSize(5));
        if(lastFiveTransactions==null || lastFiveTransactions.isEmpty()) {
            throw new ResourceNotFoundException("No transactions found");
        }
        return Optional.of(lastFiveTransactions);
    }

    public Optional<List<Transaction>> getAllTransactions(Long userId) throws ResourceNotFoundException {
        List<Transaction> transactions = transactionRepository.getAllTransactionsById(userId);
        if(transactions==null) {
            throw new ResourceNotFoundException("No transactions found");
        }
        return Optional.of(transactions);
    }


    public void updateAccount(Account account) {
        feignAccountRepository.updateBalance(account, account.getId());
    }

    public Transaction getTransactionById(Long accountId, Long transactionId) throws ResourceNotFoundException {
        Optional<Transaction> transactionOptional = transactionRepository.findTransaction(accountId, transactionId);
        if(transactionOptional.isEmpty()) {
            throw new ResourceNotFoundException("No transaction was found with the provided id");
        } else {
            return transactionOptional.get();
        }
    }
}
