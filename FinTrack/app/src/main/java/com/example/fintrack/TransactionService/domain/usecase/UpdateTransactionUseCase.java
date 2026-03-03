package com.example.fintrack.TransactionService.domain.usecase;

import com.example.fintrack.TransactionService.data.dao.AccountDao;
import com.example.fintrack.TransactionService.data.dao.TransactionDao;
import com.example.fintrack.TransactionService.data.entity.TransactionEntity;
import androidx.room.Transaction;

public class UpdateTransactionUseCase {

    private final TransactionDao transactionDao;
    private final AccountDao accountDao;

    public UpdateTransactionUseCase(
            TransactionDao transactionDao,
            AccountDao accountDao
    ) {
        this.transactionDao = transactionDao;
        this.accountDao = accountDao;
    }

    /**
     * Sửa giao dịch Thu / Chi
     */
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

        // 1. Rollback balance cũ
        if ("INCOME".equals(oldTx.tx_type_id)) {
            accountDao.updateBalance(oldTx.target_account_id, -oldTx.amount);
        } else if ("EXPENSE".equals(oldTx.tx_type_id)) {
            accountDao.updateBalance(oldTx.source_account_id, oldTx.amount);
        }

        // 2. Validate
        if (newAmount <= 0) {
            throw new IllegalArgumentException("Amount must be > 0");
        }

        // 3. Apply balance mới
        if ("INCOME".equals(newTxTypeId)) {
            accountDao.updateBalance(newAccountId, newAmount);
        } else {
            accountDao.updateBalance(newAccountId, -newAmount);
        }

        // 4. Update transaction
        oldTx.amount = newAmount;
        oldTx.note = newNote;
        oldTx.category_id = newCategoryId;
        oldTx.tx_type_id = newTxTypeId;
        oldTx.tx_date = newTxDate;

        oldTx.source_account_id =
                "EXPENSE".equals(newTxTypeId) ? newAccountId : null;
        oldTx.target_account_id =
                "INCOME".equals(newTxTypeId) ? newAccountId : null;

        transactionDao.update(oldTx);
    }

}
