package com.pisarevAS.familybudget.transaction.service;

import com.pisarevAS.familybudget.transaction.model.Transaction;
import com.pisarevAS.familybudget.transaction.model.TransactionDto;

import java.util.List;

public interface TransactionService {

    TransactionDto create(String date, String type, String category, String count);

    void delete(long transactionId);

    Transaction getById(long transactionId);

    void update(String transactionId, String date, String type, String category, String count);

    List<Transaction> findById(String transactionId);

    List<Transaction> findByDateRange(String startDate, String endDate);

    List<TransactionDto> createTransactionsDtos(List<Transaction> transactions);
}