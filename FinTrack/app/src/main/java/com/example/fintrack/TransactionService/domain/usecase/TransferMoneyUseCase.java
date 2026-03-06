package com.example.fintrack.TransactionService.domain.usecase;

import com.example.fintrack.TransactionService.data.dao.TransactionDao;
import com.example.fintrack.TransactionService.data.entity.TransactionEntity;
import com.example.fintrack.AccountService.api.IAccountApi;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class TransferMoneyUseCase {

    private final TransactionDao transactionDao;
    private final IAccountApi accountApi;

    public TransferMoneyUseCase(
            TransactionDao transactionDao,
            IAccountApi accountApi
    ) {
        this.transactionDao = transactionDao;
        this.accountApi = accountApi;
    }

    public void execute(
            String userId,
            String sourceAccountId,
            String targetAccountId,
            double amount,
            String note
    ) {

        if (sourceAccountId == null || targetAccountId == null) {
            throw new IllegalArgumentException("Account is required");
        }

        if (sourceAccountId.equals(targetAccountId)) {
            throw new IllegalArgumentException("Source và Target không được trùng");
        }

        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be > 0");
        }
        double sourceBalance = accountApi.getBalance(sourceAccountId);

        if (sourceBalance < amount) {
            throw new IllegalArgumentException("Không đủ số dư");
        }
        // cập nhật số dư thông qua AccountService
        accountApi.updateBalance(sourceAccountId, -amount);
        accountApi.updateBalance(targetAccountId, amount);

        // tạo transaction
        TransactionEntity tx = new TransactionEntity();
        tx.tx_id = UUID.randomUUID().toString();
        tx.user_id = userId;
        tx.tx_type_id = "TRANSFER";
        tx.source_account_id = sourceAccountId;
        tx.target_account_id = targetAccountId;
        tx.category_id = null;
        tx.amount = amount;
        tx.note = note;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String today = sdf.format(new Date());

        tx.tx_date = today;
        tx.month = today.substring(0, 7);
        tx.created_at = String.valueOf(System.currentTimeMillis());

        transactionDao.insert(tx);
    }
}