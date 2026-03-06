package com.example.fintrack.TransactionService.service;

import android.content.Context;

import com.example.fintrack.TransactionService.data.db.FintrackDatabase;
import com.example.fintrack.TransactionService.data.entity.TransactionEntity;

public class TransactionService {

    private final FintrackDatabase db;

    public TransactionService(Context context) {
        db = FintrackDatabase.getInstance(context);
    }

    public void save(TransactionEntity transaction) {
        new Thread(() -> {
            db.transactionDao().insert(transaction);
        }).start();
    }
}