package com.pisarevAS.familybudget.transaction.repository;

import com.pisarevAS.familybudget.transaction.model.Transaction;
import com.pisarevAS.familybudget.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Optional<Transaction> findByIdAndUser(long transactionId, User user);

    List<Transaction> findAllByUserAndDateAfterAndDateBefore(User user, LocalDateTime start, LocalDateTime end);
}