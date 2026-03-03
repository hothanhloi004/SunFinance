package com.example.fintrack.TransactionService.domain.usecase;

import androidx.room.Transaction;

import com.example.fintrack.TransactionService.data.dao.AccountDao;
import com.example.fintrack.TransactionService.data.dao.AlertDao;
import com.example.fintrack.TransactionService.data.dao.TransactionDao;
import com.example.fintrack.TransactionService.data.entity.TransactionEntity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class AddTransactionUseCase {

    private final TransactionDao transactionDao;
    private final AccountDao accountDao;
    private final AlertDao alertDao;

    public AddTransactionUseCase(
            TransactionDao transactionDao,
            AccountDao accountDao,
            AlertDao alertDao
    ) {
        this.transactionDao = transactionDao;
        this.accountDao = accountDao;
        this.alertDao = alertDao;
    }

    /**
     * CHỈ XỬ LÝ: INCOME / EXPENSE
     */
    @Transaction
    public void execute(
            String userId,
            String txTypeId,
            String accountId,
            String categoryId,
            double amount,
            String note,
            String txDate
    ) {

        // ===== 1. VALIDATE =====
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be > 0");
        }

        if (!"INCOME".equals(txTypeId) && !"EXPENSE".equals(txTypeId)) {
            throw new IllegalArgumentException("Invalid transaction type");
        }

        if (categoryId == null || categoryId.isEmpty()) {
            throw new IllegalArgumentException("Category is required");
        }

        // ===== 2. UPDATE BALANCE =====
        double balanceChange =
                "INCOME".equals(txTypeId) ? amount : -amount;

        accountDao.updateBalance(accountId, balanceChange);

        // ===== 3. CREATE TRANSACTION =====
        TransactionEntity tx = new TransactionEntity();
        tx.tx_id = UUID.randomUUID().toString();
        tx.user_id = userId;
        tx.tx_type_id = txTypeId;

        tx.source_account_id =
                "EXPENSE".equals(txTypeId) ? accountId : null;

        tx.target_account_id =
                "INCOME".equals(txTypeId) ? accountId : null;

        tx.category_id = categoryId;
        tx.amount = amount;
        tx.note = note;
        tx.tx_date = txDate;

        LocalDate date =
                LocalDate.parse(txDate, DateTimeFormatter.ISO_DATE);
        tx.month = date.getYear() + "-" +
                String.format("%02d", date.getMonthValue());

        tx.created_at = String.valueOf(System.currentTimeMillis());

        // ===== 4. SAVE =====
        transactionDao.insert(tx);

        // ⚠️ ALERT: KHÔNG cập nhật current_spent (để tránh sai lệch)
    }
}
