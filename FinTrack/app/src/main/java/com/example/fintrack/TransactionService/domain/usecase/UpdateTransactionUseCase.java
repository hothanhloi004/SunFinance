package com.example.fintrack.TransactionService.domain.usecase;

import com.example.fintrack.AccountService.port.AccountPort;
import com.example.fintrack.TransactionService.data.dao.TransactionDao;
import com.example.fintrack.TransactionService.data.entity.TransactionEntity;

import androidx.room.Transaction;

public class UpdateTransactionUseCase {

    private final TransactionDao transactionDao;
    private final AccountPort accountPort;

    public UpdateTransactionUseCase(
            TransactionDao transactionDao,
            AccountPort accountPort
    ) {
        this.transactionDao = transactionDao;
        this.accountPort = accountPort;
    }

    @Transaction
    public void execute(
            String txId,
            double newAmount,
            String newNote,
            String newAccountId,
            String newCategoryId,
            String newTxTypeId,
            String newTxDate
    ) {

        TransactionEntity oldTx = transactionDao.getById(txId);

        if (oldTx == null) {
            throw new IllegalArgumentException("Transaction not found");
        }

        // ===== ROLLBACK OLD =====

        if ("INCOME".equals(oldTx.tx_type_id)) {

            accountPort.updateBalance(oldTx.target_account_id, -oldTx.amount);

        }
        else if ("EXPENSE".equals(oldTx.tx_type_id)) {

            accountPort.updateBalance(oldTx.source_account_id, oldTx.amount);

        }
        else if ("TRANSFER".equals(oldTx.tx_type_id)) {

            accountPort.updateBalance(oldTx.source_account_id, oldTx.amount);
            accountPort.updateBalance(oldTx.target_account_id, -oldTx.amount);
        }

        // ===== APPLY NEW =====

        if ("INCOME".equals(newTxTypeId)) {

            accountPort.updateBalance(newAccountId, newAmount);

        }
        else if ("EXPENSE".equals(newTxTypeId)) {

            accountPort.updateBalance(newAccountId, -newAmount);

        }
        else if ("TRANSFER".equals(newTxTypeId)) {

            accountPort.updateBalance(oldTx.source_account_id, -newAmount);
            accountPort.updateBalance(oldTx.target_account_id, newAmount);
        }

        // ===== UPDATE ENTITY =====

        oldTx.amount = newAmount;
        oldTx.note = newNote;
        oldTx.category_id = newCategoryId;
        oldTx.tx_type_id = newTxTypeId;
        oldTx.tx_date = newTxDate;

        transactionDao.update(oldTx);
    }
}