package com.pisarevAS.familybudget.transaction.model;

public class TransactionMapper {

    public static Transaction toTransaction(TransactionDto transactionDto) {
        Transaction transaction = new Transaction();
        transaction.setDate(transactionDto.getDate());
        transaction.setType(Type.valueOf(transactionDto.getType()));
        transaction.setCategory(Category.valueOf(transactionDto.getCategory()));
        transaction.setCount(transactionDto.getCount());
        return transaction;
    }

    public static TransactionDto toTransactionDto(Transaction transaction) {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setDate(transaction.getDate());
        transactionDto.setType(transaction.getType().toString());
        transactionDto.setCategory(transaction.getCategory().toString());
        transactionDto.setCount(transaction.getCount());
        return transactionDto;
    }
}
