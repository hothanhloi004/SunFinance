package com.example.fintrack.TransactionService.service;

import android.content.Context;

import com.example.fintrack.TransactionService.data.AppDatabase;
import com.example.fintrack.TransactionService.entity.TransactionEntity;

public class TransactionService {

    private final AppDatabase db;

    public TransactionService(Context context) {
        db = AppDatabase.getInstance(context);
    }

    public void save(TransactionEntity transaction) {
        db.transactionDao().insert(transaction);
    }
}