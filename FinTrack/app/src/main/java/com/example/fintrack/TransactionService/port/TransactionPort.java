package com.example.fintrack.TransactionService.port;

import com.example.fintrack.TransactionService.data.entity.TransactionEntity;

import java.util.List;

public interface TransactionPort {

    TransactionEntity getTransactionById(String txId);

    List<TransactionEntity> getAllTransactions();

    List<TransactionEntity> searchTransactions(
            String userId,
            String txTypeId,
            String accountId,
            String keyword,
            String fromDate,
            String toDate
    );

    TransactionEntity getLatestTransaction();
}