package com.example.fintrack.TransactionService.domain.usecase;

import com.example.fintrack.TransactionService.data.dao.AccountDao;
import com.example.fintrack.TransactionService.data.dao.TransactionDao;
import com.example.fintrack.TransactionService.data.entity.TransactionEntity;
import androidx.room.Transaction;

public class DeleteTransactionUseCase {

    private final TransactionDao transactionDao;
    private final AccountDao accountDao;

    public DeleteTransactionUseCase(
            TransactionDao transactionDao,
            AccountDao accountDao
    ) {
        this.transactionDao = transactionDao;
        this.accountDao = accountDao;
    }

    /**
     * Xóa giao dịch Thu / Chi
     */
    @Transaction
    public void execute(String txId) {

        // ===== 1. LẤY TRANSACTION =====
        TransactionEntity tx = transactionDao.getById(txId);
        if (tx == null) {
            throw new IllegalArgumentException("Transaction not found");
        }

        // ===== 2. ROLLBACK BALANCE =====
        if ("INCOME".equals(tx.tx_type_id)) {
            // Thu nhập → trừ lại
            accountDao.updateBalance(tx.target_account_id, -tx.amount);
        } else if ("EXPENSE".equals(tx.tx_type_id)) {
            // Chi tiêu → cộng lại
            accountDao.updateBalance(tx.source_account_id, tx.amount);
        }

        // ===== 3. DELETE TRANSACTION =====
        transactionDao.delete(tx);
    }
}
