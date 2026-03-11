package com.example.fintrack.TransactionService.domain.usecase;

import androidx.room.Transaction;

import com.example.fintrack.TransactionService.data.dao.AlertDao;
import com.example.fintrack.TransactionService.data.dao.TransactionDao;
import com.example.fintrack.TransactionService.data.entity.TransactionEntity;
import com.example.fintrack.AlertService.usecase.UpdateSpentUseCase;
import com.example.fintrack.AccountService.port.AccountPort;
import android.content.Context;

import com.example.fintrack.AlertService.data.AlertRepository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class AddTransactionUseCase {
    private final Context context;
    private final TransactionDao transactionDao;
    private final AlertDao alertDao;
    private final AccountPort accountPort;

    public AddTransactionUseCase(
            Context context,
            TransactionDao transactionDao,
            AlertDao alertDao,
            AccountPort accountPort
    ) {
        this.context = context.getApplicationContext();
        this.transactionDao = transactionDao;
        this.alertDao = alertDao;
        this.accountPort = accountPort;
    }

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

        // ===== VALIDATE =====
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be > 0");
        }

        if (!"INCOME".equals(txTypeId) && !"EXPENSE".equals(txTypeId)) {
            throw new IllegalArgumentException("Invalid transaction type");
        }

        if (categoryId == null || categoryId.isEmpty()) {
            throw new IllegalArgumentException("Category is required");
        }

        if (accountId == null || accountId.isEmpty()) {
            throw new IllegalArgumentException("Account is required");
        }

        // ===== UPDATE BALANCE =====
        double balanceChange =
                "INCOME".equals(txTypeId) ? amount : -amount;

        accountPort.updateBalance(accountId, balanceChange);

        // ===== CREATE TRANSACTION =====
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

        // ===== SAVE =====
        transactionDao.insert(tx);
        UpdateSpentUseCase updateSpent =
                new UpdateSpentUseCase(context, AlertRepository.getInstance());

        updateSpent.execute(
                context,
                userId,
                categoryId,
                tx.month
        );
    }
}