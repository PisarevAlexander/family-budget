package com.pisarevAS.familybudget.transaction.service;

import com.pisarevAS.familybudget.exception.NotFoundException;
import com.pisarevAS.familybudget.transaction.model.*;
import com.pisarevAS.familybudget.transaction.repository.TransactionRepository;
import com.pisarevAS.familybudget.user.model.User;
import com.pisarevAS.familybudget.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionServiceImpl implements TransactionService {

    private final UserService userService;
    private final TransactionRepository transactionRepository;
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    @Override
    public TransactionDto create(String date, String type, String category, String count) {
        TransactionDto transactionDto = new TransactionDto(LocalDateTime.parse(date, timeFormatter), type,
                category, Double.parseDouble(count));
        User user = userService.getByLogin();
        Transaction transaction = TransactionMapper.toTransaction(transactionDto);
        transaction.setUser(user);
        return TransactionMapper.toTransactionDto(transactionRepository.save(transaction));
    }

    @Override
    public void update(String transactionId, String date, String type, String category, String count) {
        Transaction transaction = getById(Long.parseLong(transactionId));

        if (!date.isBlank()) {
            transaction.setDate(LocalDateTime.parse(date, timeFormatter));
        }

        if (!type.isBlank()) {
            transaction.setType(Type.valueOf(type));
        }

        if (!category.isBlank()) {
            transaction.setCategory(Category.valueOf(category));
        }

        if (!count.isBlank()) {
            transaction.setCount(Double.parseDouble(count));
        }

        transactionRepository.save(transaction);
    }

    @Override
    public String findById(String transactionId) {
        User user = userService.getByLogin();
        Transaction transaction = transactionRepository.findByIdAndUser(Long.parseLong(transactionId), user)
                .orElseThrow(() -> new NotFoundException("Transaction mot found"));
        return transaction.toString();
    }

    @Override
    public String findByDateRange(String startDate, String endDate) {
        User user = userService.getByLogin();
        List<Transaction> transactions = transactionRepository.findAllByUserAndDateAfterAndDateBefore(user,
                LocalDateTime.parse(startDate, timeFormatter), LocalDateTime.parse(endDate, timeFormatter));
        return transactions.toString();
    }

    @Override
    public void delete(long transactionId) {
        transactionRepository.deleteById(transactionId);
    }

    @Override
    public Transaction getById(long transactionId) {
        return transactionRepository.findById(transactionId)
                .orElseThrow(() -> new NotFoundException("Id " + transactionId + " not found"));
    }


}