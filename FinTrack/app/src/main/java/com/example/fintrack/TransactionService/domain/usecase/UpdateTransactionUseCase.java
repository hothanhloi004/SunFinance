package com.example.fintrack.TransactionService.domain.usecase;

import com.example.fintrack.AccountService.api.IAccountApi;
import com.example.fintrack.TransactionService.data.dao.TransactionDao;
import com.example.fintrack.TransactionService.data.entity.TransactionEntity;

import androidx.room.Transaction;

public class UpdateTransactionUseCase {

    private final TransactionDao transactionDao;
    private final IAccountApi accountApi;

    public UpdateTransactionUseCase(
            TransactionDao transactionDao,
            IAccountApi accountApi
    ) {
        this.transactionDao = transactionDao;
        this.accountApi = accountApi;
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

            accountApi.updateBalance(oldTx.target_account_id, -oldTx.amount);

        }
        else if ("EXPENSE".equals(oldTx.tx_type_id)) {

            accountApi.updateBalance(oldTx.source_account_id, oldTx.amount);

        }
        else if ("TRANSFER".equals(oldTx.tx_type_id)) {

            accountApi.updateBalance(oldTx.source_account_id, oldTx.amount);
            accountApi.updateBalance(oldTx.target_account_id, -oldTx.amount);
        }

        // ===== APPLY NEW =====

        if ("INCOME".equals(newTxTypeId)) {

            accountApi.updateBalance(newAccountId, newAmount);

        }
        else if ("EXPENSE".equals(newTxTypeId)) {

            accountApi.updateBalance(newAccountId, -newAmount);

        }
        else if ("TRANSFER".equals(newTxTypeId)) {

            accountApi.updateBalance(oldTx.source_account_id, -newAmount);
            accountApi.updateBalance(oldTx.target_account_id, newAmount);
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