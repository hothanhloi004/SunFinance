package com.example.fintrack.TransactionService.api;

import android.content.Context;

import com.example.fintrack.TransactionService.data.dao.TransactionDao;
import com.example.fintrack.TransactionService.data.db.FintrackDatabase;
import com.example.fintrack.TransactionService.data.entity.TransactionEntity;
import com.example.fintrack.TransactionService.port.TransactionPort;

import java.util.List;

public class TransactionApiImpl implements TransactionPort {

    private final TransactionDao transactionDao;

    public TransactionApiImpl(Context context) {

        FintrackDatabase db =
                FintrackDatabase.getInstance(context);

        transactionDao = db.transactionDao();
    }

    @Override
    public TransactionEntity getTransactionById(String txId) {
        return transactionDao.getById(txId);
    }

    @Override
    public List<TransactionEntity> getAllTransactions() {
        return transactionDao.getAll();
    }

    @Override
    public List<TransactionEntity> searchTransactions(
            String userId,
            String txTypeId,
            String accountId,
            String keyword,
            String fromDate,
            String toDate
    ) {

        return transactionDao.searchTransactions(
                userId,
                txTypeId,
                accountId,
                keyword,
                fromDate,
                toDate
        );
    }

    @Override
    public TransactionEntity getLatestTransaction() {
        return transactionDao.getLatestTransaction();
    }
}