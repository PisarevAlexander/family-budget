package com.pisarevAS.familybudget.transaction.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {

    private LocalDateTime date;
    private String type;
    private String category;
    private Double count;
}