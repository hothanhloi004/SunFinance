package com.example.fintrack.TransactionService.domain.usecase;

import com.example.fintrack.AccountService.port.AccountPort;
import com.example.fintrack.TransactionService.data.dao.TransactionDao;
import com.example.fintrack.TransactionService.data.entity.TransactionEntity;

import androidx.room.Transaction;

public class DeleteTransactionUseCase {

    private final TransactionDao transactionDao;
    private final AccountPort accountPort;

    public DeleteTransactionUseCase(
            TransactionDao transactionDao,
            AccountPort accountPort
    ) {
        this.transactionDao = transactionDao;
        this.accountPort = accountPort;
    }

    @Transaction
    public void execute(String txId) {

        TransactionEntity tx = transactionDao.getById(txId);

        if (tx == null) {
            throw new IllegalArgumentException("Transaction not found");
        }

        // ===== INCOME =====
        if ("INCOME".equals(tx.tx_type_id)) {

            if (tx.target_account_id != null) {
                accountPort.updateBalance(
                        tx.target_account_id,
                        -tx.amount
                );
            }

        }

        // ===== EXPENSE =====
        else if ("EXPENSE".equals(tx.tx_type_id)) {

            if (tx.source_account_id != null) {
                accountPort.updateBalance(
                        tx.source_account_id,
                        tx.amount
                );
            }

        }

        // ===== TRANSFER =====
        else if ("TRANSFER".equals(tx.tx_type_id)) {

            if (tx.source_account_id != null) {
                accountPort.updateBalance(
                        tx.source_account_id,
                        tx.amount
                );
            }

            if (tx.target_account_id != null) {
                accountPort.updateBalance(
                        tx.target_account_id,
                        -tx.amount
                );
            }

        }

        transactionDao.delete(tx);
    }
}