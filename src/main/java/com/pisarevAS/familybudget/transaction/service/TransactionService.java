package com.pisarevAS.familybudget.transaction.service;

import com.pisarevAS.familybudget.transaction.model.Transaction;
import com.pisarevAS.familybudget.transaction.model.TransactionDto;

public interface TransactionService {

    TransactionDto create(String date, String type, String category, String count);

    void delete(long transactionId);

    Transaction getById(long transactionId);

    void update(String transactionId, String date, String type, String category, String count);

    String findById(String transactionId);

    String findByDateRange(String startDate, String endDate);
}